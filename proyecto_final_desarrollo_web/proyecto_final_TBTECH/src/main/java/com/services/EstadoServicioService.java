package com.services;

import com.domain.EstadoServicio;
import com.repository.EstadoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoServicioService {

    @Autowired
    private EstadoServicioRepository estadoServicioRepository;

    public List<EstadoServicio> getAllEstadosServicio() {
        return estadoServicioRepository.findAll();
    }

    public Optional<EstadoServicio> getEstadoServicioById(Long id) {
        return estadoServicioRepository.findById(id);
    }

    public void saveEstadoServicio(EstadoServicio estadoServicio) {
        estadoServicioRepository.save(estadoServicio);
    }

    public void deleteEstadoServicio(Long id) {
        estadoServicioRepository.deleteById(id);
    }
}
