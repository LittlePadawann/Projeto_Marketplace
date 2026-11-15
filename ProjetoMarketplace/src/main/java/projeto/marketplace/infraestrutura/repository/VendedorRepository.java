package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {}