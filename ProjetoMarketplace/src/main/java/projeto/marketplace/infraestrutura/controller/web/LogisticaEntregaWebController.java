package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.LogisticaEntrega;
import projeto.marketplace.infraestrutura.service.LogisticaEntregaService;
import projeto.marketplace.infraestrutura.service.MarketplaceService;
import projeto.marketplace.infraestrutura.service.PedidoService;

@Controller
@RequestMapping("/logistica")
public class LogisticaEntregaWebController {

    @Autowired private LogisticaEntregaService service;
    @Autowired private PedidoService pedidoService;
    @Autowired private MarketplaceService marketplaceService;

    private void carregarDependencias(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        model.addAttribute("marketplaces", marketplaceService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entregas", service.listarTodos());
        return "logistica/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("entrega", new LogisticaEntrega());
        carregarDependencias(model);
        return "logistica/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("entrega", service.buscarPorId(id));
        carregarDependencias(model);
        return "logistica/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute LogisticaEntrega entrega) {
        service.salvar(entrega);
        return "redirect:/logistica";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Registro logístico excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este registro de Logística devido a vínculos no sistema.");
        }
        return "redirect:/logistica";
    }
}