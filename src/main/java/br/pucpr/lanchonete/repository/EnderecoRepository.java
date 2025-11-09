package br.pucpr.lanchonete.repository;

import br.pucpr.lanchonete.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}

