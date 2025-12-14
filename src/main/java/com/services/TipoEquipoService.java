package com.services;

import com.domain.TipoEquipo;
import com.repository.TipoEquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEquipoService {

    @Autowired
    private TipoEquipoRepository tipoEquipoRepository;

    public List<TipoEquipo> getAllTiposEquipo() {
        return tipoEquipoRepository.findAll();
    }

    public Optional<TipoEquipo> getTipoEquipoById(Long id) {
        return tipoEquipoRepository.findById(id);
    }

    public void saveTipoEquipo(TipoEquipo tipoEquipo) {
        tipoEquipoRepository.save(tipoEquipo);
    }

    public void deleteTipoEquipo(Long id) {
        tipoEquipoRepository.deleteById(id);
    }
}
