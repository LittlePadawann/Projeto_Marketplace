package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.Produto;
import projeto.marketplace.infraestrutura.repository.ProdutoRepository;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired private ProdutoRepository repository;

    public List<Produto> listarTodos() { return repository.findAll(); }
    public Produto buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }
    @Transactional public Produto salvar(Produto produto) { return repository.save(produto); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}