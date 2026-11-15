package projeto.marketplace.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.marketplace.infraestrutura.repository.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired private MarketplaceRepository marketplaceRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private VendedorRepository vendedorRepo;
    @Autowired private ProdutoRepository produtoRepo;
    @Autowired private PedidoRepository pedidoRepo;

    public Map<String, Long> obterMetricasGerais() {
        Map<String, Long> kpis = new HashMap<>();
        kpis.put("totalMarketplaces", marketplaceRepo.count());
        kpis.put("totalClientes", clienteRepo.count());
        kpis.put("totalVendedores", vendedorRepo.count());
        kpis.put("totalProdutos", produtoRepo.count());
        kpis.put("totalPedidos", pedidoRepo.count());
        return kpis;
    }
}