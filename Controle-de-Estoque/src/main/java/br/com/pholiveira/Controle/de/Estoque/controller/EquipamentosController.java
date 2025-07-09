package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos;
import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import br.com.pholiveira.Controle.de.Estoque.model.RequestAddEquipamento;
import br.com.pholiveira.Controle.de.Estoque.services.EquipamentosServices;
import br.com.pholiveira.Controle.de.Estoque.services.ProcessamentoExcel;
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

    // Upload da planilha para o banco de dados
    @PostMapping("/planilha") // Mapeia requisições POST para /api/upload/planilha
    public ResponseEntity<String> uploadPlanilha(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Por favor, selecione um arquivo para upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            _processamentoExcel.processarUploadPlanilha(file);
            return new ResponseEntity<>("Planilha carregada e dados salvos com sucesso!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Logar o erro completo para depuração
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao processar a planilha: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadCSV() throws IOException {
        return _processamentoExcel.exportToCSV();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Equipamentos>> buscarPorNome(@RequestParam String nome) {
        List<Equipamentos> equipamentos = equipamentosService.buscarPorNome(nome);
        return ResponseEntity.ok(equipamentos);
    }

    }

