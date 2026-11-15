package projeto.marketplace.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.marketplace.infraestrutura.model.Marketplace;
import java.util.List;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Integer> {
    List<Marketplace> findTop5ByOrderByMarketplaceIdAsc();
}