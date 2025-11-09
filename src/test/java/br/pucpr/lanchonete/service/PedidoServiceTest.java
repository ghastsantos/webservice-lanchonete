package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.PedidoRequestDTO;
import br.pucpr.lanchonete.dto.PedidoResponseDTO;
import br.pucpr.lanchonete.model.Cliente;
import br.pucpr.lanchonete.model.Pedido;
import br.pucpr.lanchonete.model.Produto;
import br.pucpr.lanchonete.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdutoService produtoService;

    private PedidoService pedidoService;

    @BeforeEach
    void setup() {
        pedidoService = new PedidoService(pedidoRepository, clienteService, produtoService);
    }

    @Test
    void deveCriarPedidoComItens() {
        PedidoRequestDTO request = new PedidoRequestDTO();
        request.setClienteId(1L);
        request.setStatus("NOVO");
        PedidoRequestDTO.Item item = new PedidoRequestDTO.Item();
        item.setProdutoId(2L);
        item.setQuantidade(3);
        request.getItens().add(item);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        Produto produto = new Produto();
        produto.setId(2L);
        produto.setNome("Combo");
        produto.setPreco(BigDecimal.valueOf(25));

        when(clienteService.obterEntidade(1L)).thenReturn(cliente);
        when(produtoService.obterEntidade(2L)).thenReturn(produto);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(10L);
            pedido.setDataCriacao(LocalDateTime.now());
            pedido.getItens().forEach(i -> i.setId(20L));
            return pedido;
        });

        PedidoResponseDTO resposta = pedidoService.criar(request);

        assertThat(resposta.getId()).isEqualTo(10L);
        assertThat(resposta.getItens()).hasSize(1);
        assertThat(resposta.getItens().get(0).getProdutoNome()).isEqualTo("Combo");
    }
}

