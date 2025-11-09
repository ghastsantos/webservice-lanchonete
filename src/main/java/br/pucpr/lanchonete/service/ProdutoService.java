package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.ProdutoDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.Categoria;
import br.pucpr.lanchonete.model.Produto;
import br.pucpr.lanchonete.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final ModelMapper modelMapper;

    public ProdutoDTO salvar(ProdutoDTO dto) {
        Categoria categoria = categoriaService.obterEntidade(dto.getCategoriaId());
        Produto produto = modelMapper.map(dto, Produto.class);
        produto.setCategoria(categoria);
        Produto salvo = produtoRepository.save(produto);
        return toDTO(salvo);
    }

    public List<ProdutoDTO> listar() {
        return produtoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("PRODUTO_NOT_FOUND", "Produto não encontrado"));
        return toDTO(produto);
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("PRODUTO_NOT_FOUND", "Produto não encontrado"));
        Categoria categoria = categoriaService.obterEntidade(dto.getCategoriaId());

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);

        Produto salvo = produtoRepository.save(produto);
        return toDTO(salvo);
    }

    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new BusinessException("PRODUTO_NOT_FOUND", "Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    public Produto obterEntidade(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("PRODUTO_NOT_FOUND", "Produto não encontrado"));
    }

    private ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);
        dto.setCategoriaId(produto.getCategoria().getId());
        return dto;
    }
}

