package com.challenge.investimentos.investimentos_api.security;

import com.challenge.investimentos.investimentos_api.model.Usuario;
import com.challenge.investimentos.investimentos_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por carregar os detalhes do usuário para autenticação do Spring Security.
 *
 * Implementa {@link UserDetailsService} para buscar usuários no banco de dados.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {


    /**
     * Repositório de usuários para acesso aos dados persistidos.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;


    /**
     * Carrega os detalhes do usuário a partir do nome de usuário para autenticação.
     *
     * param username nome de usuário
     * return detalhes do usuário para autenticação
     * throws UsernameNotFoundException se o usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + usuario.getRoleAsString());
        return new User(usuario.getUsername(), usuario.getPassword(), List.of(auth));
    }
}
