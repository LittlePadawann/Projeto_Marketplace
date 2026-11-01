package projeto.marketplace;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import projeto.marketplace.model.*;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class ImportadorExcel implements CommandLineRunner {

    // O Spring injeta o EntityManager automaticamente
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional // Garante que tudo rode dentro de uma transação do Spring
    public void run(String... args) throws Exception {
        String arquivo = "08_marketplaces.xlsx";

        try (FileInputStream fis = new FileInputStream(arquivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            System.out.println("Importando Marketplaces...");
            importarMarketplaces(workbook);
            em.flush(); em.clear(); // Salva e limpa a memória

            System.out.println("Importando Clientes e Vendedores...");
            importarClientes(workbook);
            importarVendedores(workbook);
            importarProdutos(workbook);
            em.flush(); em.clear();

            System.out.println("Importando Pedidos...");
            importarPedidos(workbook);
            em.flush(); em.clear();

            System.out.println("Importando Tabelas Auxiliares...");
            importarPagamentos(workbook);
            importarLogistica(workbook);
            importarAtendimento(workbook);

            System.out.println("Importação concluída com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Tabela Marketplace
    private void importarMarketplaces(Workbook workbook) {
        Sheet sheet = workbook.getSheet("Marketplaces");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Marketplace mk = new Marketplace();
            mk.setNomeMarketplace(lerString(row.getCell(1)));
            mk.setSegmento(lerString(row.getCell(2)));
            mk.setCidadeSede(lerString(row.getCell(3)));
            mk.setEstado(lerString(row.getCell(4)));
            mk.setRegiao(lerString(row.getCell(5)));

            // Lendo Double e Integer com os novos métodos seguros
            mk.setLatitude(lerDouble(row.getCell(6)));
            mk.setLongitude(lerDouble(row.getCell(7)));
            mk.setAnoFundacao(lerInteger(row.getCell(8)));
            mk.setPorte(lerString(row.getCell(9)));
            mk.setModeloNegocio(lerString(row.getCell(10)));
            mk.setGmvAnualEstimado(lerDouble(row.getCell(11)));
            mk.setTakeRateMedioPercentual(lerDouble(row.getCell(12)));

            em.persist(mk);

            // OTIMIZAÇÃO DE MEMÓRIA (Aplique isso em TODOS os métodos de importação)
            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 2. Tabela Clientes (Depende de Marketplace)
    private void importarClientes(Workbook workbook) {
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

            cliente.setCidade(lerString(row.getCell(2)));
            cliente.setEstado(lerString(row.getCell(3)));
            cliente.setRegiao(lerString(row.getCell(4)));
            cliente.setLatitude(lerDouble(row.getCell(5)));
            cliente.setLongitude(lerDouble(row.getCell(6)));
            cliente.setFaixaEtaria(lerString(row.getCell(7)));
            cliente.setFaixaRenda(lerString(row.getCell(8)));
            cliente.setCanalAquisicao(lerString(row.getCell(9)));

            // Lendo Data
            Cell cellData = row.getCell(10);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                cliente.setDataCadastro(data);
            }

            cliente.setStatusCliente(lerString(row.getCell(11)));
            cliente.setScoreChurn(lerDouble(row.getCell(12)));
            cliente.setIndiceFidelidade(lerDouble(row.getCell(13)));

            em.persist(cliente);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 3. Tabela Vendedores (Depende de Marketplace)
    private void importarVendedores(Workbook workbook) {
        Sheet sheet = workbook.getSheet("Vendedores");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Vendedor vendedor = new Vendedor();

            // FK: Marketplace
            int mkId = lerInteger(row.getCell(1));
            vendedor.setMarketplace(em.getReference(Marketplace.class, mkId));

            vendedor.setCidade(lerString(row.getCell(2)));
            vendedor.setEstado(lerString(row.getCell(3)));
            vendedor.setRegiao(lerString(row.getCell(4)));
            vendedor.setLatitude(lerDouble(row.getCell(5)));
            vendedor.setLongitude(lerDouble(row.getCell(6)));
            vendedor.setTipoVendedor(lerString(row.getCell(7)));
            vendedor.setCategoriaPrincipal(lerString(row.getCell(8)));

            // Lendo Data
            Cell cellData = row.getCell(9);
            if (cellData != null && cellData.getCellType() == CellType.NUMERIC) {
                LocalDate data = cellData.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                vendedor.setDataEntrada(data);
            }

            vendedor.setStatusVendedor(lerString(row.getCell(10)));
            vendedor.setReputacaoMedia(lerDouble(row.getCell(11)));
            vendedor.setScoreRiscoSuspensao(lerDouble(row.getCell(12)));

            em.persist(vendedor);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 4. Tabela Produtos (Depende de Vendedor e Marketplace)
    private void importarProdutos(Workbook workbook) {
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

            produto.setNomeProduto(lerString(row.getCell(3)));
            produto.setCategoriaProduto(lerString(row.getCell(4)));
            produto.setPrecoProduto(lerDouble(row.getCell(5)));
            produto.setEstoqueAtual(lerInteger(row.getCell(6)));
            produto.setAvaliacaoMedia(lerDouble(row.getCell(7)));
            produto.setStatusProduto(lerString(row.getCell(8)));
            produto.setMargemEstimadaPercentual(lerDouble(row.getCell(9)));
            produto.setScoreRiscoRuptura(lerDouble(row.getCell(10)));

            em.persist(produto);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 5. Tabela Pedidos (Depende de Cliente, Vendedor, Produto e Marketplace)
    private void importarPedidos(Workbook workbook) {
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

            pedido.setStatusPedido(lerString(row.getCell(6)));
            pedido.setQuantidadeItens(lerInteger(row.getCell(7)));
            pedido.setValorItens(lerDouble(row.getCell(8)));
            pedido.setDesconto(lerDouble(row.getCell(9)));
            pedido.setValorFrete(lerDouble(row.getCell(10)));
            pedido.setValorTotal(lerDouble(row.getCell(11)));
            pedido.setReceitaComissao(lerDouble(row.getCell(12)));
            pedido.setCanalVenda(lerString(row.getCell(13)));
            pedido.setAvaliacaoCliente(lerInteger(row.getCell(14)));
            pedido.setProbabilidadeRecompra(lerDouble(row.getCell(15)));

            em.persist(pedido);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 6. Tabela Pagamentos (Depende de Pedido, Cliente e Marketplace)
    private void importarPagamentos(Workbook workbook) {
        Sheet sheet = workbook.getSheet("Pagamentos");
        if (sheet == null) return;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Pagamento pag = new Pagamento();

            // Lendo as 2 FKs
            pag.setPedido(em.getReference(Pedido.class, lerInteger(row.getCell(1))));
            pag.setCliente(em.getReference(Cliente.class, lerInteger(row.getCell(2))));

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

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 7. Tabela Logistica_Entregas (Depende de Pedido e Marketplace)
    private void importarLogistica(Workbook workbook) {
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

            log.setModalidadeEntrega(lerString(row.getCell(4)));
            log.setPrazoPrometidoDias(lerInteger(row.getCell(5)));
            log.setPrazoRealDias(lerInteger(row.getCell(6)));

            // Booleano
            log.setEntregaAtrasada(lerBooleanoSimNao(row.getCell(7)));

            log.setCustoLogistico(lerDouble(row.getCell(8)));
            log.setScoreRiscoAtraso(lerDouble(row.getCell(9)));

            // Booleano
            log.setDevolucaoLogistica(lerBooleanoSimNao(row.getCell(10)));

            em.persist(log);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    // 8. Tabela Atendimento_Satisfacao (Depende de Cliente e Marketplace)
    private void importarAtendimento(Workbook workbook) {
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

            at.setCanalAtendimento(lerString(row.getCell(4)));
            at.setAssunto(lerString(row.getCell(5)));
            at.setTempoAtendimentoMin(lerDouble(row.getCell(6)));

            // Booleano
            at.setResolvidoPrimeiroContato(lerBooleanoSimNao(row.getCell(7)));

            at.setNotaNps(lerInteger(row.getCell(8)));
            at.setCategoriaNps(lerString(row.getCell(9)));

            em.persist(at);

            if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
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

    // --- METODO AUXILIAR PARA STRINGS ---
    private String lerString(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}