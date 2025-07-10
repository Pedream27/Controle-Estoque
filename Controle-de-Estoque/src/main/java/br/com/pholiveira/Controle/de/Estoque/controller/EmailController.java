package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.model.DTOs.EmailDto;
import br.com.pholiveira.Controle.de.Estoque.model.DTOs.SolicitacaoCompraDTO;
import br.com.pholiveira.Controle.de.Estoque.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarEmail(@RequestBody EmailDto emailDto) {
        emailService.enviarEmailSimples(emailDto);
        return "Email enviado com sucesso para " ;
    }


    @PostMapping("/compras")
    public ResponseEntity AutorizarEmail(@RequestBody SolicitacaoCompraDTO solicitacao) {
        emailService.solicitacaoDeCompra(solicitacao);
        return ResponseEntity.ok().build();
    }
}
