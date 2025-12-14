package com.repository;

import com.domain.EstadoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoServicioRepository extends JpaRepository<EstadoServicio, Long> {
}
