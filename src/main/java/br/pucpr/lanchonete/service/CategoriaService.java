package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.CategoriaDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.Categoria;
import br.pucpr.lanchonete.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaDTO salvar(CategoriaDTO dto) {
        Categoria categoria = modelMapper.map(dto, Categoria.class);
        Categoria salva = categoriaRepository.save(categoria);
        return modelMapper.map(salva, CategoriaDTO.class);
    }

    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .toList();
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CATEGORIA_NOT_FOUND", "Categoria não encontrada"));
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CATEGORIA_NOT_FOUND", "Categoria não encontrada"));
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        Categoria salva = categoriaRepository.save(categoria);
        return modelMapper.map(salva, CategoriaDTO.class);
    }

    public void excluir(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new BusinessException("CATEGORIA_NOT_FOUND", "Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    public Categoria obterEntidade(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CATEGORIA_NOT_FOUND", "Categoria não encontrada"));
    }
}

