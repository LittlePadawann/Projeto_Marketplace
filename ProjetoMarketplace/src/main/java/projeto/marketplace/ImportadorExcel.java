package projeto.marketplace;

import projeto.marketplace.model.*;
import jakarta.persistence.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.ZoneId;

public class ImportadorExcel {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MarketplacePU");
        EntityManager em = emf.createEntityManager();

        String arquivo = "08_marketplaces.xlsx";

        try (FileInputStream fis = new FileInputStream(arquivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            em.getTransaction().begin();

            System.out.println("Importando Marketplaces...");
            importarMarketplaces(workbook, em);

            // O flush garante que os IDs dos Marketplaces sejam gerados no H2 agora,
            // pois Clientes e Vendedores precisam desses IDs.
            em.flush();

            System.out.println("Importando Clientes...");
            importarClientes(workbook, em);

            System.out.println("Importando Vendedores...");
            importarVendedores(workbook, em);

            System.out.println("Importando Produtos...");
            importarProdutos(workbook, em);

            // O flush aqui é super importante porque a tabela de Pedidos precisa
            // das PKs de Clientes, Vendedores e Produtos geradas no banco!
            em.flush();

            System.out.println("Importando Pedidos...");
            importarPedidos(workbook, em);

            // Último flush antes das tabelas que dependem de Pedidos
            em.flush();

            System.out.println("Importando Pagamentos...");
            importarPagamentos(workbook, em);

            System.out.println("Importando Logística...");
            importarLogistica(workbook, em);

            System.out.println("Importando Campanhas...");
            importarCampanhas(workbook, em);

            System.out.println("Importando Financeiro...");
            importarFinanceiro(workbook, em);

            System.out.println("Importando Atendimento...");
            importarAtendimento(workbook, em);

            em.getTransaction().commit(); // Salva tudo definitivamente no banco
            System.out.println("Importação concluída com sucesso!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    // 1. Tabela Marketplace
    private static void importarMarketplaces(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Marketplaces");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Marketplace mk = new Marketplace();

            // Lendo String
            mk.setNomeMarketplace(row.getCell(1).getStringCellValue());
            mk.setSegmento(row.getCell(2).getStringCellValue());
            mk.setCidadeSede(row.getCell(3).getStringCellValue());
            mk.setEstado(row.getCell(4).getStringCellValue());
            mk.setRegiao(row.getCell(5).getStringCellValue());

            // Lendo Double e Integer
            mk.setLatitude(row.getCell(6).getNumericCellValue());
            mk.setLongitude(row.getCell(7).getNumericCellValue());
            mk.setAnoFundacao((int) row.getCell(8).getNumericCellValue());
            mk.setPorte(row.getCell(9).getStringCellValue());
            mk.setModeloNegocio(row.getCell(10).getStringCellValue());
            mk.setGmvAnualEstimado(row.getCell(11).getNumericCellValue());
            mk.setTakeRateMedioPercentual(row.getCell(12).getNumericCellValue());

            em.persist(mk);
        }
    }

    // 2. Tabela Clientes (Depende de Marketplace)
    private static void importarClientes(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Clientes");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cliente cliente = new Cliente();

            // Lendo FK (Marketplace)
            int mkId = (int) row.getCell(1).getNumericCellValue();
            Marketplace mk = em.getReference(Marketplace.class, mkId);
            cliente.setMarketplace(mk);

            cliente.setCidade(row.getCell(2).getStringCellValue());
            cliente.setEstado(row.getCell(3).getStringCellValue());
            cliente.setRegiao(row.getCell(4).getStringCellValue());
            cliente.setLatitude(row.getCell(5).getNumericCellValue());
            cliente.setLongitude(row.getCell(6).getNumericCellValue());
            cliente.setFaixaEtaria(row.getCell(7).getStringCellValue());
            cliente.setFaixaRenda(row.getCell(8).getStringCellValue());
            cliente.setCanalAquisicao(row.getCell(9).getStringCellValue());

            // Lendo Data
            Cell cellData = row.getCell(10);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                cliente.setDataCadastro(data);
            }

            cliente.setStatusCliente(row.getCell(11).getStringCellValue());
            cliente.setScoreChurn(row.getCell(12).getNumericCellValue());
            cliente.setIndiceFidelidade(row.getCell(13).getNumericCellValue());

            em.persist(cliente);
        }
    }
    // 3. Tabela Vendedores (Depende de Marketplace)
    private static void importarVendedores(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Vendedores");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Vendedor vendedor = new Vendedor();

            // FK: Marketplace
            int mkId = (int) row.getCell(1).getNumericCellValue();
            vendedor.setMarketplace(em.getReference(Marketplace.class, mkId));

            vendedor.setCidade(row.getCell(2).getStringCellValue());
            vendedor.setEstado(row.getCell(3).getStringCellValue());
            vendedor.setRegiao(row.getCell(4).getStringCellValue());
            vendedor.setLatitude(row.getCell(5).getNumericCellValue());
            vendedor.setLongitude(row.getCell(6).getNumericCellValue());
            vendedor.setTipoVendedor(row.getCell(7).getStringCellValue());
            vendedor.setCategoriaPrincipal(row.getCell(8).getStringCellValue());

            // Lendo Data
            Cell cellData = row.getCell(9);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                vendedor.setDataEntrada(data);
            }

            vendedor.setStatusVendedor(row.getCell(10).getStringCellValue());
            vendedor.setReputacaoMedia(row.getCell(11).getNumericCellValue());
            vendedor.setScoreRiscoSuspensao(row.getCell(12).getNumericCellValue());

            em.persist(vendedor);
        }
    }

    // 4. Tabela Produtos (Depende de Vendedor e Marketplace)
    private static void importarProdutos(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Produtos");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Produto produto = new Produto();

            // FK: Vendedor
            int vendId = (int) row.getCell(1).getNumericCellValue();
            produto.setVendedor(em.getReference(Vendedor.class, vendId));

            // FK: Marketplace
            int mkId = (int) row.getCell(2).getNumericCellValue();
            produto.setMarketplace(em.getReference(Marketplace.class, mkId));

            produto.setNomeProduto(row.getCell(3).getStringCellValue());
            produto.setCategoriaProduto(row.getCell(4).getStringCellValue());
            produto.setPrecoProduto(row.getCell(5).getNumericCellValue());
            produto.setEstoqueAtual((int) row.getCell(6).getNumericCellValue());
            produto.setAvaliacaoMedia(row.getCell(7).getNumericCellValue());
            produto.setStatusProduto(row.getCell(8).getStringCellValue());
            produto.setMargemEstimadaPercentual(row.getCell(9).getNumericCellValue());
            produto.setScoreRiscoRuptura(row.getCell(10).getNumericCellValue());

            em.persist(produto);
        }
    }

    // 5. Tabela Pedidos (Depende de Cliente, Vendedor, Produto e Marketplace)
    private static void importarPedidos(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Pedidos");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Pedido pedido = new Pedido();

            // Lendo as 4 FKs
            pedido.setCliente(em.getReference(Cliente.class, (int) row.getCell(1).getNumericCellValue()));
            pedido.setVendedor(em.getReference(Vendedor.class, (int) row.getCell(2).getNumericCellValue()));
            pedido.setProduto(em.getReference(Produto.class, (int) row.getCell(3).getNumericCellValue()));
            pedido.setMarketplace(em.getReference(Marketplace.class, (int) row.getCell(4).getNumericCellValue()));

            // Data
            Cell cellData = row.getCell(5);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                pedido.setDataPedido(data);
            }

            pedido.setStatusPedido(row.getCell(6).getStringCellValue());
            pedido.setQuantidadeItens((int) row.getCell(7).getNumericCellValue());
            pedido.setValorItens(row.getCell(8).getNumericCellValue());
            pedido.setDesconto(row.getCell(9).getNumericCellValue());
            pedido.setValorFrete(row.getCell(10).getNumericCellValue());
            pedido.setValorTotal(row.getCell(11).getNumericCellValue());
            pedido.setReceitaComissao(row.getCell(12).getNumericCellValue());
            pedido.setCanalVenda(row.getCell(13).getStringCellValue());
            pedido.setAvaliacaoCliente((int) row.getCell(14).getNumericCellValue());
            pedido.setProbabilidadeRecompra(row.getCell(15).getNumericCellValue());

            em.persist(pedido);
        }
    }
    // 6. Tabela Pagamentos (Depende de Pedido, Cliente e Marketplace)
    private static void importarPagamentos(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Pagamentos");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Pagamento pag = new Pagamento();

            // Lendo as 3 FKs
            pag.setPedido(em.getReference(Pedido.class, (int) row.getCell(1).getNumericCellValue()));
            pag.setCliente(em.getReference(Cliente.class, (int) row.getCell(2).getNumericCellValue()));
            pag.setMarketplace(em.getReference(Marketplace.class, (int) row.getCell(3).getNumericCellValue()));

            // Data
            Cell cellData = row.getCell(4);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                pag.setDataPagamento(data);
            }

            pag.setValorPago(row.getCell(5).getNumericCellValue());
            pag.setFormaPagamento(row.getCell(6).getStringCellValue());
            pag.setStatusPagamento(row.getCell(7).getStringCellValue());
            pag.setTaxaProcessamento(row.getCell(8).getNumericCellValue());
            pag.setScoreRiscoFraude(row.getCell(9).getNumericCellValue());

            // Lendo Booleano (Sim/Não)
            pag.setFraudeConfirmada(lerBooleanoSimNao(row.getCell(10)));

            em.persist(pag);
        }
    }

    // 7. Tabela Logistica_Entregas (Depende de Pedido e Marketplace)
    private static void importarLogistica(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Logistica_Entregas");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            LogisticaEntrega log = new LogisticaEntrega();

            // FKs
            log.setPedido(em.getReference(Pedido.class, (int) row.getCell(1).getNumericCellValue()));
            log.setMarketplace(em.getReference(Marketplace.class, (int) row.getCell(2).getNumericCellValue()));

            // Data
            Cell cellData = row.getCell(3);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                log.setDataEnvio(data);
            }

            log.setModalidadeEntrega(row.getCell(4).getStringCellValue());
            log.setPrazoPrometidoDias((int) row.getCell(5).getNumericCellValue());
            log.setPrazoRealDias((int) row.getCell(6).getNumericCellValue());

            // Booleano
            log.setEntregaAtrasada(lerBooleanoSimNao(row.getCell(7)));

            log.setCustoLogistico(row.getCell(8).getNumericCellValue());
            log.setScoreRiscoAtraso(row.getCell(9).getNumericCellValue());

            // Booleano
            log.setDevolucaoLogistica(lerBooleanoSimNao(row.getCell(10)));

            em.persist(log);
        }
    }

    // 8. Tabela Campanhas_Marketing (Depende de Marketplace)
    private static void importarCampanhas(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Campanhas_Marketing");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            CampanhaMarketing camp = new CampanhaMarketing();

            camp.setMarketplace(em.getReference(Marketplace.class, (int) row.getCell(1).getNumericCellValue()));

            // Data
            Cell cellData = row.getCell(2);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                camp.setDataReferencia(data);
            }

            camp.setCanalMarketing(row.getCell(3).getStringCellValue());
            camp.setCategoriaFoco(row.getCell(4).getStringCellValue());
            camp.setInvestimentoMarketing(row.getCell(5).getNumericCellValue());
            camp.setVisitas((int) row.getCell(6).getNumericCellValue());
            camp.setPedidosGerados((int) row.getCell(7).getNumericCellValue());
            camp.setTaxaConversaoPercentual(row.getCell(8).getNumericCellValue());
            camp.setRoas(row.getCell(9).getNumericCellValue());
            camp.setProbabilidadeConversao(row.getCell(10).getNumericCellValue());

            em.persist(camp);
        }
    }

    // 9. Tabela Financeiro_Mensal (Depende de Marketplace)
    private static void importarFinanceiro(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Financeiro_Mensal");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            FinanceiroMensal fin = new FinanceiroMensal();

            fin.setMarketplace(em.getReference(Marketplace.class, (int) row.getCell(1).getNumericCellValue()));

            // Data
            Cell cellData = row.getCell(2);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                fin.setDataReferencia(data);
            }

            fin.setCompradoresAtivos((int) row.getCell(3).getNumericCellValue());
            fin.setGmv(row.getCell(4).getNumericCellValue());
            fin.setReceitaComissao(row.getCell(5).getNumericCellValue());
            fin.setReceitaAds(row.getCell(6).getNumericCellValue());
            fin.setReceitaLogistica(row.getCell(7).getNumericCellValue());
            fin.setCustoOperacional(row.getCell(8).getNumericCellValue());
            fin.setCac(row.getCell(9).getNumericCellValue());
            fin.setLtv(row.getCell(10).getNumericCellValue());
            fin.setTaxaChurnPercentual(row.getCell(11).getNumericCellValue());
            fin.setResultadoOperacional(row.getCell(12).getNumericCellValue());

            em.persist(fin);
        }
    }

    // 10. Tabela Atendimento_Satisfacao (Depende de Cliente e Marketplace)
    private static void importarAtendimento(Workbook workbook, EntityManager em) {
        Sheet sheet = workbook.getSheet("Atendimento_Satisfacao");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            AtendimentoSatisfacao at = new AtendimentoSatisfacao();

            at.setCliente(em.getReference(Cliente.class, (int) row.getCell(1).getNumericCellValue()));
            at.setMarketplace(em.getReference(Marketplace.class, (int) row.getCell(2).getNumericCellValue()));

            // Data
            Cell cellData = row.getCell(3);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                at.setDataAtendimento(data);
            }

            at.setCanalAtendimento(row.getCell(4).getStringCellValue());
            at.setAssunto(row.getCell(5).getStringCellValue());
            at.setTempoAtendimentoMin(row.getCell(6).getNumericCellValue());

            // Booleano
            at.setResolvidoPrimeiroContato(lerBooleanoSimNao(row.getCell(7)));

            at.setNotaNps((int) row.getCell(8).getNumericCellValue());
            at.setCategoriaNps(row.getCell(9).getStringCellValue());

            em.persist(at);
        }
    }

    // --- METODO AUXILIAR PARA BOOLEANOS ---
    private static Boolean lerBooleanoSimNao(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return false;
        if (cell.getCellType() == CellType.STRING) {
            String val = cell.getStringCellValue().trim();
            return "Sim".equalsIgnoreCase(val);
        }
        return false;
    }
}