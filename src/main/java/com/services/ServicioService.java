package com.services;

import com.domain.Servicio;
import com.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public List<Servicio> getServiciosNoFinalizados() {
        return servicioRepository.findByEstadoServicio_EstadoServicioDescripcionNot("Finalizado");
    }

    public Optional<Servicio> getServicioById(Long id) {
        return servicioRepository.findById(id);
    }

    public void saveServicio(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    public void deleteServicio(Long id) {
        servicioRepository.deleteById(id);
    }

    public Optional<Servicio> buscarPorId(Long id) {
        return servicioRepository.findByServicioId(id);
    }
}
