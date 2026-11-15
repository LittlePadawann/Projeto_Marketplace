package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {}