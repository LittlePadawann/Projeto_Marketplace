package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.Vendedor;
import projeto.marketplace.infraestrutura.service.MarketplaceService;
import projeto.marketplace.infraestrutura.service.VendedorService;

@Controller
@RequestMapping("/vendedores")
public class VendedorWebController {

    @Autowired private VendedorService service;
    @Autowired private MarketplaceService marketplaceService;

    private void carregarDependencias(Model model) {
        model.addAttribute("marketplaces", marketplaceService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("vendedores", service.listarTodos());
        return "vendedor/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("vendedor", new Vendedor());
        carregarDependencias(model);
        return "vendedor/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("vendedor", service.buscarPorId(id));
        carregarDependencias(model);
        return "vendedor/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Vendedor vendedor) {
        service.salvar(vendedor);
        return "redirect:/vendedores";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Vendedor excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este Vendedor pois existem produtos ou pedidos vinculados a ele.");
        }
        return "redirect:/vendedores";
    }
}