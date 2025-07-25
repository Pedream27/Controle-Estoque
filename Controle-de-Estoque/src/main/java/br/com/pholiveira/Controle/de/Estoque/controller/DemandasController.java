package br.com.pholiveira.Controle.de.Estoque.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.pholiveira.Controle.de.Estoque.model.Demandas;
import br.com.pholiveira.Controle.de.Estoque.services.DemandaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos/demandas")
public class DemandasController {

    @Autowired
    private DemandaService service;

    @PostMapping
    public ResponseEntity<Demandas> criar(@RequestBody Demandas demanda) {
        Demandas d = service.criarDemanda(demanda);
        return ResponseEntity.status(201).body(d);
    }

    @GetMapping
    public List<Demandas> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demandas> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Demandas> atualizar(@PathVariable Long id, @RequestBody Demandas demanda) {
        try {
            Demandas atualizada = service.atualizarDemanda(id, demanda);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletarDemanda(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
