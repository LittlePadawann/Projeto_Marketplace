package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.Produto;
import projeto.marketplace.infraestrutura.service.MarketplaceService;
import projeto.marketplace.infraestrutura.service.ProdutoService;
import projeto.marketplace.infraestrutura.service.VendedorService;

@Controller
@RequestMapping("/produtos")
public class ProdutoWebController {

    @Autowired private ProdutoService service;
    @Autowired private VendedorService vendedorService;
    @Autowired private MarketplaceService marketplaceService;

    private void carregarDependencias(Model model) {
        model.addAttribute("vendedores", vendedorService.listarTodos());
        model.addAttribute("marketplaces", marketplaceService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        return "produto/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        carregarDependencias(model);
        return "produto/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        carregarDependencias(model);
        return "produto/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        service.salvar(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este Produto pois existem pedidos vinculados a ele.");
        }
        return "redirect:/produtos";
    }
}