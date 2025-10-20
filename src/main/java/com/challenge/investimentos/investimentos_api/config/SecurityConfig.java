package com.challenge.investimentos.investimentos_api.config;

import com.challenge.investimentos.investimentos_api.security.CustomUserDetailsService;
import com.challenge.investimentos.investimentos_api.security.JwtAuthenticationFilter;
import com.challenge.investimentos.investimentos_api.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configuração de segurança da aplicação utilizando Spring Security.
 *
 * Define as regras de autenticação, autorização, filtros JWT e endpoints públicos.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    

    /**
     * Construtor para injeção de dependências dos componentes JWT e UserDetails.
     *
     * param tokenProvider provedor de tokens JWT
     * param userDetailsService serviço customizado de detalhes do usuário
     */
    public SecurityConfig(JwtTokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }


    /**
     * Bean responsável por codificar senhas usando BCrypt.
     *
     * return PasswordEncoder para criptografia de senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configura a cadeia de filtros de segurança, endpoints públicos e proteção JWT.
     *
     * param http objeto de configuração do HttpSecurity
     * return SecurityFilterChain configurada
     * throws Exception em caso de erro de configuração
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, userDetailsService);

        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/", "/index.html", "/investae-home", "/investae-home.html", "/*.html", "/static/**", "/css/**", "/js/**", "/images/**", "/*.css", "/*.js", "/favicon.ico").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /**
     * Bean para expor o AuthenticationManager do Spring Security.
     *
     * param authConfig configuração de autenticação
     * return AuthenticationManager
     * throws Exception em caso de erro
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
