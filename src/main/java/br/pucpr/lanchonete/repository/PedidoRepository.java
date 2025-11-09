package br.pucpr.lanchonete.repository;

import br.pucpr.lanchonete.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

