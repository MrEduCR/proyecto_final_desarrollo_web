package com.services;

import com.domain.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    void guardar(Cliente cliente);
    List<Cliente> listar();
    Optional<Cliente> buscarPorCedula(int cedula); 
}