package br.com.pholiveira.Controle.de.Estoque.repository;

import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {

    List<Equipamentos> findByNomeCadastradoTasyContainingIgnoreCase(String nome);



}
