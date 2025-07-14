package br.com.pholiveira.Controle.de.Estoque.repository;

import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {

    List<Equipamentos> findByNomeCadastradoTasyContainingIgnoreCase(String nome);



}
