package com.services;

import com.domain.Cliente;
import com.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service  // Esto le dice a Spring que esta clase es un servicio
public class ClienteServicioImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente); // Guarda el cliente en la BD
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll(); // Devuelve todos los clientes
    }
}

