
package com.controller;

import com.domain.Usuario;
import com.services.RolService;
import com.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolService rolService;
    
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuario/lista";
    }
    
    @GetMapping("/editar/{cedulaUsuario}")
    public String editarUsuario(@PathVariable("cedulaUsuario") int cedula, Model model) {
        Usuario usuario = usuarioService.getUsuarioByCedula(cedula)
                .orElseThrow(() -> new IllegalArgumentException("Cedula no encontrada: " + cedula));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, @RequestParam("rolId") Long rolId) {
        usuarioService.saveUsuario(usuario, rolId);
        return "redirect:/usuario";
    }
    
}

