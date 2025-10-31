package com.services;

import com.domain.TipoServicio;
import com.repository.TipoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TipoServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    public List<TipoServicio> getAllTipoServicio() {
        return tipoServicioRepository.findAll();
    }

    public Optional<TipoServicio> getTipoServicioById(Long id) {
        return tipoServicioRepository.findById(id);
    }

    public TipoServicio saveTipoServicio(TipoServicio tipoServicio) {
        return tipoServicioRepository.save(tipoServicio);
    }

    public void deleteTipoServicio(Long id) {
        tipoServicioRepository.deleteById(id);
    }
}
