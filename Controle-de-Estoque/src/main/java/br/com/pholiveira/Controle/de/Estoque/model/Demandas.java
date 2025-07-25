package br.com.pholiveira.Controle.de.Estoque.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.pholiveira.Controle.de.Estoque.model.enuns.Prioridade;
import br.com.pholiveira.Controle.de.Estoque.model.enuns.Status;

@Entity
@Table(name = "demandas")
public class Demandas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Prioridade prioridade;
    private boolean infra;
    private String idHelp;
    private Status status;
    private String local;
    private String descricao;
    private String responsavel;
    private String tecnicoUnipel;
    private LocalDate dataInfra;
    private LocalDate dataTerceiro;
    private LocalDateTime agendamentoTerceiro;
    private String tecnicoTerceiro;
    private String respostaHelp;
    private String observacoes;


    public Demandas() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Prioridade getPrioridade() {
        return prioridade;
    }


    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }


    public boolean isInfra() {
        return infra;
    }


    public void setInfra(boolean infra) {
        this.infra = infra;
    }


    public String getIdHelp() {
        return idHelp;
    }


    public void setIdHelp(String idHelp) {
        this.idHelp = idHelp;
    }


    public Status getStatus() {
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public String getLocal() {
        return local;
    }


    public void setLocal(String local) {
        this.local = local;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getResponsavel() {
        return responsavel;
    }


    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }


    public String getTecnicoUnipel() {
        return tecnicoUnipel;
    }


    public void setTecnicoUnipel(String tecnicoUnipel) {
        this.tecnicoUnipel = tecnicoUnipel;
    }


    public LocalDate getDataInfra() {
        return dataInfra;
    }


    public void setDataInfra(LocalDate dataInfra) {
        this.dataInfra = dataInfra;
    }


    public LocalDate getDataTerceiro() {
        return dataTerceiro;
    }


    public void setDataTerceiro(LocalDate dataTerceiro) {
        this.dataTerceiro = dataTerceiro;
    }


    public LocalDateTime getAgendamentoTerceiro() {
        return agendamentoTerceiro;
    }


    public void setAgendamentoTerceiro(LocalDateTime agendamentoTerceiro) {
        this.agendamentoTerceiro = agendamentoTerceiro;
    }


    public String getTecnicoTerceiro() {
        return tecnicoTerceiro;
    }


    public void setTecnicoTerceiro(String tecnicoTerceiro) {
        this.tecnicoTerceiro = tecnicoTerceiro;
    }


    public String getRespostaHelp() {
        return respostaHelp;
    }


    public void setRespostaHelp(String respostaHelp) {
        this.respostaHelp = respostaHelp;
    }


    public String getObservacoes() {
        return observacoes;
    }


    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    



}
