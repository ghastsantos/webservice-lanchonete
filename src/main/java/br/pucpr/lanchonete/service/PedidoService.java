package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.PedidoRequestDTO;
import br.pucpr.lanchonete.dto.PedidoResponseDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.Cliente;
import br.pucpr.lanchonete.model.ItemPedido;
import br.pucpr.lanchonete.model.Pedido;
import br.pucpr.lanchonete.model.Produto;
import br.pucpr.lanchonete.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Cliente cliente = clienteService.obterEntidade(dto.getClienteId());
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(dto.getStatus() != null ? dto.getStatus() : "NOVO");

        List<ItemPedido> itens = new ArrayList<>();
        dto.getItens().forEach(itemDTO -> {
            Produto produto = produtoService.obterEntidade(itemDTO.getProdutoId());
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            itens.add(item);
        });
        pedido.setItens(itens);

        Pedido salvo = pedidoRepository.save(pedido);
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("PEDIDO_NOT_FOUND", "Pedido não encontrado"));
        return toResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("PEDIDO_NOT_FOUND", "Pedido não encontrado"));

        Cliente cliente = clienteService.obterEntidade(dto.getClienteId());
        pedido.setCliente(cliente);
        pedido.setStatus(dto.getStatus() != null ? dto.getStatus() : pedido.getStatus());

        pedido.getItens().clear();
        dto.getItens().forEach(itemDTO -> {
            Produto produto = produtoService.obterEntidade(itemDTO.getProdutoId());
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            pedido.getItens().add(item);
        });

        Pedido salvo = pedidoRepository.save(pedido);
        return toResponseDTO(salvo);
    }

    public void excluir(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new BusinessException("PEDIDO_NOT_FOUND", "Pedido não encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    public Pedido obterEntidade(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("PEDIDO_NOT_FOUND", "Pedido não encontrado"));
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setClienteNome(pedido.getCliente().getNome());
        dto.setStatus(pedido.getStatus());
        dto.setDataCriacao(pedido.getDataCriacao());

        pedido.getItens().forEach(item -> {
            PedidoResponseDTO.Item itemDTO = new PedidoResponseDTO.Item();
            itemDTO.setId(item.getId());
            itemDTO.setProdutoId(item.getProduto().getId());
            itemDTO.setProdutoNome(item.getProduto().getNome());
            itemDTO.setQuantidade(item.getQuantidade());
            itemDTO.setPrecoUnitario(item.getPrecoUnitario());
            dto.getItens().add(itemDTO);
        });

        return dto;
    }
}

