package com.services;

import com.domain.Pieza;
import com.repository.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PiezaService {

    @Autowired
    private PiezaRepository piezaRepository;

    public List<Pieza> getAllPiezas() {
        return piezaRepository.findAll();
    }

    public Optional<Pieza> getPiezaById(Long id) {
        return piezaRepository.findById(id);
    }

    public void savePieza(Pieza pieza) {
        piezaRepository.save(pieza);
    }

    public void deletePieza(Long id) {
        piezaRepository.deleteById(id);
    }
}
