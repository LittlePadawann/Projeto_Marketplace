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

            // =========================================================
            // SELECT

            System.out.println("\n--- Buscando os 5 primeiros Marketplaces ---");

            // Fazendo um SELECT limitando a 5 resultados
            java.util.List<Marketplace> primeirosMarketplaces = em.createQuery("SELECT m FROM Marketplace m", Marketplace.class)
                    .setMaxResults(5)
                    .getResultList();

            // Imprimindo os resultados
            for (Marketplace m : primeirosMarketplaces) {
                System.out.println("ID: " + m.getMarketplaceId() +
                        " | Nome: " + m.getNomeMarketplace() +
                        " | Segmento: " + m.getSegmento() +
                        " | Cidade: " + m.getCidadeSede());
            }
            // =========================================================

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

            // Lendo Double e Integer com os novos métodos seguros
            mk.setLatitude(lerDouble(row.getCell(6)));
            mk.setLongitude(lerDouble(row.getCell(7)));
            mk.setAnoFundacao(lerInteger(row.getCell(8)));
            mk.setPorte(row.getCell(9).getStringCellValue());
            mk.setModeloNegocio(row.getCell(10).getStringCellValue());
            mk.setGmvAnualEstimado(lerDouble(row.getCell(11)));
            mk.setTakeRateMedioPercentual(lerDouble(row.getCell(12)));

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
            int mkId = lerInteger(row.getCell(1));
            Marketplace mk = em.getReference(Marketplace.class, mkId);
            cliente.setMarketplace(mk);

            cliente.setCidade(row.getCell(2).getStringCellValue());
            cliente.setEstado(row.getCell(3).getStringCellValue());
            cliente.setRegiao(row.getCell(4).getStringCellValue());
            cliente.setLatitude(lerDouble(row.getCell(5)));
            cliente.setLongitude(lerDouble(row.getCell(6)));
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
            cliente.setScoreChurn(lerDouble(row.getCell(12)));
            cliente.setIndiceFidelidade(lerDouble(row.getCell(13)));

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
            int mkId = lerInteger(row.getCell(1));
            vendedor.setMarketplace(em.getReference(Marketplace.class, mkId));

            vendedor.setCidade(row.getCell(2).getStringCellValue());
            vendedor.setEstado(row.getCell(3).getStringCellValue());
            vendedor.setRegiao(row.getCell(4).getStringCellValue());
            vendedor.setLatitude(lerDouble(row.getCell(5)));
            vendedor.setLongitude(lerDouble(row.getCell(6)));
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
            vendedor.setReputacaoMedia(lerDouble(row.getCell(11)));
            vendedor.setScoreRiscoSuspensao(lerDouble(row.getCell(12)));

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
            int vendId = lerInteger(row.getCell(1));
            produto.setVendedor(em.getReference(Vendedor.class, vendId));

            // FK: Marketplace
            int mkId = lerInteger(row.getCell(2));
            produto.setMarketplace(em.getReference(Marketplace.class, mkId));

            produto.setNomeProduto(row.getCell(3).getStringCellValue());
            produto.setCategoriaProduto(row.getCell(4).getStringCellValue());
            produto.setPrecoProduto(lerDouble(row.getCell(5)));
            produto.setEstoqueAtual(lerInteger(row.getCell(6)));
            produto.setAvaliacaoMedia(lerDouble(row.getCell(7)));
            produto.setStatusProduto(row.getCell(8).getStringCellValue());
            produto.setMargemEstimadaPercentual(lerDouble(row.getCell(9)));
            produto.setScoreRiscoRuptura(lerDouble(row.getCell(10)));

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
            pedido.setCliente(em.getReference(Cliente.class, lerInteger(row.getCell(1))));
            pedido.setVendedor(em.getReference(Vendedor.class, lerInteger(row.getCell(2))));
            pedido.setProduto(em.getReference(Produto.class, lerInteger(row.getCell(3))));
            pedido.setMarketplace(em.getReference(Marketplace.class, lerInteger(row.getCell(4))));

            // Data
            Cell cellData = row.getCell(5);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                pedido.setDataPedido(data);
            }

            pedido.setStatusPedido(row.getCell(6).getStringCellValue());
            pedido.setQuantidadeItens(lerInteger(row.getCell(7)));
            pedido.setValorItens(lerDouble(row.getCell(8)));
            pedido.setDesconto(lerDouble(row.getCell(9)));
            pedido.setValorFrete(lerDouble(row.getCell(10)));
            pedido.setValorTotal(lerDouble(row.getCell(11)));
            pedido.setReceitaComissao(lerDouble(row.getCell(12)));
            pedido.setCanalVenda(row.getCell(13).getStringCellValue());
            pedido.setAvaliacaoCliente(lerInteger(row.getCell(14)));
            pedido.setProbabilidadeRecompra(lerDouble(row.getCell(15)));

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
            pag.setPedido(em.getReference(Pedido.class, lerInteger(row.getCell(1))));
            pag.setCliente(em.getReference(Cliente.class, lerInteger(row.getCell(2))));
            pag.setMarketplace(em.getReference(Marketplace.class, lerInteger(row.getCell(3))));

            // Data
            Cell cellData = row.getCell(4);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                pag.setDataPagamento(data);
            }

            pag.setValorPago(lerDouble(row.getCell(5)));
            pag.setFormaPagamento(row.getCell(6).getStringCellValue());
            pag.setStatusPagamento(row.getCell(7).getStringCellValue());
            pag.setTaxaProcessamento(lerDouble(row.getCell(8)));
            pag.setScoreRiscoFraude(lerDouble(row.getCell(9)));

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
            log.setPedido(em.getReference(Pedido.class, lerInteger(row.getCell(1))));
            log.setMarketplace(em.getReference(Marketplace.class, lerInteger(row.getCell(2))));

            // Data
            Cell cellData = row.getCell(3);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                log.setDataEnvio(data);
            }

            log.setModalidadeEntrega(row.getCell(4).getStringCellValue());
            log.setPrazoPrometidoDias(lerInteger(row.getCell(5)));
            log.setPrazoRealDias(lerInteger(row.getCell(6)));

            // Booleano
            log.setEntregaAtrasada(lerBooleanoSimNao(row.getCell(7)));

            log.setCustoLogistico(lerDouble(row.getCell(8)));
            log.setScoreRiscoAtraso(lerDouble(row.getCell(9)));

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

            camp.setMarketplace(em.getReference(Marketplace.class, lerInteger(row.getCell(1))));

            // Data
            Cell cellData = row.getCell(2);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                camp.setDataReferencia(data);
            }

            camp.setCanalMarketing(row.getCell(3).getStringCellValue());
            camp.setCategoriaFoco(row.getCell(4).getStringCellValue());
            camp.setInvestimentoMarketing(lerDouble(row.getCell(5)));
            camp.setVisitas(lerInteger(row.getCell(6)));
            camp.setPedidosGerados(lerInteger(row.getCell(7)));
            camp.setTaxaConversaoPercentual(lerDouble(row.getCell(8)));
            camp.setRoas(lerDouble(row.getCell(9)));
            camp.setProbabilidadeConversao(lerDouble(row.getCell(10)));

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

            fin.setMarketplace(em.getReference(Marketplace.class, lerInteger(row.getCell(1))));

            // Data
            Cell cellData = row.getCell(2);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                fin.setDataReferencia(data);
            }

            fin.setCompradoresAtivos(lerInteger(row.getCell(3)));
            fin.setGmv(lerDouble(row.getCell(4)));
            fin.setReceitaComissao(lerDouble(row.getCell(5)));
            fin.setReceitaAds(lerDouble(row.getCell(6)));
            fin.setReceitaLogistica(lerDouble(row.getCell(7)));
            fin.setCustoOperacional(lerDouble(row.getCell(8)));
            fin.setCac(lerDouble(row.getCell(9)));
            fin.setLtv(lerDouble(row.getCell(10)));
            fin.setTaxaChurnPercentual(lerDouble(row.getCell(11)));
            fin.setResultadoOperacional(lerDouble(row.getCell(12)));

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

            at.setCliente(em.getReference(Cliente.class, lerInteger(row.getCell(1))));
            at.setMarketplace(em.getReference(Marketplace.class, lerInteger(row.getCell(2))));

            // Data
            Cell cellData = row.getCell(3);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                at.setDataAtendimento(data);
            }

            at.setCanalAtendimento(row.getCell(4).getStringCellValue());
            at.setAssunto(row.getCell(5).getStringCellValue());
            at.setTempoAtendimentoMin(lerDouble(row.getCell(6)));

            // Booleano
            at.setResolvidoPrimeiroContato(lerBooleanoSimNao(row.getCell(7)));

            at.setNotaNps(lerInteger(row.getCell(8)));
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

    // --- METODO AUXILIAR PARA DECIMAIS (DOUBLE) ---
    private static Double lerDouble(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return 0.0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        if (cell.getCellType() == CellType.STRING) {
            try {
                String valor = cell.getStringCellValue().replace(",", ".").trim();
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    // --- METODO AUXILIAR PARA INTEIROS (INTEGER) ---
    private static Integer lerInteger(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        if (cell.getCellType() == CellType.STRING) {
            try {
                String valor = cell.getStringCellValue().trim();
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}