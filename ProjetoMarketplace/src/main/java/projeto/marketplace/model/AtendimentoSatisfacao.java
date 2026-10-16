package projeto.marketplace.model;

import projeto.marketplace.util.SimNaoConverter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ATENDIMENTO_SATISFACAO")
public class AtendimentoSatisfacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atendimento_satisfacao_seq")
    @SequenceGenerator(name = "atendimento_satisfacao_seq", sequenceName = "seq_atendimento_satisfacao", allocationSize = 50)
    @Column(name = "ID_ATENDIMENTO")
    private Integer atendimentoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "DATA_ATENDIMENTO")
    private LocalDate dataAtendimento;

    @Column(name = "CANAL_ATENDIMENTO")
    private String canalAtendimento;

    @Column(name = "ASSUNTO")
    private String assunto;

    @Column(name = "TEMPO_ATENDIMENTO_MIN")
    private Double tempoAtendimentoMin;

    @Convert(converter = SimNaoConverter.class)
    @Column(name = "RESOLVIDO_PRIMEIRO_CONTATO", length = 3)
    private Boolean resolvidoPrimeiroContato;

    @Column(name = "NOTA_NPS")
    private Integer notaNps;

    @Column(name = "CATEGORIA_NPS")
    private String categoriaNps;

    public AtendimentoSatisfacao() {}
    // Getters e Setters

    public Integer getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(Integer atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getCanalAtendimento() {
        return canalAtendimento;
    }

    public void setCanalAtendimento(String canalAtendimento) {
        this.canalAtendimento = canalAtendimento;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Double getTempoAtendimentoMin() {
        return tempoAtendimentoMin;
    }

    public void setTempoAtendimentoMin(Double tempoAtendimentoMin) {
        this.tempoAtendimentoMin = tempoAtendimentoMin;
    }

    public Boolean getResolvidoPrimeiroContato() {
        return resolvidoPrimeiroContato;
    }

    public void setResolvidoPrimeiroContato(Boolean resolvidoPrimeiroContato) {
        this.resolvidoPrimeiroContato = resolvidoPrimeiroContato;
    }

    public Integer getNotaNps() {
        return notaNps;
    }

    public void setNotaNps(Integer notaNps) {
        this.notaNps = notaNps;
    }

    public String getCategoriaNps() {
        return categoriaNps;
    }

    public void setCategoriaNps(String categoriaNps) {
        this.categoriaNps = categoriaNps;
    }
}