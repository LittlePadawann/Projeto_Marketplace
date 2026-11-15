package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projeto.marketplace.infraestrutura.model.Pagamento;
import projeto.marketplace.infraestrutura.service.ClienteService;
import projeto.marketplace.infraestrutura.service.PagamentoService;
import projeto.marketplace.infraestrutura.service.PedidoService;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoWebController {

    @Autowired private PagamentoService service;
    @Autowired private PedidoService pedidoService;
    @Autowired private ClienteService clienteService;

    private void carregarDependencias(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pagamentos", service.listarTodos());
        return "pagamento/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pagamento", new Pagamento());
        carregarDependencias(model);
        return "pagamento/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("pagamento", service.buscarPorId(id));
        carregarDependencias(model);
        return "pagamento/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pagamento pagamento) {
        service.salvar(pagamento);
        return "redirect:/pagamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Pagamento excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este registro de Pagamento devido a vínculos no sistema.");
        }
        return "redirect:/pagamentos";
    }
}