package projeto.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CAMPANHAS_MARKETING")
public class CampanhaMarketing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campanha_seq")
    @SequenceGenerator(name = "campanha_seq", sequenceName = "seq_campanha", allocationSize = 50)
    @Column(name = "CAMPANHA_ID")
    private Integer campanhaId;

    @ManyToOne @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "DATA_REFERENCIA")
    private LocalDate dataReferencia;

    @Column(name = "CANAL_MARKETING")
    private String canalMarketing;

    @Column(name = "CATEGORIA_FOCO")
    private String categoriaFoco;

    @Column(name = "INVESTIMENTO_MARKETING")
    private Double investimentoMarketing;

    @Column(name = "VISITAS")
    private Integer visitas;

    @Column(name = "PEDIDOS_GERADOS")
    private Integer pedidosGerados;

    @Column(name = "TAXA_CONVERSAO_PERCENTUAL")
    private Double taxaConversaoPercentual;

    @Column(name = "ROAS")
    private Double roas;

    @Column(name = "PROBABILIDADE_CONVERSAO")
    private Double probabilidadeConversao;

    public CampanhaMarketing() {}
    // Getters e Setters

    public Integer getCampanhaId() {
        return campanhaId;
    }

    public void setCampanhaId(Integer campanhaId) {
        this.campanhaId = campanhaId;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public String getCanalMarketing() {
        return canalMarketing;
    }

    public void setCanalMarketing(String canalMarketing) {
        this.canalMarketing = canalMarketing;
    }

    public String getCategoriaFoco() {
        return categoriaFoco;
    }

    public void setCategoriaFoco(String categoriaFoco) {
        this.categoriaFoco = categoriaFoco;
    }

    public Double getInvestimentoMarketing() {
        return investimentoMarketing;
    }

    public void setInvestimentoMarketing(Double investimentoMarketing) {
        this.investimentoMarketing = investimentoMarketing;
    }

    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public Integer getPedidosGerados() {
        return pedidosGerados;
    }

    public void setPedidosGerados(Integer pedidosGerados) {
        this.pedidosGerados = pedidosGerados;
    }

    public Double getTaxaConversaoPercentual() {
        return taxaConversaoPercentual;
    }

    public void setTaxaConversaoPercentual(Double taxaConversaoPercentual) {
        this.taxaConversaoPercentual = taxaConversaoPercentual;
    }

    public Double getRoas() {
        return roas;
    }

    public void setRoas(Double roas) {
        this.roas = roas;
    }

    public Double getProbabilidadeConversao() {
        return probabilidadeConversao;
    }

    public void setProbabilidadeConversao(Double probabilidadeConversao) {
        this.probabilidadeConversao = probabilidadeConversao;
    }
}