package br.pucpr.lanchonete.repository;

import br.pucpr.lanchonete.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

