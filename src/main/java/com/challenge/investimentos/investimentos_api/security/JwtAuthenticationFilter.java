package com.challenge.investimentos.investimentos_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticação JWT para requisições HTTP.
 *
 * Intercepta as requisições, valida o token JWT e autentica o usuário no contexto de segurança do Spring.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    /**
     * Provedor de tokens JWT para validação e extração de informações.
     */
    private final JwtTokenProvider tokenProvider;

    /**
     * Serviço para carregar detalhes do usuário a partir do username extraído do token.
     */
    private final UserDetailsService userDetailsService;


    /**
     * Construtor do filtro de autenticação JWT.
     * param tokenProvider provedor de tokens JWT
     * param userDetailsService serviço de detalhes do usuário
     */
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }


    /**
     * Executa o filtro de autenticação JWT para cada requisição.
     *
     * Valida o token JWT presente no cabeçalho Authorization e autentica o usuário no contexto de segurança.
     *
     * param request requisição HTTP
     * param response resposta HTTP
     * param filterChain cadeia de filtros
     * throws ServletException em caso de erro de servlet
     * throws IOException em caso de erro de I/O
     */
    @Override
    @SuppressWarnings("java:S3776") // Cognitive complexity is acceptable for security filter
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            String token = bearer.substring(7);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsername(token);
                if (StringUtils.hasText(username)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
