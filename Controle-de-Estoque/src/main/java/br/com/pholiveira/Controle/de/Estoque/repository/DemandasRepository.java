package br.com.pholiveira.Controle.de.Estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pholiveira.Controle.de.Estoque.model.Demandas;
import br.com.pholiveira.Controle.de.Estoque.model.enuns.Prioridade;
import br.com.pholiveira.Controle.de.Estoque.model.enuns.Status;

public interface DemandasRepository  extends JpaRepository<Demandas, Long> {

    // Custom query methods can be added here if needed
    // For example, to find by status or priority
    List<Demandas> findByStatus(Status status);
    List<Demandas> findByPrioridade(Prioridade prioridade);     

}
