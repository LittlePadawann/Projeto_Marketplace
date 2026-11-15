package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.Cliente;
import projeto.marketplace.infraestrutura.service.ClienteService;
import projeto.marketplace.infraestrutura.service.MarketplaceService;

@Controller
@RequestMapping("/clientes")
public class ClienteWebController {

    @Autowired private ClienteService service;
    @Autowired private MarketplaceService marketplaceService;

    private void carregarDependencias(Model model) {
        model.addAttribute("marketplaces", marketplaceService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listarTodos());
        return "cliente/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        carregarDependencias(model);
        return "cliente/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id));
        carregarDependencias(model);
        return "cliente/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        service.salvar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este Cliente pois existem pedidos, pagamentos ou atendimentos vinculados a ele.");
        }
        return "redirect:/clientes";
    }
}