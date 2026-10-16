package projeto.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketplaceApplication {
    public static void main(String[] args) {
        // Inicia o servidor Web na porta 8080
        SpringApplication.run(MarketplaceApplication.class, args);
        System.out.println("Servidor a correr! Aceda a http://localhost:8080/api/marketplaces");
    }
}