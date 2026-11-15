package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {}