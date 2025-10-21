package com.challenge.investimentos.investimentos_api.config;

import com.challenge.investimentos.investimentos_api.model.Usuario;
import com.challenge.investimentos.investimentos_api.repository.UsuarioRepository;
import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário admin se não existir
        Usuario existingAdmin = usuarioRepository.findByUsername("admin");
        if (existingAdmin == null) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNome("Administrador");
            admin.setEmail("admin@investimentos.com");
            admin.setRole(RoleEnum.ADMIN);
            admin.setCpf("11111111111");
            
            usuarioRepository.save(admin);
            System.out.println("✅ Usuário admin criado com sucesso!");
            System.out.println("   Username: admin");
            System.out.println("   Password: admin123");
        } else {
            System.out.println("ℹ️  Usuário admin já existe no banco de dados.");
        }
    }
}