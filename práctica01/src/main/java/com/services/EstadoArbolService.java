package com.services;

import com.domain.EstadoArbol;
import com.repository.EstadoArbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EstadoArbolService {

    @Autowired
    private EstadoArbolRepository estadoArbolRepository;

    public List<EstadoArbol> getAllEstados() {
        return estadoArbolRepository.findAll();
    }

    public Optional<EstadoArbol> getEstadoById(Long id) {
        return estadoArbolRepository.findById(id);
    }

    public EstadoArbol saveEstado(EstadoArbol estadoArbol) {
        return estadoArbolRepository.save(estadoArbol);
    }

    public void deleteEstado(Long id) {
        estadoArbolRepository.deleteById(id);
    }
}
