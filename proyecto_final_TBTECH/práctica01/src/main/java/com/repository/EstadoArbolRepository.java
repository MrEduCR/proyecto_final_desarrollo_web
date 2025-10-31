package com.repository;

import com.domain.EstadoArbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoArbolRepository extends JpaRepository<EstadoArbol, Long> {
}

