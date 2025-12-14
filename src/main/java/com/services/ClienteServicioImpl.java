package com.services;

import com.domain.Cliente;
import com.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorCedula(int cedula) {
        return clienteRepository.findByCedula(cedula);
    }
}