package br.com.pholiveira.Controle.de.Estoque.services;

import br.com.pholiveira.Controle.de.Estoque.model.EmailDto;
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
        mensagem.setText(emailDto.assunto());
        mensagem.setFrom(emailDto.solicitante());

        mailSender.send(mensagem);
    }

    public void enviarEmailParaAvisarQueEstamosSemEstoque(EmailDto emailDto) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo("infra.ti@unimedpelotas.com.br");
        mensagem.setSubject("Falta de Material no Estoque");
        mensagem.setText(emailDto.assunto());
        mensagem.setFrom(emailDto.solicitante());

        mailSender.send(mensagem);

    }
}
