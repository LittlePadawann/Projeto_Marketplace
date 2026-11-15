package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.Pedido;
import projeto.marketplace.infraestrutura.repository.PedidoRepository;
import java.util.List;

@Service
public class PedidoService {
    @Autowired private PedidoRepository repository;

    public List<Pedido> listarTodos() { return repository.findAll(); }
    public Pedido buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }
    @Transactional public Pedido salvar(Pedido pedido) { return repository.save(pedido); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}