package projeto.marketplace.model;

import projeto.marketplace.util.SimNaoConverter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LOGISTICA_ENTREGAS")
public class LogisticaEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica_seq")
    @SequenceGenerator(name = "logistica_seq", sequenceName = "seq_logistica", allocationSize = 50)
    @Column(name = "LOGISTICA_ID")
    private Integer logisticaId;

    @ManyToOne @JoinColumn(name = "PEDIDO_ID", nullable = false)
    private Pedido pedido;

    @ManyToOne @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "DATA_ENVIO")
    private LocalDate dataEnvio;

    @Column(name = "MODALIDADE_ENTREGA")
    private String modalidadeEntrega;

    @Column(name = "PRAZO_PROMETIDO_DIAS")
    private Integer prazoPrometidoDias;

    @Column(name = "PRAZO_REAL_DIAS")
    private Integer prazoRealDias;

    @Convert(converter = SimNaoConverter.class)
    @Column(name = "ENTREGA_ATRASADA", length = 3)
    private Boolean entregaAtrasada;

    @Column(name = "CUSTO_LOGISTICO")
    private Double custoLogistico;

    @Column(name = "SCORE_RISCO_ATRASO")
    private Double scoreRiscoAtraso;

    @Convert(converter = SimNaoConverter.class)
    @Column(name = "DEVOLUCAO_LOGISTICA", length = 3)
    private Boolean devolucaoLogistica;

    public LogisticaEntrega() {}
    // Getters e Setters

    public Integer getLogisticaId() {
        return logisticaId;
    }

    public void setLogisticaId(Integer logisticaId) {
        this.logisticaId = logisticaId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getModalidadeEntrega() {
        return modalidadeEntrega;
    }

    public void setModalidadeEntrega(String modalidadeEntrega) {
        this.modalidadeEntrega = modalidadeEntrega;
    }

    public Integer getPrazoPrometidoDias() {
        return prazoPrometidoDias;
    }

    public void setPrazoPrometidoDias(Integer prazoPrometidoDias) {
        this.prazoPrometidoDias = prazoPrometidoDias;
    }

    public Integer getPrazoRealDias() {
        return prazoRealDias;
    }

    public void setPrazoRealDias(Integer prazoRealDias) {
        this.prazoRealDias = prazoRealDias;
    }

    public Boolean getEntregaAtrasada() {
        return entregaAtrasada;
    }

    public void setEntregaAtrasada(Boolean entregaAtrasada) {
        this.entregaAtrasada = entregaAtrasada;
    }

    public Double getCustoLogistico() {
        return custoLogistico;
    }

    public void setCustoLogistico(Double custoLogistico) {
        this.custoLogistico = custoLogistico;
    }

    public Double getScoreRiscoAtraso() {
        return scoreRiscoAtraso;
    }

    public void setScoreRiscoAtraso(Double scoreRiscoAtraso) {
        this.scoreRiscoAtraso = scoreRiscoAtraso;
    }

    public Boolean getDevolucaoLogistica() {
        return devolucaoLogistica;
    }

    public void setDevolucaoLogistica(Boolean devolucaoLogistica) {
        this.devolucaoLogistica = devolucaoLogistica;
    }
}