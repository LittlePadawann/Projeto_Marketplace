package projeto.marketplace.carga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import projeto.marketplace.infraestrutura.model.Usuario;
import projeto.marketplace.infraestrutura.repository.UsuarioRepository;

@Component
public class CargaUsuarios implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario("admin", passwordEncoder.encode("123456"), "ROLE_ADMIN");
            Usuario operador = new Usuario("operador", passwordEncoder.encode("123456"), "ROLE_USER");

            usuarioRepository.save(admin);
            usuarioRepository.save(operador);
            System.out.println("Usuários padrão criados: admin / 123456 (ADMIN) e operador / 123456 (USER)");
        }
    }
}