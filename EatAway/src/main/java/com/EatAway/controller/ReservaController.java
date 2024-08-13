package com.EatAway.controller;

import com.EatAway.domain.Reserva;
import com.EatAway.domain.Local;
import com.EatAway.service.LocalService;
import com.EatAway.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private LocalService restbarService;
    
    @GetMapping("/reservar/{idLocal}")
    public String localCargar(@PathVariable("idLocal") long idLocal, Model model) {                          
        
        Local localDetails = restbarService.getLocalID(idLocal);          
        
        // Añadir el local al modelo
        model.addAttribute("local", localDetails);
        
        return "/reserva/reserva";
    }
    
    @PostMapping("/reservar")
    public String reservaGuardar(Reserva reserva) {
                     
        reservaService.saveReserva(reserva);
        
        return "redirect:/";
    }
    
}