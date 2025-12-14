package com.repository;

import com.domain.TipoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEquipoRepository extends JpaRepository<TipoEquipo, Long> {
}
