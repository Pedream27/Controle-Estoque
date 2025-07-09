package br.com.pholiveira.Controle.de.Estoque.hateos;

import br.com.pholiveira.Controle.de.Estoque.controller.EquipamentosController;
import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import br.com.pholiveira.Controle.de.Estoque.model.RequestAddEquipamento;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class EquipamentosHateos {

    public static void  addHateos(Equipamentos equipamento){

        //Hateos para atualizar o equipamento no sistema
        equipamento.add(linkTo(methodOn(EquipamentosController.class)
                .atualizarEquipamento(equipamento.getId() , equipamento)).withRel("atualizarEquipamento"));
        // Hateos para deletar o equipamento no sistema
        equipamento.add(linkTo(methodOn(EquipamentosController.class)
                .deletarEquipamento(equipamento.getId())).withRel("deletarEquipamento"));
        // Hateos para buscar equipamento por id
        equipamento.add(linkTo(methodOn(EquipamentosController.class)
                .buscarEquipamentoPorId(equipamento.getId())).withRel("buscarEquipamento"));



    }


    public static Equipamentos  addHateosReturn(Equipamentos equipamento){

        //Hateos para atualizar o equipamento no sistema
        equipamento.add(linkTo(methodOn(EquipamentosController.class)
                .atualizarEquipamento(equipamento.getId() , equipamento)).withRel("atualizarEquipamento"));
        // Hateos para deletar o equipamento no sistema
        equipamento.add(linkTo(methodOn(EquipamentosController.class)
                .deletarEquipamento(equipamento.getId())).withRel("deletarEquipamento"));
        // Hateos para buscar equipamento por id
        equipamento.add(linkTo(methodOn(EquipamentosController.class)
                .buscarEquipamentoPorId(equipamento.getId())).withRel("buscarEquipamento"));


        return equipamento;

    }
}
