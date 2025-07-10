package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos;
import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import br.com.pholiveira.Controle.de.Estoque.model.RequestAddEquipamento;
import br.com.pholiveira.Controle.de.Estoque.services.EquipamentosServices;
import br.com.pholiveira.Controle.de.Estoque.services.ProcessamentoExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos.addHateos;
import static br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos.addHateosReturn;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentosController {

    private final EquipamentosServices equipamentosService;
    private final ProcessamentoExcel _processamentoExcel;
    private static final Logger log = LoggerFactory.getLogger(PlanilhaController.class);

    public EquipamentosController(EquipamentosServices equipamentosService, ProcessamentoExcel processamentoExcel) {
        this.equipamentosService = equipamentosService;
        _processamentoExcel = processamentoExcel;
    }

    // Endpoint para criar um novo equipamento
    @PostMapping
    public ResponseEntity<Equipamentos> criarEquipamento(@RequestBody Equipamentos equipamento) {
        Equipamentos novoEquipamento = equipamentosService.criarEquipamento(equipamento);
        addHateos(novoEquipamento);
        return new ResponseEntity<>(novoEquipamento, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os equipamentos
    @GetMapping
    public ResponseEntity<Page<Equipamentos>> listarEquipamentosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Equipamentos> equipamentosPage = equipamentosService.listarTodosEquipamentos(pageable);

        equipamentosPage.forEach(EquipamentosHateos::addHateos);

        return new ResponseEntity<>(equipamentosPage, HttpStatus.OK);
    }

    // Endpoint para buscar equipamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipamentos> buscarEquipamentoPorId(@PathVariable Long id) {
        return equipamentosService.buscarEquipamentoPorId(id)
                .map(equipamento -> new ResponseEntity<>(addHateosReturn(equipamento), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para atualizar um equipamento
    @PutMapping("/{id}")
    public ResponseEntity<Equipamentos> atualizarEquipamento(@PathVariable Long id, @RequestBody Equipamentos equipamento) {
        try {
            Equipamentos equipamentoAtualizado = equipamentosService.atualizarEquipamento(id, equipamento);
            return new ResponseEntity<>(addHateosReturn(equipamentoAtualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para deletar um equipamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEquipamento(@PathVariable Long id) {
        try {
            equipamentosService.deletarEquipamento(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Busca por nome todo: não esta funcionando
    @GetMapping("/buscar")
    public ResponseEntity<List<Equipamentos>> buscarPorNome(@RequestParam String nome) {
        List<Equipamentos> equipamentos = equipamentosService.buscarPorNome(nome);
        return ResponseEntity.ok(equipamentos);
    }
}


