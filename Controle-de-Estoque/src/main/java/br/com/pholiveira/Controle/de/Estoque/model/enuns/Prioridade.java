package br.com.pholiveira.Controle.de.Estoque.model.enuns;

public enum Prioridade {

    ALTA,
    MEDIA,
    BAIXA;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
