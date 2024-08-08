
package com.EatAway.controller;

import com.EatAway.domain.Evento;
import com.EatAway.service.EventoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventoController {
    
    @Autowired
    private EventoService eventoService; 
    
    @GetMapping("/eventos")
    public String inicio(Model model) {
        List<Evento> listado = eventoService.getEventos();

        model.addAttribute("eventos", listado);
        return "/evento/evento";
    }
    
    
}
