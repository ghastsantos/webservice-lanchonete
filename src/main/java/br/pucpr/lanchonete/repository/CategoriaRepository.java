package br.pucpr.lanchonete.repository;

import br.pucpr.lanchonete.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

