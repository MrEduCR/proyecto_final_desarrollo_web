package com.repository;

import com.domain.Factura;
import com.domain.Servicio; // Importamos la entidad Servicio
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Usamos Optional para manejar el caso de no encontrar la factura

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    /**
     * Patrón de Consulta por Propiedad:
     * Spring Data JPA automáticamente genera la consulta SQL:
     * SELECT * FROM facturas WHERE servicio_id = ?
     * * @param servicio La entidad Servicio a buscar.
     * @return Un Optional<Factura> que puede contener la factura ligada a ese servicio.
     */
    Optional<Factura> findByServicio(Servicio servicio);
}// Patrón Repositorio/DAO: Hereda todas las operaciones CRUD automáticamente.


