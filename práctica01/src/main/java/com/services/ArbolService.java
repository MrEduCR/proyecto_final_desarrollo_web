package com.services;

import com.domain.Arbol;
import com.repository.ArbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArbolService {

    @Autowired
    private ArbolRepository arbolRepository;

    public List<Arbol> getAllArboles() {
        return arbolRepository.findAll(); //find all es basicamente un select * de jpa
    }

    public Optional<Arbol> getArbolById(Long id) {
        return arbolRepository.findById(id);
    }

    public Arbol saveArbol(Arbol arbol) {
        return arbolRepository.save(arbol);
    }

    public void deleteArbol(Long id) {
        arbolRepository.deleteById(id);
    }

    public Arbol updateArbol(Long id, Arbol arbolDetails) {
        return arbolRepository.findById(id).map(arbol -> {  //asi se pasa de una vez
            arbol.setNombreComun(arbolDetails.getNombreComun());
            arbol.setTipoFlor(arbolDetails.getTipoFlor());
            arbol.setDurezaMadera(arbolDetails.getDurezaMadera());
            arbol.setAlturaPromedio(arbolDetails.getAlturaPromedio());
            arbol.setRutaImagen(arbolDetails.getRutaImagen());
            return arbolRepository.save(arbol);
        }).orElseGet(() -> {
            arbolDetails.setId(id);
            return arbolRepository.save(arbolDetails);
        });
    }
}
