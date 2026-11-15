package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {}