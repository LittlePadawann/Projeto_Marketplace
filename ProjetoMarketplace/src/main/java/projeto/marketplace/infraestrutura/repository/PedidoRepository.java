package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {}