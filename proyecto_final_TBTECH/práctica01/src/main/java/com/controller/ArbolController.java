package com.controller;

import com.domain.Arbol;
import com.domain.EstadoArbol;
import com.services.ArbolService;
import com.services.EstadoArbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/arbol")
public class ArbolController {

    @Autowired
    private ArbolService arbolService;

    @Autowired
    private EstadoArbolService estadoArbolService; // para llenar el coso de select de estados, pero nada mas


    @GetMapping
    public String mostrarArboles(Model model) {
        model.addAttribute("arboles", arbolService.getAllArboles());
        model.addAttribute("estados", estadoArbolService.getAllEstados()); // lista de estados, en plurarl. asi thymeleaf la busca luego
        return "arbol/lista";  //luego se renderiza
    }



    @GetMapping("/nuevo") // usar /NUEVO en barra de busqueda xq si se usa arbol/form.html da el 404
    public String nuevoArbol(Model model) {
        model.addAttribute("arbol", new Arbol()); //objeto vacio xq es nuevo
        model.addAttribute("estados", estadoArbolService.getAllEstados());  //SOLo para llenar el select
        return "arbol/form"; // templates/arbol/form.html
    }

   
    @GetMapping("/editar/{id}")
    public String editarArbol(@PathVariable Long id, Model model) {
        Arbol arbol = arbolService.getArbolById(id)
                .orElseThrow(() -> new IllegalArgumentException("Árbol no encontrado: " + id));
        model.addAttribute("arbol", arbol);
        model.addAttribute("estados", estadoArbolService.getAllEstados()); 
        return "arbol/form";
    }


    @PostMapping("/guardar")
    public String guardarArbol(@ModelAttribute("arbol") Arbol arbol) {
        arbolService.saveArbol(arbol);
        return "redirect:/arbol"; // redirige a la lista
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarArbol(@PathVariable Long id) {
        arbolService.deleteArbol(id);
        return "redirect:/arbol";
    }
}
