package com.controller;

import com.domain.Rol;
import com.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.getAllRoles());
        return "rol/lista"; // templates/rol/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevoRol(Model model) {
        model.addAttribute("rol", new Rol());
        return "rol/form"; // templates/rol/form.html
    }

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable Long id, Model model) {
        Rol rol = rolService.getRolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + id));
        model.addAttribute("rol", rol);
        return "rol/form";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute("rol") Rol rol) {
        rolService.saveRol(rol);
        return "redirect:/rol";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return "redirect:/rol";
    }
}
