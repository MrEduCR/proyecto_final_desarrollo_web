
package com.services;

import com.domain.Pieza;
import com.domain.Usuario;
import com.repository.RolRepository;
import com.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> getUsuarioByCedula(int cedula) {
        return usuarioRepository.findById(cedula);
    }
    
}

