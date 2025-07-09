package br.com.pholiveira.Controle.de.Estoque.model.enuns;

public enum Localizacao {

    DATA_CENTER("DATA_CENTER"),
    ARMARIO_SUPORTE("ARMARIO_SUPORTE"),
    INDEFINIDA("INDEFINIDA");

    private final String descricao;

    Localizacao(String localizacao) {
            this.descricao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }
}
