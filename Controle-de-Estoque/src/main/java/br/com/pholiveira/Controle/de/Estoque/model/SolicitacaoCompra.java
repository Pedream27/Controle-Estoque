package br.com.pholiveira.Controle.de.Estoque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SolicitacaoCompra {

    private String nomeSolicitante;
    private String motivo;
    private String produto;
    private String valor;
    private String emailContato;
    @Id
    private Long id;

    // Getters e Setters
    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
