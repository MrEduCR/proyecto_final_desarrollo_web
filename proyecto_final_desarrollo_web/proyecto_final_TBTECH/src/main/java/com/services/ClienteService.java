package com.services;

import com.domain.Cliente;
import java.util.List;

public interface ClienteService {
    void guardar(Cliente cliente);
    List<Cliente> listar();
}
