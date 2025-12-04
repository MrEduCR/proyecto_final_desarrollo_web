package com.controller;

import com.domain.Usuario;
import com.services.RolService;
import com.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }

    @GetMapping("/editar/{cedulaUsuario}")
    public String editarUsuario(@PathVariable("cedulaUsuario") int cedula, Model model) {
        Usuario usuario = usuarioService.getUsuarioByCedula(cedula)
                .orElseThrow(() -> new IllegalArgumentException("Cedula no encontrada: " + cedula));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(
            @ModelAttribute("usuario") Usuario usuario,
            @RequestParam("rolId") Long rolId,
            @RequestParam(value = "nuevaContrasenia", required = false) String nuevaContrasenia) {

        Usuario existente = usuarioService.getUsuarioByCedula(usuario.getCedulaUsuario()).orElse(null);

        if (existente != null) {
            if (nuevaContrasenia != null && !nuevaContrasenia.isBlank()) {
                usuario.setContraseniaUsuario(nuevaContrasenia);
            } else {
                usuario.setContraseniaUsuario(existente.getContraseniaUsuario());
            }
        }

        usuarioService.saveUsuario(usuario, rolId);
        return "redirect:/usuario";
    }

    @GetMapping("/configuracion")
    public String mostrarConfiguracion(Model model, Principal principal) {
        Usuario usuario = usuarioService.getUsuarioByCorreo(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuario/configuracion";
    }

    @PostMapping("/configuracion/guardar")
    public String guardarConfiguracion(
            @RequestParam(value = "nuevaContrasenia", required = false) String nuevaContrasenia,
            Principal principal) {

        Usuario existente = usuarioService.getUsuarioByCorreo(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (nuevaContrasenia != null && !nuevaContrasenia.isBlank()) {
            existente.setContraseniaUsuario(nuevaContrasenia);
        }

        usuarioService.saveUsuario(existente, existente.getRol().getRolId());
        return "redirect:/usuario/configuracion";
    }
}
