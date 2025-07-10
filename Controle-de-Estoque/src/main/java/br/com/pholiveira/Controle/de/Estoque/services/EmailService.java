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
        mensagem.setTo("E-MAIL DE PRODUTO");
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
        mensagem.setSubject("Solicitação de Compra de Equipamento");
        mensagem.setText(
                "Venho por meio deste solicitar a aquisição do seguinte equipamento:\n" +
                        "\n" +
                        "Equipamento: " + solicitacao.nomeEquipamento() + " \n" +
                        "Quantidade: "+  solicitacao.quantidadeDesejada() + " \n" +
                        "Finalidade: "+ solicitacao.motivo() + "\n" +
                        "Valor estimado: "+ solicitacao.valorEstimado() + "\n" +
                        "\n" +
                        "Essa solicitação se faz necessária para atender às demandas do setor de forma mais eficiente, garantindo a continuidade das atividades com qualidade e segurança.\n" +
                        "\n" +
                        "Agradeço pela atenção e fico à disposição para qualquer esclarecimento adicional.\n" +
                        "\n" +
                        "Atenciosamente,\n" +
                        solicitacao.nomeSolicitante()

        );
        mensagem.setFrom(solicitacao.emailSolicitante());

        mailSender.send(mensagem);
    }
}
