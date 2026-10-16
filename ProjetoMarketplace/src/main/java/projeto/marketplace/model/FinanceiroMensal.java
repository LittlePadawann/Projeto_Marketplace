package projeto.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FINANCEIRO_MENSAL")
public class FinanceiroMensal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financeiro_seq")
    @SequenceGenerator(name = "financeiro_seq", sequenceName = "seq_financeiro", allocationSize = 50)
    @Column(name = "ID_FINANCEIRO")
    private Integer financeiroId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "DATA_REFERENCIA")
    private LocalDate dataReferencia;

    @Column(name = "COMPRADORES_ATIVOS")
    private Integer compradoresAtivos;

    @Column(name = "GMV")
    private Double gmv;

    @Column(name = "RECEITA_COMISSAO")
    private Double receitaComissao;

    @Column(name = "RECEITA_ADS")
    private Double receitaAds;

    @Column(name = "RECEITA_LOGISTICA")
    private Double receitaLogistica;

    @Column(name = "CUSTO_OPERACIONAL")
    private Double custoOperacional;

    @Column(name = "CAC")
    private Double cac;

    @Column(name = "LTV")
    private Double ltv;

    @Column(name = "TAXA_CHURN_PERCENTUAL")
    private Double taxaChurnPercentual;

    @Column(name = "RESULTADO_OPERACIONAL")
    private Double resultadoOperacional;

    public FinanceiroMensal() {}
    // Getters e Setters

    public Integer getFinanceiroId() {
        return financeiroId;
    }

    public void setFinanceiroId(Integer financeiroId) {
        this.financeiroId = financeiroId;
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

    public Integer getCompradoresAtivos() {
        return compradoresAtivos;
    }

    public void setCompradoresAtivos(Integer compradoresAtivos) {
        this.compradoresAtivos = compradoresAtivos;
    }

    public Double getGmv() {
        return gmv;
    }

    public void setGmv(Double gmv) {
        this.gmv = gmv;
    }

    public Double getReceitaComissao() {
        return receitaComissao;
    }

    public void setReceitaComissao(Double receitaComissao) {
        this.receitaComissao = receitaComissao;
    }

    public Double getReceitaAds() {
        return receitaAds;
    }

    public void setReceitaAds(Double receitaAds) {
        this.receitaAds = receitaAds;
    }

    public Double getReceitaLogistica() {
        return receitaLogistica;
    }

    public void setReceitaLogistica(Double receitaLogistica) {
        this.receitaLogistica = receitaLogistica;
    }

    public Double getCustoOperacional() {
        return custoOperacional;
    }

    public void setCustoOperacional(Double custoOperacional) {
        this.custoOperacional = custoOperacional;
    }

    public Double getCac() {
        return cac;
    }

    public void setCac(Double cac) {
        this.cac = cac;
    }

    public Double getLtv() {
        return ltv;
    }

    public void setLtv(Double ltv) {
        this.ltv = ltv;
    }

    public Double getTaxaChurnPercentual() {
        return taxaChurnPercentual;
    }

    public void setTaxaChurnPercentual(Double taxaChurnPercentual) {
        this.taxaChurnPercentual = taxaChurnPercentual;
    }

    public Double getResultadoOperacional() {
        return resultadoOperacional;
    }

    public void setResultadoOperacional(Double resultadoOperacional) {
        this.resultadoOperacional = resultadoOperacional;
    }
}