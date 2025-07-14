package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.services.ProcessamentoExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("api/equipamentos")
public class PlanilhaController {


    private static final Logger log = LoggerFactory.getLogger(PlanilhaController.class);
    private final ProcessamentoExcel _processamentoExcel;

    public PlanilhaController(ProcessamentoExcel processamentoExcel) {
        _processamentoExcel = processamentoExcel;
    }

    // Upload da planilha para o banco de dados
    @PostMapping("/planilha") // Mapeia requisições POST para /api/upload/planilha
    public ResponseEntity<String> uploadPlanilha(@RequestParam("file") MultipartFile file) {
        log.info("Iniciando o upload da planilha: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return new ResponseEntity<>("Por favor, selecione um arquivo para upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            _processamentoExcel.processarUploadPlanilha(file);
            log.warn("Planilha Processado com sucesso!");
            return new ResponseEntity<>("Planilha carregada e dados salvos com sucesso!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.warn(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao processar a planilha: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Faz Dowload do Banco de dados dos equipamentos em CSV
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadCSV() throws IOException {
        log.info("Iniciando o download dos equipamentos em CSV");
        return _processamentoExcel.exportToCSV();
    }
}
