package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.ItemPedidoDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.ItemPedido;
import br.pucpr.lanchonete.model.Pedido;
import br.pucpr.lanchonete.model.Produto;
import br.pucpr.lanchonete.repository.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public ItemPedidoDTO salvar(ItemPedidoDTO dto) {
        Pedido pedido = pedidoService.obterEntidade(dto.getPedidoId());
        Produto produto = produtoService.obterEntidade(dto.getProdutoId());

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(resolvePreco(dto.getPrecoUnitario(), produto));

        ItemPedido salvo = itemPedidoRepository.save(item);
        return toDTO(salvo);
    }

    public List<ItemPedidoDTO> listar() {
        return itemPedidoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ItemPedidoDTO buscarPorId(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ITEM_PEDIDO_NOT_FOUND", "Item do pedido não encontrado"));
        return toDTO(item);
    }

    public ItemPedidoDTO atualizar(Long id, ItemPedidoDTO dto) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ITEM_PEDIDO_NOT_FOUND", "Item do pedido não encontrado"));
        Pedido pedido = pedidoService.obterEntidade(dto.getPedidoId());
        Produto produto = produtoService.obterEntidade(dto.getProdutoId());

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setPrecoUnitario(resolvePreco(dto.getPrecoUnitario(), produto));

        ItemPedido salvo = itemPedidoRepository.save(item);
        return toDTO(salvo);
    }

    public void excluir(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new BusinessException("ITEM_PEDIDO_NOT_FOUND", "Item do pedido não encontrado");
        }
        itemPedidoRepository.deleteById(id);
    }

    private ItemPedidoDTO toDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setId(item.getId());
        dto.setPedidoId(item.getPedido().getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        return dto;
    }

    private BigDecimal resolvePreco(BigDecimal preco, Produto produto) {
        if (preco == null) {
            return produto.getPreco();
        }
        return preco;
    }
}

