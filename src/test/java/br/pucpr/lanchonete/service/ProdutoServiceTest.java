package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.ProdutoDTO;
import br.pucpr.lanchonete.model.Categoria;
import br.pucpr.lanchonete.model.Produto;
import br.pucpr.lanchonete.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    private ProdutoService produtoService;

    @BeforeEach
    void setup() {
        produtoService = new ProdutoService(produtoRepository, categoriaService, new ModelMapper());
    }

    @Test
    void deveSalvarProduto() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("X-Burger");
        dto.setDescricao("Hambúrguer com queijo");
        dto.setPreco(BigDecimal.valueOf(20));
        dto.setCategoriaId(10L);

        Categoria categoria = new Categoria();
        categoria.setId(10L);
        categoria.setNome("Lanches");

        when(categoriaService.obterEntidade(10L)).thenReturn(categoria);
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> {
            Produto produto = invocation.getArgument(0);
            produto.setId(1L);
            return produto;
        });

        ProdutoDTO resultado = produtoService.salvar(dto);

        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getCategoriaId()).isEqualTo(10L);

        ArgumentCaptor<Produto> captor = ArgumentCaptor.forClass(Produto.class);
        verify(produtoRepository).save(captor.capture());
        assertThat(captor.getValue().getCategoria().getNome()).isEqualTo("Lanches");
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = new Produto();
        produto.setId(3L);
        produto.setNome("Refrigerante");
        produto.setPreco(BigDecimal.TEN);
        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setNome("Bebidas");
        produto.setCategoria(categoria);

        when(produtoRepository.findById(3L)).thenReturn(Optional.of(produto));

        ProdutoDTO resultado = produtoService.buscarPorId(3L);

        assertThat(resultado.getNome()).isEqualTo("Refrigerante");
        assertThat(resultado.getCategoriaId()).isEqualTo(2L);
    }
}

