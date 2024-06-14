package com.complex.finAdvisor.config;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final JwtCore jwtCore;
    private final UserDetailsService userDetailsService;
    @Value("${jwt-token.secret}")
    private String secret;
    @Value("${jwt-token.lifetime}")
    private int tokenLifetime;
    @Value("${jwt-token.refresh-lifetime}")
    private int refreshTokenLifetime;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String login = null;
        UserDetails userDetails = null;
        UsernamePasswordAuthenticationToken auth = null;
        try {
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7);
            }
            if (jwt != null) {
                try {
                    login = jwtCore.getLoginFromJwt(jwt);
                }
                catch (ExpiredJwtException e) {
                    Cookie[] cookies = request.getCookies();
                    if(cookies != null){
                        for(Cookie cookie : cookies){
                            if(cookie.getName().equals("refreshToken")){
                                String refreshToken = cookie.getValue();
                                try {
                                    Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken);
                                    userDetails = userDetailsService.loadUserByUsername(login);
                                    auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    String newToken = jwtCore.generateToken(auth);
                                    response.setHeader("Authorization", "Bearer " + newToken);

                                    // Получаем куки для access токена и обновляем его значение
                                    Cookie accessTokenCookie = WebUtils.getCookie(request, "accessToken");
                                    if (accessTokenCookie != null) {
                                        accessTokenCookie.setValue(newToken);
                                        accessTokenCookie.setMaxAge(tokenLifetime); // 1 неделя
                                        response.addCookie(accessTokenCookie); // Обновляем куки в ответе
                                    }

                                    // Получаем куки для refresh токена и обновляем его значение
                                    Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
                                    if (refreshTokenCookie != null) {
                                        refreshTokenCookie.setValue(refreshToken);
                                        refreshTokenCookie.setMaxAge(refreshTokenLifetime); // 30 дней
                                        response.addCookie(refreshTokenCookie); // Обновляем куки в ответе
                                    }
                                } catch (JwtException ex) {
                                    System.out.println("Refresh token is invalid");
                                    // Создаем ответ с кодом ошибки и сообщением об ошибке
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.getWriter().write("{\"message\": \"Refresh token is invalid\"}");
                                    return;
                                }
                                break;
                            }
                        }
                    }
                }
                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    userDetails = userDetailsService.loadUserByUsername(login);
                    auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        catch (Exception e) {
            // TODO
        }
        filterChain.doFilter(request, response);
    }
}
