package com.services;

import com.domain.Servicio;
import com.domain.Pieza;
import com.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioPiezaService {

    @Autowired
    private ServicioRepository servicioRepository;

    public Optional<Servicio> agregarPieza(Long servicioId, Pieza pieza) {
        Optional<Servicio> optServicio = servicioRepository.findById(servicioId);
        if (optServicio.isPresent()) {
            Servicio servicio = optServicio.get();
            if (!servicio.getPiezas().contains(pieza)) {
                servicio.getPiezas().add(pieza);
                servicioRepository.save(servicio);
            }
            return Optional.of(servicio);
        }
        return Optional.empty();
    }

    public Optional<Servicio> eliminarPieza(Long servicioId, Pieza pieza) {
        Optional<Servicio> optServicio = servicioRepository.findById(servicioId);
        if (optServicio.isPresent()) {
            Servicio servicio = optServicio.get();
            if (servicio.getPiezas().contains(pieza)) {
                servicio.getPiezas().remove(pieza);
                servicioRepository.save(servicio);
            }
            return Optional.of(servicio);
        }
        return Optional.empty();
    }
}
