package br.com.pholiveira.Controle.de.Estoque.model.DTOs;

public record  SolicitacaoCompraDTO(String nomeSolicitante,
                                    String emailSolicitante,
                                    String nomeEquipamento,
                                    String quantidadeDesejada,
                                    String motivo,
                                    String valorEstimado) {

}
