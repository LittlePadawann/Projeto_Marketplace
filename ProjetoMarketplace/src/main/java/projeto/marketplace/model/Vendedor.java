package projeto.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "VENDEDORES")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendedor_seq")
    @SequenceGenerator(name = "vendedor_seq", sequenceName = "seq_vendedor", allocationSize = 50)
    @Column(name = "ID_VENDEDOR")
    private Integer vendedorId;

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

    @Column(name = "TIPO_VENDEDOR")
    private String tipoVendedor;

    @Column(name = "CATEGORIA_PRINCIPAL")
    private String categoriaPrincipal;

    @Column(name = "DATA_ENTRADA")
    private LocalDate dataEntrada;

    @Column(name = "STATUS_VENDEDOR")
    private String statusVendedor;

    @Column(name = "REPUTACAO_MEDIA")
    private Double reputacaoMedia;

    @Column(name = "SCORE_RISCO_SUSPENSAO")
    private Double scoreRiscoSuspensao;

    public Vendedor() {}
    // Getters e Setters

    public Integer getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Integer vendedorId) {
        this.vendedorId = vendedorId;
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

    public String getTipoVendedor() {
        return tipoVendedor;
    }

    public void setTipoVendedor(String tipoVendedor) {
        this.tipoVendedor = tipoVendedor;
    }

    public String getCategoriaPrincipal() {
        return categoriaPrincipal;
    }

    public void setCategoriaPrincipal(String categoriaPrincipal) {
        this.categoriaPrincipal = categoriaPrincipal;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getStatusVendedor() {
        return statusVendedor;
    }

    public void setStatusVendedor(String statusVendedor) {
        this.statusVendedor = statusVendedor;
    }

    public Double getReputacaoMedia() {
        return reputacaoMedia;
    }

    public void setReputacaoMedia(Double reputacaoMedia) {
        this.reputacaoMedia = reputacaoMedia;
    }

    public Double getScoreRiscoSuspensao() {
        return scoreRiscoSuspensao;
    }

    public void setScoreRiscoSuspensao(Double scoreRiscoSuspensao) {
        this.scoreRiscoSuspensao = scoreRiscoSuspensao;
    }
}