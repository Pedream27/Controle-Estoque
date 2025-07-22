package br.com.pholiveira.Controle.de.Estoque.services;

import br.com.pholiveira.Controle.de.Estoque.model.Equipamentos;
import br.com.pholiveira.Controle.de.Estoque.repository.EquipamentosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EquipamentosServices {

    private final EquipamentosRepository _repository;


    public EquipamentosServices( EquipamentosRepository repository) {
        _repository = repository;

    }

    // Criar/Salvar um novo equipamento
    public Equipamentos criarEquipamento(Equipamentos equipamento) {
        return _repository.save(equipamento);
    }

    // Listar todos os equipamentos
    public Page<Equipamentos> listarTodosEquipamentos(Pageable paggeable) {
        return _repository.findAll(paggeable);
    }

    // Buscar equipamento por ID
    public Optional<Equipamentos> buscarEquipamentoPorId(Long id) {
        return _repository.findById(id);
    }

    // Atualizar um equipamento existente
    public Equipamentos atualizarEquipamento(Long id, Equipamentos equipamentoAtualizado) {
        return _repository.findById(id)
                .map(equipamento -> {
                    validaAtualizacaoDoEquipamento(equipamento, equipamentoAtualizado);
                    return _repository.save(equipamento);

                })
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado com o ID: " + id));
    }

    private void validaAtualizacaoDoEquipamento(Equipamentos equipamento, Equipamentos equipamentoAtualizado) {

        equipamento.setNomeCadastradoTasy(equipamentoAtualizado.getNomeCadastradoTasy());
        equipamento.setTipoEquipamento(equipamentoAtualizado.getTipoEquipamento());
        equipamento.setMarca(equipamentoAtualizado.getMarca());
        equipamento.setModelo(equipamentoAtualizado.getModelo());
        equipamento.setLocalizacao(equipamentoAtualizado.getLocalizacao());
        equipamento.setQntEstoque(equipamentoAtualizado.getQntEstoque());
        equipamento.setQntFuncionando(equipamentoAtualizado.getQntFuncionando());
        equipamento.setQntInoperante(equipamentoAtualizado.getQntInoperante());
        equipamento.setURLImagem(equipamentoAtualizado.getURLImagem());
    }

    // Deletar equipamento por ID
    public void deletarEquipamento(Long id) {
        _repository.deleteById(id);
    }

    public List<Equipamentos> buscarPorNome(String nome) {
        return _repository.findByNomeCadastradoTasyContainingIgnoreCase(nome);
    }

    public Equipamentos atualizarUrlImagem(Long id, String url) {
    Equipamentos eq = _repository.findById(id).orElseThrow(NoSuchElementException::new);
    eq.setURLImagem(url);
    return _repository.save(eq);
}







}
