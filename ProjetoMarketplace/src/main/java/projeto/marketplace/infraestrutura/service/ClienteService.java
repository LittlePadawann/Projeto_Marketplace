package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.Cliente;
import projeto.marketplace.infraestrutura.repository.ClienteRepository;
import java.util.List;

@Service
public class ClienteService {
    @Autowired private ClienteRepository repository;

    public List<Cliente> listarTodos() { return repository.findAll(); }
    public Cliente buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
    }
    @Transactional public Cliente salvar(Cliente cliente) { return repository.save(cliente); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}