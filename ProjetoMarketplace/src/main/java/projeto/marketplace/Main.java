package projeto.marketplace;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // Inicializa o JPA, lê o persistence.xml e gera as tabelas no H2
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MarketplacePU");
        EntityManager em = emf.createEntityManager();

        System.out.println("Tabelas geradas com sucesso no banco H2!");

        em.close();
        emf.close();
    }
}