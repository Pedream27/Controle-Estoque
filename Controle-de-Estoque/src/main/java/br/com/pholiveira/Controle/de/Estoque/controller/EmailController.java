package br.com.pholiveira.Controle.de.Estoque.controller;

import br.com.pholiveira.Controle.de.Estoque.model.DTOs.EmailDto;
import br.com.pholiveira.Controle.de.Estoque.model.DTOs.SolicitacaoCompraDTO;
import br.com.pholiveira.Controle.de.Estoque.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    // Logger para registrar informações e erros
    private static Logger logger = LoggerFactory.getLogger(EmailController.class);

    @PostMapping("/enviar")
    public String enviarEmail(@RequestBody EmailDto emailDto) {
        logger.info("Iniciando o envio de email para: {}", emailDto.solicitante());
        // Chama o serviço de email para enviar o email simples
        emailService.enviarEmailSimples(emailDto);
        return "Email enviado com sucesso para " ;
    }


    @PostMapping("/compras")
    public ResponseEntity AutorizarEmail(@RequestBody SolicitacaoCompraDTO solicitacao) {
        logger.info("Iniciando o processo de autorização de compra para: {}", solicitacao.nomeSolicitante());
        // Chama o serviço de email para enviar a solicitação de compra
        emailService.solicitacaoDeCompra(solicitacao);
        return ResponseEntity.ok().build();
    }
}
