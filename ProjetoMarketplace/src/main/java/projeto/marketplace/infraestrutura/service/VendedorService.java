package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.Vendedor;
import projeto.marketplace.infraestrutura.repository.VendedorRepository;
import java.util.List;

@Service
public class VendedorService {
    @Autowired private VendedorRepository repository;

    public List<Vendedor> listarTodos() { return repository.findAll(); }
    public Vendedor buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Vendedor não encontrado: " + id));
    }
    @Transactional public Vendedor salvar(Vendedor vendedor) { return repository.save(vendedor); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}