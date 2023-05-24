package com.example.lab6_sol.controller;

import com.example.lab6_sol.entity.Usuario;
import com.example.lab6_sol.repository.CursoRepository;
import com.example.lab6_sol.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;
    CursoRepository cursoRepository;

    @GetMapping("/lista")
    public String listaUsuarios(Model model) {
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @RequestMapping(value = {"/crear"}, method = RequestMethod.GET)
    public String crearEstudiante(Model model,@ModelAttribute("estudiante") Usuario estudiante) {
        List<Usuario> estudiantes = usuarioRepository.findAll();
        model.addAttribute("estudiantes", estudiantes);
        return "formulario";
    }

    @PostMapping("/save")
    public String guardarEstudiante(RedirectAttributes attr,
                                  Model model,
                                  @ModelAttribute("estudiante") @Valid Usuario estudiante,
                                  BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            model.addAttribute("listaCursos", cursoRepository.findAll());
            return "formulario";
        } else {
            estudiante.setActivo(true);
            if (estudiante.getId() == 0) {
                attr.addFlashAttribute("msg", "Estudiante creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Estudiante actualizado exitosamente");
            }
            usuarioRepository.save(estudiante);
            return "redirect:/estudiante/lista";
        }
    }

}
