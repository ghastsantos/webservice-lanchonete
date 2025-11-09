package br.pucpr.lanchonete.repository;

import br.pucpr.lanchonete.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}

