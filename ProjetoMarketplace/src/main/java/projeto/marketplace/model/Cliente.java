package projeto.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "seq_cliente", allocationSize = 50)
    @Column(name = "ID_CLIENTE")
    private Integer clienteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO", length = 2)
    private String estado;

    @Column(name = "REGIAO")
    private String regiao;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "FAIXA_ETARIA")
    private String faixaEtaria;

    @Column(name = "FAIXA_RENDA")
    private String faixaRenda;

    @Column(name = "CANAL_AQUISICAO")
    private String canalAquisicao;

    @Column(name = "DATA_CADASTRO")
    private LocalDate dataCadastro;

    @Column(name = "STATUS_CLIENTE")
    private String statusCliente;

    @Column(name = "SCORE_CHURN")
    private Double scoreChurn;

    @Column(name = "INDICE_FIDELIDADE")
    private Double indiceFidelidade;

    public Cliente() {}
    // Getters e Setters

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getFaixaRenda() {
        return faixaRenda;
    }

    public void setFaixaRenda(String faixaRenda) {
        this.faixaRenda = faixaRenda;
    }

    public String getCanalAquisicao() {
        return canalAquisicao;
    }

    public void setCanalAquisicao(String canalAquisicao) {
        this.canalAquisicao = canalAquisicao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatusCliente() {
        return statusCliente;
    }

    public void setStatusCliente(String statusCliente) {
        this.statusCliente = statusCliente;
    }

    public Double getScoreChurn() {
        return scoreChurn;
    }

    public void setScoreChurn(Double scoreChurn) {
        this.scoreChurn = scoreChurn;
    }

    public Double getIndiceFidelidade() {
        return indiceFidelidade;
    }

    public void setIndiceFidelidade(Double indiceFidelidade) {
        this.indiceFidelidade = indiceFidelidade;
    }
}