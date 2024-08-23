package com.EatAway.controller;

import com.EatAway.domain.Resena;
import com.EatAway.domain.Usuario;
import com.EatAway.service.ResenaService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ResenaController {
    
    @Autowired
    private ResenaService resenaService;
    
    @PostMapping("/enviar")
    public String resenaGuardar(Resena resena) {
        resenaService.saveResena(resena);
        
        return "redirect:/";
    }
    
    @GetMapping("/resenas")
    public String resenas(HttpSession session,Usuario usuario, Model model) {
        
        // Obtener las reservas del usuario autenticado
        List<Resena> resena = resenaService.obtenerResenasPorUsuario(session);
        
        model.addAttribute("resenas", resena);
        model.addAttribute("totalResenas", resena.size());
        
        return "/resenas/historialResenas";
    }
    
    @GetMapping("/resenas/eliminar/{idResena}")
    public String reservaEliminar(Resena resena) {
        resenaService.eliminarResena(resena);
        return "redirect:/resenas";
    }
    
    @PostMapping("/editarReseña")
    public String editarProducto(Resena resena){
        resenaService.editarResena(resena);
        return "redirect:/resenas";
    }
}