package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.services.ProcessamentoExcel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller("/api/equipamentos")
public class PlanilhaController {


    private final ProcessamentoExcel _processamentoExcel;

    public PlanilhaController(ProcessamentoExcel processamentoExcel) {
        _processamentoExcel = processamentoExcel;
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
    // Faz Dowload do Banco de dados dos equipamentos em CSV
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadCSV() throws IOException {
        return _processamentoExcel.exportToCSV();
    }
}
