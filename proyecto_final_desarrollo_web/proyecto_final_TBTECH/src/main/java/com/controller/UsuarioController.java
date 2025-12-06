package com.controller;

import com.domain.Usuario;
import com.services.RolService;
import com.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                .orElseThrow(() -> new IllegalArgumentException("Cédula no encontrada: " + cedula));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuario/formNuevo";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(
            @ModelAttribute("usuario") Usuario usuario,
            @RequestParam("rolId") Long rolId,
            @RequestParam(value = "nuevaContrasenia", required = false) String nuevaContrasenia,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {

            // Validar cédula
            if (usuario.getCedulaUsuario() == null || usuario.getCedulaUsuario() <= 0) {
                model.addAttribute("error", "La cédula debe ser un número válido y positivo");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }

            // Validar nombre
            if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
                model.addAttribute("error", "El nombre es obligatorio");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }
            if (usuario.getNombreUsuario().trim().length() < 3) {
                model.addAttribute("error", "El nombre debe tener al menos 3 caracteres");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }
            if (!usuario.getNombreUsuario().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
                model.addAttribute("error", "El nombre solo puede contener letras y espacios");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }

            //  Validar correo
            if (usuario.getCorreoUsuario() == null || usuario.getCorreoUsuario().trim().isEmpty()) {
                model.addAttribute("error", "El correo es obligatorio");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }

            //  Validar teléfono
            if (usuario.getTelefonoUsuario() == null || usuario.getTelefonoUsuario().trim().isEmpty()) {
                model.addAttribute("error", "El teléfono es obligatorio");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }
            if (!usuario.getTelefonoUsuario().matches("^[0-9]{8,10}$")) {
                model.addAttribute("error", "El teléfono debe tener entre 8 y 10 dígitos");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }

            //  Validar rol
            if (rolId == null || rolId <= 0) {
                model.addAttribute("error", "Debe seleccionar un rol válido");
                model.addAttribute("roles", rolService.getAllRoles());
                return "usuario/formNuevo";
            }

            // Verificar si el usuario ya existe
            Usuario existente = usuarioService.getUsuarioByCedula(usuario.getCedulaUsuario()).orElse(null);
            boolean esEdicion = existente != null;

            if (esEdicion) {

                // Validar correo único
                Usuario usuarioPorCorreo = usuarioService.getUsuarioByCorreo(usuario.getCorreoUsuario()).orElse(null);
                if (usuarioPorCorreo != null
                        && !usuarioPorCorreo.getCedulaUsuario().equals(usuario.getCedulaUsuario())) {
                    model.addAttribute("error", "El correo electrónico ya está registrado con otro usuario");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }

                // Validar teléfono único
                Usuario usuarioPorTelefono = usuarioService.getUsuarioByTelefono(usuario.getTelefonoUsuario()).orElse(null);
                if (usuarioPorTelefono != null
                        && !usuarioPorTelefono.getCedulaUsuario().equals(usuario.getCedulaUsuario())) {
                    model.addAttribute("error", "El teléfono ya está registrado con otro usuario");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }

                // Manejo de contraseña en edición
                if (nuevaContrasenia != null && !nuevaContrasenia.isBlank()) {
                    if (nuevaContrasenia.length() < 6) {
                        model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                        model.addAttribute("roles", rolService.getAllRoles());
                        return "usuario/formNuevo";
                    }
                    if (!nuevaContrasenia.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")) {
                        model.addAttribute("error", "La contraseña debe contener mayúsculas, minúsculas y números");
                        model.addAttribute("roles", rolService.getAllRoles());
                        return "usuario/formNuevo";
                    }
                    usuario.setContraseniaUsuario(nuevaContrasenia);
                } else {
                    usuario.setContraseniaUsuario(existente.getContraseniaUsuario());
                }
            } else {

                if (usuarioService.getUsuarioByCedula(usuario.getCedulaUsuario()).isPresent()) {
                    model.addAttribute("error", "Ya existe un usuario con esta cédula");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }

                if (usuarioService.getUsuarioByCorreo(usuario.getCorreoUsuario()).isPresent()) {
                    model.addAttribute("error", "El correo electrónico ya está registrado");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }

                if (usuarioService.getUsuarioByTelefono(usuario.getTelefonoUsuario()).isPresent()) {
                    model.addAttribute("error", "El teléfono ya está registrado");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }

                if (usuario.getContraseniaUsuario() == null || usuario.getContraseniaUsuario().trim().isEmpty()) {
                    model.addAttribute("error", "La contraseña es obligatoria");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }
                if (usuario.getContraseniaUsuario().length() < 6) {
                    model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }
                if (!usuario.getContraseniaUsuario().matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")) {
                    model.addAttribute("error", "La contraseña debe contener mayúsculas, minúsculas y números");
                    model.addAttribute("roles", rolService.getAllRoles());
                    return "usuario/formNuevo";
                }
            }

            usuarioService.saveUsuario(usuario, rolId);

            String mensaje = esEdicion
                    ? "Usuario actualizado correctamente"
                    : "Usuario registrado correctamente";

            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            return "redirect:/usuario";

        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            model.addAttribute("roles", rolService.getAllRoles());
            return "usuario/formNuevo";
        }
    }

    //arreglo de las rutas
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
            Principal principal,
            RedirectAttributes redirectAttributes) {
        Usuario existente = usuarioService.getUsuarioByCorreo(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (nuevaContrasenia != null && !nuevaContrasenia.isBlank()) {
            existente.setContraseniaUsuario(nuevaContrasenia);
        }

        usuarioService.saveUsuario(existente, existente.getRol().getRolId());

        redirectAttributes.addFlashAttribute("mensaje", "Cambios guardados correctamente");
        return "redirect:/usuario/configuracion";
    }
}
