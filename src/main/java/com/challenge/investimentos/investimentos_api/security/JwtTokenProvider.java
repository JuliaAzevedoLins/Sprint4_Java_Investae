package com.challenge.investimentos.investimentos_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Provedor utilitário para geração, validação e extração de informações de tokens JWT.
 *
 * Utiliza a biblioteca JJWT para manipulação dos tokens.
 */
@Component
public class JwtTokenProvider {


    /**
     * Chave secreta utilizada para assinar e validar os tokens JWT.
     */
    private final Key key;


    /**
     * Construtor que inicializa a chave secreta e o tempo de expiração do token.
     * param secret chave secreta para assinatura do token
     * param expirationMillis tempo de expiração em milissegundos
     */
    public JwtTokenProvider(@Value("${security.jwt.secret:ChangeThisSecretForProd}") String secret,
                            @Value("${security.jwt.expiration:3600000}") long expirationMillis) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMillis;
    }


    /**
     * Tempo de expiração do token em milissegundos.
     */
    private final long expirationMillis;


    /**
     * Cria um token JWT para o usuário e role informados.
     *
     * param username nome de usuário
     * param role perfil do usuário
     * return token JWT gerado
     */
    public String createToken(String username, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Valida se o token JWT é válido e não expirou.
     *
     * param token token JWT a ser validado
     * return true se válido, false caso contrário
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }


    /**
     * Extrai o nome de usuário do token JWT.
     *
     * param token token JWT
     * return nome de usuário contido no token
     */
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
