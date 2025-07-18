package br.com.pholiveira.Controle.de.Estoque.model;

import br.com.pholiveira.Controle.de.Estoque.model.enuns.Localizacao;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "tb_Equipamentos")
public class Equipamentos extends RepresentationModel<Equipamentos> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomeCadastradoTasy;
    private String tipoEquipamento;
    private String marca;
    private String modelo;
    private Localizacao localizacao;
    private int qntEstoque;
    private int qntFuncionando;
    private int qntInoperante;
    private String URLImagem;

    public Equipamentos() {
    }

    public Equipamentos(String nomeCadastradoTasy, String tipoEquipamento, String marca, Localizacao localizacao, int qntEstoque, int qntFuncionando, int qntInoperante, String URLImagem) {
        this.nomeCadastradoTasy = nomeCadastradoTasy;
        this.tipoEquipamento = tipoEquipamento;
        this.marca = marca;
        this.localizacao = localizacao;
        this.qntEstoque = qntEstoque;
        this.qntFuncionando = qntFuncionando;
        this.qntInoperante = qntInoperante;
        this.URLImagem = URLImagem;
    }

    public String getNomeCadastradoTasy() {
        return nomeCadastradoTasy;
    }

    public void setNomeCadastradoTasy(String nomeCadastradoTasy) {
        this.nomeCadastradoTasy = nomeCadastradoTasy;
    }

    public String getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(String tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public int getQntEstoque() {
        return qntEstoque;
    }

    public void setQntEstoque(int qntEstoque) {
        this.qntEstoque = qntEstoque;
    }

    public int getQntFuncionando() {
        return qntFuncionando;
    }

    public void setQntFuncionando(int qntFuncionando) {
        this.qntFuncionando = qntFuncionando;
    }

    public int getQntInoperante() {
        return qntInoperante;
    }

    public void setQntInoperante(int qntInoperante) {
        this.qntInoperante = qntInoperante;
    }

    public long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getURLImagem() {
        return URLImagem;
    }

    public void setURLImagem(String URLImagem) {
        this.URLImagem = URLImagem;
    }

}

