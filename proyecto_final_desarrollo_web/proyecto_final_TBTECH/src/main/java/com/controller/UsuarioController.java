
package com.controller;

import com.domain.Pieza;
import com.domain.Usuario;
import com.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuario/lista"; // templates/usuario/lista.html
    }
    
    @GetMapping("/usuario/{id}")
    public String editarUsuario(@PathVariable int cedula, Model model) {
        Usuario usuario = usuarioService.getUsuarioByCedula(cedula)
                .orElseThrow(() -> new IllegalArgumentException("Cedula no encontrada: " + cedula));
        model.addAttribute("usuario", usuario);
        return "usuario/form";
    }
    
}
