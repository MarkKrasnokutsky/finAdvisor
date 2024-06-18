package com.complex.finAdvisor.config;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                handleToken(jwt, request, response);
            }
            else {
                Cookie accessTokenCookie = WebUtils.getCookie(request, "accessToken");
                if (accessTokenCookie != null) {
                    jwt = accessTokenCookie.getValue();
                    handleToken(jwt, request, response);
                }
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
            else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Access token is expired and refresh token is not found\"}");
            }
        }
        catch (Exception e) {
            // TODO
        }
        filterChain.doFilter(request, response);
    }
    private void handleToken(String jwt, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String login = jwtCore.getLoginFromJwt(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            // Проверяем, сколько времени осталось до истечения токена
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
            long expirationTime = claims.getExpiration().getTime();
            long currentTime = System.currentTimeMillis();
            long timeLeft = expirationTime - currentTime;
            // Если осталось меньше 1го дня, обновляем токен
            if (timeLeft < 24 * 60 * 60 * 1000) {
                Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
                if (refreshTokenCookie != null && refreshTokenCookie.getValue() != null) {
                    try {
                        String newToken = jwtCore.generateToken(auth);
                        response.setHeader("Authorization", "Bearer " + newToken);
                        // Обновляем куки с токеном доступа
                        Cookie accessTokenCookie = new Cookie("accessToken", newToken);
                        accessTokenCookie.setMaxAge(tokenLifetime);
                        response.addCookie(accessTokenCookie);
                    } catch (JwtException ex) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"message\": \"Refresh token is invalid\"}");
                    }
                }
                else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"message\": \"Access token is expired and refresh token is not found\"}");
                }
            }
        } catch (ExpiredJwtException e) {
            Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
            if (refreshTokenCookie != null) {
                String refreshToken = refreshTokenCookie.getValue();
                try {
                    String login = jwtCore.getLoginFromJwt(refreshToken);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(login);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    String newToken = jwtCore.generateToken(auth);
                    response.setHeader("Authorization", "Bearer " + newToken);
                    // Обновляем куки с токеном доступа
                    Cookie accessTokenCookie = new Cookie("accessToken", newToken);
                    accessTokenCookie.setMaxAge(tokenLifetime);
                    response.addCookie(accessTokenCookie);
                } catch (JwtException ex) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"message\": \"Refresh token is invalid\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Access token is expired and refresh token is not found\"}");
            }
        }
    }
}
