package com.spring_jpa_cache.config;

import com.spring_jpa_cache.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final CustomUserDetailsService userDetailsService;
    private final Environment environment;

    public SecurityConfig(CustomUserDetailsService userDetailsService, Environment environment) {
        this.userDetailsService = userDetailsService;
        this.environment = environment;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // چک کردن پروفایل فعال
        boolean isDevProfile = Arrays.asList(environment.getActiveProfiles()).contains("dev");

        http
                // 1. CORS Configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. CSRF Protection
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/public/**")
                )

                // 3. Headers (XSS و Clickjacking)
                .headers(headers -> {
                    headers
                            .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
                                    "default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self'; img-src 'self'; frame-ancestors 'self';"))
                            .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
                            .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block"))
                            .addHeaderWriter(new StaticHeadersWriter("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0"))
                            .addHeaderWriter(new StaticHeadersWriter("Pragma", "no-cache"))
                            .addHeaderWriter(new StaticHeadersWriter("Expires", "0"))
                            .defaultsDisabled();
                    if (isDevProfile) {
                        headers.frameOptions(frame -> frame.disable());
                    } else {
                        headers.frameOptions(frame -> frame.sameOrigin())
                                .addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security", "max-age=31536000; includeSubDomains"));
                    }
                })

                // 4. Session Management (Session Hijacking)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expired")
                )

                // 5. Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","css/**", "/h2-console/**", "/public/**").permitAll()
                        .requestMatchers("/user/profile/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/data").hasAuthority("READ_DATA")
                        .anyRequest().authenticated()
                )

                // 6. Form Login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // 7. Logout Configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .addLogoutHandler((request, response, authentication) -> {
                            if (authentication != null) {
                                logger.info("User {} logged out at {}", authentication.getName(), LocalDateTime.now());
                            }
                            removeCustomCookie(request, response, "myCustomCookie");
                        })
                        .permitAll()
                )

                // 8. HTTPS Enforcement
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure()
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://localhost:8443"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void removeCustomCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}