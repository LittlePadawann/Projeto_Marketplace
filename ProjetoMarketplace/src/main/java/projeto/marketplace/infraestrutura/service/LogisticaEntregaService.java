package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.LogisticaEntrega;
import projeto.marketplace.infraestrutura.repository.LogisticaEntregaRepository;
import java.util.List;

@Service
public class LogisticaEntregaService {
    @Autowired private LogisticaEntregaRepository repository;

    public List<LogisticaEntrega> listarTodos() { return repository.findAll(); }
    public LogisticaEntrega buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Logística de Entrega não encontrado: " + id));
    }
    @Transactional public LogisticaEntrega salvar(LogisticaEntrega entrega) { return repository.save(entrega); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}