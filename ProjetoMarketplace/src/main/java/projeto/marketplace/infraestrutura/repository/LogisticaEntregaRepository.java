package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.LogisticaEntrega;

public interface LogisticaEntregaRepository extends JpaRepository<LogisticaEntrega, Integer> {}