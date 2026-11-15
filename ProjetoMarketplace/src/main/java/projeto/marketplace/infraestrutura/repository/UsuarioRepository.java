package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}