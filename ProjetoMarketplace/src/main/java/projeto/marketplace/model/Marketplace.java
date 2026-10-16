package projeto.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MARKETPLACES")
public class Marketplace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marketplace_seq")
    @SequenceGenerator(name = "marketplace_seq", sequenceName = "seq_marketplace", allocationSize = 50)
    @Column(name = "ID_MARKETPLACE")
    private Integer marketplaceId;

    @Column(name = "NOME_MARKETPLACE", length = 150)
    private String nomeMarketplace;

    @Column(name = "SEGMENTO")
    private String segmento;

    @Column(name = "CIDADE_SEDE")
    private String cidadeSede;

    @Column(name = "ESTADO", length = 2)
    private String estado;

    @Column(name = "REGIAO")
    private String regiao;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "ANO_FUNDACAO")
    private Integer anoFundacao;

    @Column(name = "PORTE")
    private String porte;

    @Column(name = "MODELO_NEGOCIO")
    private String modeloNegocio;

    @Column(name = "GMV_ANUAL_ESTIMADO")
    private Double gmvAnualEstimado;

    @Column(name = "TAKE_RATE_MEDIO_PERCENTUAL")
    private Double takeRateMedioPercentual;

    public Marketplace() {}
    // Getters e Setters

    public Integer getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(Integer marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getNomeMarketplace() {
        return nomeMarketplace;
    }

    public void setNomeMarketplace(String nomeMarketplace) {
        this.nomeMarketplace = nomeMarketplace;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getCidadeSede() {
        return cidadeSede;
    }

    public void setCidadeSede(String cidadeSede) {
        this.cidadeSede = cidadeSede;
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

    public Integer getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(Integer anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getModeloNegocio() {
        return modeloNegocio;
    }

    public void setModeloNegocio(String modeloNegocio) {
        this.modeloNegocio = modeloNegocio;
    }

    public Double getGmvAnualEstimado() {
        return gmvAnualEstimado;
    }

    public void setGmvAnualEstimado(Double gmvAnualEstimado) {
        this.gmvAnualEstimado = gmvAnualEstimado;
    }

    public Double getTakeRateMedioPercentual() {
        return takeRateMedioPercentual;
    }

    public void setTakeRateMedioPercentual(Double takeRateMedioPercentual) {
        this.takeRateMedioPercentual = takeRateMedioPercentual;
    }
}