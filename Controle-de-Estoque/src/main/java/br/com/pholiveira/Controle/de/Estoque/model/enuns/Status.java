package br.com.pholiveira.Controle.de.Estoque.model.enuns;

public enum Status {   

    PENDENTE,
    EM_ANALISE,
    EM_ANDAMENTO,
    EM_EXECUCAO,
    AGUARDANDO_TERCEIRO,
    CONCLUIDA,
    CANCELADA;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
