package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.Marketplace;
import projeto.marketplace.infraestrutura.service.MarketplaceService;

@Controller
@RequestMapping("/marketplaces")
public class MarketplaceWebController {

    @Autowired
    private MarketplaceService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("marketplaces", service.listarTodos());
        return "marketplace/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("marketplace", new Marketplace());
        return "marketplace/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("marketplace", service.buscarPorId(id));
        return "marketplace/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Marketplace marketplace) {
        service.salvar(marketplace);
        return "redirect:/marketplaces";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Marketplace excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este Marketplace pois existem clientes, vendedores ou pedidos vinculados a ele.");
        }
        return "redirect:/marketplaces";
    }
}