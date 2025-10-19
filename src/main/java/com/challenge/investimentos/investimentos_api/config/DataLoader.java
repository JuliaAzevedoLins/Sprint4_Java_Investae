package com.challenge.investimentos.investimentos_api.config;

import com.challenge.investimentos.investimentos_api.model.Usuario;
import com.challenge.investimentos.investimentos_api.model.UsuarioInvestimento;
import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import com.challenge.investimentos.investimentos_api.repository.UsuarioRepository;
import com.challenge.investimentos.investimentos_api.repository.UsuarioInvestimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Carregador de dados iniciais da aplicação.
 * Executa após a inicialização do Spring Boot para inserir dados padrão.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioInvestimentoRepository usuarioInvestimentoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar se já existe usuário admin
        if (usuarioRepository.findByUsername("admin") == null) {
            // Criar usuário admin padrão
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNome("Administrador");
            admin.setEmail("admin@investimentos.com");
            admin.setRole(RoleEnum.ADMIN);
            
            usuarioRepository.save(admin);
            System.out.println("Usuário admin criado com sucesso!");
        }
        
        // Verificar se já existe usuário investimento padrão
        if (usuarioInvestimentoRepository.count() == 0) {
            // Criar usuário investimento padrão com CPF válido
            UsuarioInvestimento usuarioInv = new UsuarioInvestimento();
            usuarioInv.setCpfIdentificacao("12345678909"); // CPF válido para teste
            
            usuarioInvestimentoRepository.save(usuarioInv);
            System.out.println("Usuário investimento padrão criado com sucesso!");
        }
    }
}