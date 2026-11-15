package projeto.marketplace.infraestrutura.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projeto.marketplace.infraestrutura.service.DashboardService;

@Controller
public class DashboardWebController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping({"/", "/dashboard"})
    public String index(Model model) {
        model.addAttribute("metricas", dashboardService.obterMetricasGerais());
        return "index"; // Aponta para templates/index.html
    }
}