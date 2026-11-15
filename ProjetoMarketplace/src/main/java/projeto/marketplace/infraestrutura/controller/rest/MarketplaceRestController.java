package projeto.marketplace.infraestrutura.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.marketplace.infraestrutura.model.Marketplace;
import projeto.marketplace.infraestrutura.service.MarketplaceService;

import java.util.List;

@RestController
@RequestMapping("/api/marketplaces")
public class MarketplaceRestController {

    @Autowired
    private MarketplaceService service;

    @GetMapping
    public List<Marketplace> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marketplace> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Marketplace> criar(@RequestBody Marketplace marketplace) {
        return ResponseEntity.ok(service.salvar(marketplace));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marketplace> atualizar(@PathVariable Integer id, @RequestBody Marketplace dados) {
        Marketplace existente = service.buscarPorId(id);
        existente.setNomeMarketplace(dados.getNomeMarketplace());
        existente.setSegmento(dados.getSegmento());
        existente.setCidadeSede(dados.getCidadeSede());
        existente.setEstado(dados.getEstado());
        existente.setRegiao(dados.getRegiao());
        return ResponseEntity.ok(service.salvar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}