package projeto.marketplace.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PEDIDOS")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    @SequenceGenerator(name = "pedido_seq", sequenceName = "seq_pedido", allocationSize = 50)
    @Column(name = "PEDIDO_ID")
    private Integer pedidoId;

    @ManyToOne @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;

    @ManyToOne @JoinColumn(name = "VENDEDOR_ID", nullable = false)
    private Vendedor vendedor;

    @ManyToOne @JoinColumn(name = "PRODUTO_ID", nullable = false)
    private Produto produto;

    @ManyToOne @JoinColumn(name = "MARKETPLACE_ID", nullable = false)
    private Marketplace marketplace;

    @Column(name = "DATA_PEDIDO")
    private LocalDate dataPedido;

    @Column(name = "STATUS_PEDIDO")
    private String statusPedido;

    @Column(name = "QUANTIDADE_ITENS")
    private Integer quantidadeItens;

    @Column(name = "VALOR_ITENS")
    private Double valorItens;

    @Column(name = "DESCONTO")
    private Double desconto;

    @Column(name = "VALOR_FRETE")
    private Double valorFrete;

    @Column(name = "VALOR_TOTAL")
    private Double valorTotal;

    @Column(name = "RECEITA_COMISSAO")
    private Double receitaComissao;

    @Column(name = "CANAL_VENDA")
    private String canalVenda;

    @Column(name = "AVALIACAO_CLIENTE")
    private Integer avaliacaoCliente;

    @Column(name = "PROBABILIDADE_RECOMPRA")
    private Double probabilidadeRecompra;

    public Pedido() {}
    // Getters e Setters

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Integer getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(Integer quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public Double getValorItens() {
        return valorItens;
    }

    public void setValorItens(Double valorItens) {
        this.valorItens = valorItens;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getReceitaComissao() {
        return receitaComissao;
    }

    public void setReceitaComissao(Double receitaComissao) {
        this.receitaComissao = receitaComissao;
    }

    public String getCanalVenda() {
        return canalVenda;
    }

    public void setCanalVenda(String canalVenda) {
        this.canalVenda = canalVenda;
    }

    public Integer getAvaliacaoCliente() {
        return avaliacaoCliente;
    }

    public void setAvaliacaoCliente(Integer avaliacaoCliente) {
        this.avaliacaoCliente = avaliacaoCliente;
    }

    public Double getProbabilidadeRecompra() {
        return probabilidadeRecompra;
    }

    public void setProbabilidadeRecompra(Double probabilidadeRecompra) {
        this.probabilidadeRecompra = probabilidadeRecompra;
    }
}