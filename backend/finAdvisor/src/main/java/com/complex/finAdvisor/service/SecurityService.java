package com.complex.finAdvisor.service;

import com.complex.finAdvisor.config.JwtCore;
import com.complex.finAdvisor.dto.SigninRequest;
import com.complex.finAdvisor.dto.SignupRequest;
import com.complex.finAdvisor.dto.TokenResponse;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserDetailsService userDetailsService;
    @Value("${jwt-token.secret}")
    private String secret;
    @Value("${jwt-token.lifetime}")
    private int tokenLifetime;
    @Value("${jwt-token.refresh-lifetime}")
    private int refreshTokenLifetime;
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    private AuthenticationManager authenticationManager;
    @Autowired
    public void setAuthenticationManager(@Qualifier("authenticationManager") AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    private JwtCore jwtCore;
    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }
    public ResponseEntity<?> register(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Имя занято");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Почтовый ящик занят");
        }
        if (signupRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароль не должен быть пустой");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signupRequest.getUsername());
        userEntity.setEmail(signupRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        userEntity.setCreatedAt(moscowTime.toLocalDateTime());
        userRepository.save(userEntity);
        return ResponseEntity.ok("Added user successfully");
    }
    public ResponseEntity<?> login(SigninRequest signinRequest, HttpServletResponse response) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не найден");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        String refreshToken = jwtCore.generateRefreshToken(authentication);

        // Создаем куки для access токена
        Cookie jwtCookie = new Cookie("accessToken", jwt);
        jwtCookie.setMaxAge(tokenLifetime); // 1 неделя
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");

        // Создаем куки для refresh токена
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(refreshTokenLifetime); // 30 дней
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        // Добавляем куки в ответ
        response.addCookie(jwtCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(new TokenResponse(jwt, refreshToken));
    }

    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        // Получаем куки для refresh токена
        Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
        if (refreshTokenCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }

        String refreshToken = refreshTokenCookie.getValue();
        try {
            // Проверяем, действителен ли refresh токен
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken);
            String username = claims.getBody().getSubject();

            // Загружаем данные пользователя
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Генерируем новый access токен
            String newToken = jwtCore.generateToken(auth);

            // Создаем куки для нового access токена
            Cookie jwtCookie = new Cookie("accessToken", newToken);
            jwtCookie.setMaxAge(tokenLifetime); // 1 неделя
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");

            // Добавляем куки в ответ
            response.addCookie(jwtCookie);

            return ResponseEntity.ok(new TokenResponse(newToken, refreshToken));
        } catch (JwtException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is invalid");
        }
    }
}
