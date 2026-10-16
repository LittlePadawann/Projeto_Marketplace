package projeto.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUTOS")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
    @SequenceGenerator(name = "produto_seq", sequenceName = "seq_produto", allocationSize = 50)
    @Column(name = "ID_PRODUTO")
    private Integer produtoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VENDEDOR_ID", nullable = false)
    private Vendedor vendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "NOME_PRODUTO", length = 200)
    private String nomeProduto;

    @Column(name = "CATEGORIA_PRODUTO")
    private String categoriaProduto;

    @Column(name = "PRECO_PRODUTO")
    private Double precoProduto;

    @Column(name = "ESTOQUE_ATUAL")
    private Integer estoqueAtual;

    @Column(name = "AVALIACAO_MEDIA")
    private Double avaliacaoMedia;

    @Column(name = "STATUS_PRODUTO")
    private String statusProduto;

    @Column(name = "MARGEM_ESTIMADA_PERCENTUAL")
    private Double margemEstimadaPercentual;

    @Column(name = "SCORE_RISCO_RUPTURA")
    private Double scoreRiscoRuptura;

    public Produto() {}
    // Getters e Setters

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public Double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(Double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(Integer estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public Double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(Double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public String getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(String statusProduto) {
        this.statusProduto = statusProduto;
    }

    public Double getMargemEstimadaPercentual() {
        return margemEstimadaPercentual;
    }

    public void setMargemEstimadaPercentual(Double margemEstimadaPercentual) {
        this.margemEstimadaPercentual = margemEstimadaPercentual;
    }

    public Double getScoreRiscoRuptura() {
        return scoreRiscoRuptura;
    }

    public void setScoreRiscoRuptura(Double scoreRiscoRuptura) {
        this.scoreRiscoRuptura = scoreRiscoRuptura;
    }
}