package br.com.pholiveira.Controle.de.Estoque.services;

import br.com.pholiveira.Controle.de.Estoque.model.DTOs.EmailDto;
import br.com.pholiveira.Controle.de.Estoque.model.DTOs.SolicitacaoCompraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private  JavaMailSender mailSender;

    public void enviarEmailSimples(EmailDto emailDto) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("pholiveira@unimedpelotas.com.br");
        mensagem.setSubject(emailDto.assunto());
        mensagem.setText(emailDto.corpo());
        mensagem.setFrom(emailDto.solicitante());

        mailSender.send(mensagem);
    }

    public void enviarEmailParaAvisarQueEstamosSemEstoque(EmailDto emailDto) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("E-MAIL INFRA");
        mensagem.setSubject("Falta de Material no Estoque");
        mensagem.setText(emailDto.assunto());
        mensagem.setFrom(emailDto.solicitante());

        mailSender.send(mensagem);

    }

    public void solicitacaoDeCompra(SolicitacaoCompraDTO solicitacao) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("E-MAIL TESTE");
        mensagem.setSubject(emailDto.assunto());
        mensagem.setText(emailDto.corpo());
        mensagem.setFrom(emailDto.solicitante());

        mailSender.send(mensagem);
    }
}
