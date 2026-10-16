package projeto.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.model.Marketplace;
import java.util.List;

// O JpaRepository já traz o Salvar, Editar, Apagar e Procurar prontos a usar!
public interface MarketplaceRepository extends JpaRepository<Marketplace, Integer> {

    // Metodo mágico do Spring: Basta escrever o nome e ele cria o comando SQL automático!
    List<Marketplace> findTop5ByOrderByMarketplaceIdAsc();
}