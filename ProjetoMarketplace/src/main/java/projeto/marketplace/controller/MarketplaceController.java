package projeto.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.marketplace.model.Marketplace;
import projeto.marketplace.repository.MarketplaceRepository;

import java.util.List;

@RestController
public class MarketplaceController {

    // Injetamos a interface que criámos
    @Autowired
    private MarketplaceRepository marketplaceRepository;

    @GetMapping("/api/marketplaces")
    public List<Marketplace> listarMarketplaces() {
        // Numa única linha, vamos à base de dados buscar os 5 primeiros!
        return marketplaceRepository.findTop5ByOrderByMarketplaceIdAsc();
    }
}