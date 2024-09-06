package com.complex.finAdvisor.service;

import com.complex.finAdvisor.config.JwtCore;
import com.complex.finAdvisor.dto.*;
import com.complex.finAdvisor.entity.ResetCodeEntity;
import com.complex.finAdvisor.entity.UserEntity;
import com.complex.finAdvisor.repository.ResetCodeRepository;
import com.complex.finAdvisor.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
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

import java.io.IOException;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

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
    private final ResetCodeRepository resetCodeRepository;
    private final MailSenderService mailService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

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
        jwtCookie.setHttpOnly(false);
        jwtCookie.setPath("/");

        // Создаем куки для refresh токена
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(refreshTokenLifetime); // 30 дней
        refreshTokenCookie.setHttpOnly(false);
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
            Optional<UserEntity> authUser = userRepository.findByUsername(username);
            TokenRefreshResponse userData = new TokenRefreshResponse();
            authUser.ifPresent(userEntity -> {
                userData.setTariff(userEntity.getTariff());
                userData.setUsername(username);
                userData.setEmail(userEntity.getEmail());
                userData.setTgNickname(userEntity.getTgNickname());
                userData.setTariffInception(userEntity.getTariffInception());
                userData.setTariffExpiration(userEntity.getTariffExpiration());
                userData.setTariffDuration(userEntity.getTariffDuration());
            });
            // Загружаем данные пользователя
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Генерируем новый access токен
            String newToken = jwtCore.generateToken(auth);

            // Создаем куки для нового access токена
            Cookie jwtCookie = new Cookie("accessToken", newToken);
            jwtCookie.setMaxAge(tokenLifetime); // 1 неделя
            jwtCookie.setHttpOnly(false);
            jwtCookie.setPath("/");

            // Добавляем куки в ответ
            response.addCookie(jwtCookie);

            return ResponseEntity.ok(userData);
        } catch (JwtException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is invalid");
        }
    }

    public ResponseEntity<?> sendResetCode(MailRequest request) throws MessagingException, IOException {
        Optional<UserEntity> currentUser = userRepository.findByEmail(request.getEmail());
        if (currentUser.isPresent()) {
            Optional<ResetCodeEntity> currentCode = resetCodeRepository.findByEmail(currentUser.get().getEmail());
            currentCode.ifPresent(resetCodeRepository::delete);
            String resetCode = generateRandomCode();
            ResetCodeEntity resetCodeEntity = new ResetCodeEntity(resetCode,currentUser.get().getEmail());
            resetCodeRepository.save(resetCodeEntity);
            String subject = "Попытка сброса пароля";
            mailService.sendHtmlMessage(currentUser.get().getEmail(), subject, resetCode);
            return ResponseEntity.ok("Код отправлен");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с таким email не найден");
    }

    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        if (request.getNewPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароль не должен быть пустой");
        }
        Optional<UserEntity> currentUser = userRepository.findByEmail(request.getEmail());
        if (currentUser.isPresent()) {
            Optional<ResetCodeEntity> currentCode = resetCodeRepository.findByEmail(request.getEmail());
            if (currentCode.isPresent()) {
                if (Objects.equals(request.getCode(), currentCode.get().getCode())) {
                    if (!currentCode.get().isExpired()) {
                        String newPassword = passwordEncoder.encode(request.getNewPassword());
                        currentUser.get().setPassword(newPassword);
                        resetCodeRepository.delete(currentCode.get());
                        return ResponseEntity.ok("Пароль успешно изменен");
                    }
                    else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Код подтверждения устарел. Перевыпустите код");
                    }
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный код подтверждения");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с таким email не найден");
    }

    private static String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}
