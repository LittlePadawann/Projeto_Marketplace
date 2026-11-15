package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.Pedido;
import projeto.marketplace.infraestrutura.service.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoWebController {

    @Autowired private PedidoService service;
    @Autowired private ClienteService clienteService;
    @Autowired private VendedorService vendedorService;
    @Autowired private ProdutoService produtoService;
    @Autowired private MarketplaceService marketplaceService;

    private void carregarDependencias(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("vendedores", vendedorService.listarTodos());
        model.addAttribute("produtos", produtoService.listarTodos());
        model.addAttribute("marketplaces", marketplaceService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", service.listarTodos());
        return "pedido/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pedido", new Pedido());
        carregarDependencias(model);
        return "pedido/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("pedido", service.buscarPorId(id));
        carregarDependencias(model);
        return "pedido/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pedido pedido) {
        service.salvar(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Pedido excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este Pedido pois existem registros de pagamento ou logística vinculados a ele.");
        }
        return "redirect:/pedidos";
    }
}