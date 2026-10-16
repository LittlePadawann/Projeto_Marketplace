package projeto.marketplace.model;

import projeto.marketplace.util.SimNaoConverter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PAGAMENTOS")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_seq")
    @SequenceGenerator(name = "pagamento_seq", sequenceName = "seq_pagamento", allocationSize = 50)
    @Column(name = "ID_PAGAMENTO")
    private Integer pagamentoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEDIDO_ID", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Column(name = "VALOR_PAGO")
    private Double valorPago;

    @Column(name = "FORMA_PAGAMENTO")
    private String formaPagamento;

    @Column(name = "STATUS_PAGAMENTO")
    private String statusPagamento;

    @Column(name = "TAXA_PROCESSAMENTO")
    private Double taxaProcessamento;

    @Column(name = "SCORE_RISCO_FRAUDE")
    private Double scoreRiscoFraude;

    @Convert(converter = SimNaoConverter.class)
    @Column(name = "FRAUDE_CONFIRMADA", length = 3)
    private Boolean fraudeConfirmada;

    public Pagamento() {}
    // Getters e Setters

    public Integer getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(Integer pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Double getTaxaProcessamento() {
        return taxaProcessamento;
    }

    public void setTaxaProcessamento(Double taxaProcessamento) {
        this.taxaProcessamento = taxaProcessamento;
    }

    public Double getScoreRiscoFraude() {
        return scoreRiscoFraude;
    }

    public void setScoreRiscoFraude(Double scoreRiscoFraude) {
        this.scoreRiscoFraude = scoreRiscoFraude;
    }

    public Boolean getFraudeConfirmada() {
        return fraudeConfirmada;
    }

    public void setFraudeConfirmada(Boolean fraudeConfirmada) {
        this.fraudeConfirmada = fraudeConfirmada;
    }
}