package br.com.pholiveira.Controle.de.Estoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pholiveira.Controle.de.Estoque.model.Demandas;
import br.com.pholiveira.Controle.de.Estoque.repository.DemandasRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DemandaService {

    @Autowired
    private DemandasRepository repository;

    public Demandas criarDemanda(Demandas demanda) {
        return repository.save(demanda);
    }

    public List<Demandas> listarTodas() {
        return repository.findAll();
    }

    public Optional<Demandas> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Demandas atualizarDemanda(Long id, Demandas novaDemanda) {
        return repository.findById(id)
                .map(d -> {
                    novaDemanda.setId(d.getId());
                    return repository.save(novaDemanda);
                })
                .orElseThrow(() -> new RuntimeException("Demanda não encontrada: " + id));
    }

    public void deletarDemanda(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Demanda não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}

