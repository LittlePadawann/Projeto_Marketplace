package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.AtendimentoSatisfacao;
import projeto.marketplace.infraestrutura.service.AtendimentoSatisfacaoService;
import projeto.marketplace.infraestrutura.service.ClienteService;
import projeto.marketplace.infraestrutura.service.MarketplaceService;

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoSatisfacaoWebController {

    @Autowired private AtendimentoSatisfacaoService service;
    @Autowired private ClienteService clienteService;
    @Autowired private MarketplaceService marketplaceService;

    private void carregarDependencias(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("marketplaces", marketplaceService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("atendimentos", service.listarTodos());
        return "atendimento/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("atendimento", new AtendimentoSatisfacao());
        carregarDependencias(model);
        return "atendimento/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("atendimento", service.buscarPorId(id));
        carregarDependencias(model);
        return "atendimento/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute AtendimentoSatisfacao atendimento) {
        service.salvar(atendimento);
        return "redirect:/atendimentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Atendimento excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este Atendimento devido a vínculos no sistema.");
        }
        return "redirect:/atendimentos";
    }
}