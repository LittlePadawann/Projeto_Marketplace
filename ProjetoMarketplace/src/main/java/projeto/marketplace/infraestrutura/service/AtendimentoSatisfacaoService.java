package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.infraestrutura.model.AtendimentoSatisfacao;
import projeto.marketplace.infraestrutura.repository.AtendimentoSatisfacaoRepository;
import java.util.List;

@Service
public class AtendimentoSatisfacaoService {
    @Autowired private AtendimentoSatisfacaoRepository repository;

    public List<AtendimentoSatisfacao> listarTodos() { return repository.findAll(); }
    public AtendimentoSatisfacao buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Atendimento Satisfação não encontrado: " + id));
    }
    @Transactional public AtendimentoSatisfacao salvar(AtendimentoSatisfacao satisfacao) { return repository.save(satisfacao); }
    @Transactional public void excluir(Integer id) { repository.deleteById(id); }
}