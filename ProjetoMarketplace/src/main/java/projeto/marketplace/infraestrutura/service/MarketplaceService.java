package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.Marketplace;
import projeto.marketplace.infraestrutura.repository.MarketplaceRepository;

import java.util.List;

@Service
public class MarketplaceService {

    @Autowired
    private MarketplaceRepository repository;

    public List<Marketplace> listarTodos() {
        return repository.findAll();
    }

    public Marketplace buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marketplace não encontrado: " + id));
    }

    @Transactional
    public Marketplace salvar(Marketplace marketplace) {
        return repository.save(marketplace);
    }

    @Transactional
    public void excluir(Integer id) {
        repository.deleteById(id);
    }
}