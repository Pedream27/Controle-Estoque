package br.com.pholiveira.Controle.de.Estoque.model;

public class RequestAddEquipamento {

    private Long id;
    private int quantidade;

    public RequestAddEquipamento(Long id, int quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public RequestAddEquipamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
