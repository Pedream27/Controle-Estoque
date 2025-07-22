package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos;
import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import br.com.pholiveira.Controle.de.Estoque.services.EquipamentosServices;
import br.com.pholiveira.Controle.de.Estoque.services.ProcessamentoExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

import static br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos.addHateos;
import static br.com.pholiveira.Controle.de.Estoque.hateos.EquipamentosHateos.addHateosReturn;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentosController {

    private final EquipamentosServices equipamentosService;
    private static final Logger log = LoggerFactory.getLogger(PlanilhaController.class);

    public EquipamentosController(EquipamentosServices equipamentosService) {
        this.equipamentosService = equipamentosService;
       
    }

    // Endpoint para criar um novo equipamento
    @PostMapping
    public ResponseEntity<Equipamentos> criarEquipamento(@RequestBody Equipamentos equipamento) {
        log.info("Criando novo equipamento: {}" + equipamento.getNomeCadastradoTasy() + " " +equipamento.getTipoEquipamento() +" " +equipamento.getMarca()+ " " + equipamento.getModelo()+"" +equipamento.getLocalizacao()+ "" +equipamento.getQntEstoque() + " "+ equipamento.getQntFuncionando()+" "+ equipamento.getQntInoperante() +" " +equipamento.getURLImagem());
        Equipamentos novoEquipamento = equipamentosService.criarEquipamento(equipamento);
        return new ResponseEntity<>(novoEquipamento, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os equipamentos
    @GetMapping
    public ResponseEntity<Page<Equipamentos>> listarEquipamentosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Listando equipamentos - Página: {}, Tamanho: {}", page, size);
        // Configura a paginação
        Pageable pageable = PageRequest.of(page, size);
        Page<Equipamentos> equipamentosPage = equipamentosService.listarTodosEquipamentos(pageable);

        equipamentosPage.forEach(EquipamentosHateos::addHateos);

        return new ResponseEntity<>(equipamentosPage, HttpStatus.OK);
    }

    // Endpoint para buscar equipamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipamentos> buscarEquipamentoPorId(@PathVariable Long id) {
        log.info("Buscando equipamento com ID: {}", id);
        // Busca o equipamento pelo ID e adiciona os links HATEOAS
        return equipamentosService.buscarEquipamentoPorId(id)
                .map(equipamento -> new ResponseEntity<>(addHateosReturn(equipamento), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para atualizar um equipamento
    @PutMapping("/{id}")
    public ResponseEntity<Equipamentos> atualizarEquipamento(@PathVariable Long id, @RequestBody Equipamentos equipamento) {
        log.info("Atualizando equipamento com ID: {}", id);
        // Verifica se o equipamento existe e atualiza
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
        log.info("Deletando equipamento com ID: {}", id);
        // Tenta deletar o equipamento e retorna o status apropriado
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
        log.info("Buscando equipamentos por nome: {}", nome);
        // Busca equipamentos pelo nome e retorna a lista
        List<Equipamentos> equipamentos = equipamentosService.buscarPorNome(nome);
        return ResponseEntity.ok(equipamentos);
    }

    @PutMapping("/{id}/imagem")
    public ResponseEntity<Equipamentos> atualizarImagem(
        @PathVariable Long id,
        @RequestParam("url") String urlImagem
    ) {
        try {
            Equipamentos eq = equipamentosService.atualizarUrlImagem(id, urlImagem);
            return ResponseEntity.ok(addHateosReturn(eq));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

