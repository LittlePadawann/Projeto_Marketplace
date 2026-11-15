package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.Pagamento;
import projeto.marketplace.infraestrutura.repository.PagamentoRepository;
import java.util.List;

@Service
public class PagamentoService {
    @Autowired private PagamentoRepository repository;

    public List<Pagamento> listarTodos() { return repository.findAll(); }
    public Pagamento buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + id));
    }
    @Transactional public Pagamento salvar(Pagamento pagamento) { return repository.save(pagamento); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}