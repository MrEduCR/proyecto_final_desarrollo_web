package com.services;

import com.domain.Rol;
import com.domain.Usuario;
import com.repository.RolRepository;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioByCedula(int cedula) {
        return usuarioRepository.findById(cedula);
    }

    public Optional<Usuario> getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreoUsuario(correo);
    }

    public Usuario saveUsuario(Usuario usuario, Long rolId) {

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("El rol con ID " + rolId + " no existe."));
        usuario.setRol(rol);

        String raw = usuario.getContraseniaUsuario();

        if (raw != null && !raw.startsWith("$2a$")) {
            usuario.setContraseniaUsuario(passwordEncoder.encode(raw));
        }

        return usuarioRepository.save(usuario);
    }
}
