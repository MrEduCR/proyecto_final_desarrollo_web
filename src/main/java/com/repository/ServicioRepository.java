package com.repository;

import com.domain.Servicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Optional<Servicio> findByServicioId(Long servicioId); // el findall ya viene incluido siempre x aquello, este es para busqueda especifica

    // Para todos los NO finalizados
    List<Servicio> findByEstadoServicio_EstadoServicioDescripcionNot(String descripcion);


}
