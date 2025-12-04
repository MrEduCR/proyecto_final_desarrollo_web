package com.repository;

import com.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; 

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCedula(int cedula); // Buscar cliente por cédula
}