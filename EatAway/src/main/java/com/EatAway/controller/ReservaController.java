package com.EatAway.controller;

import com.EatAway.domain.Reserva;
import com.EatAway.domain.Local;
import com.EatAway.domain.Usuario;
import com.EatAway.service.LocalService;
import com.EatAway.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
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
    public String reservaGuardar(Reserva reserva, Model model, HttpSession session, Usuario usuario) {
                     
        reservaService.agregarReserva(reserva);
        List<Reserva> reservas = reservaService.obtenerReservasPorUsuario(session);
        
        model.addAttribute("reservas", reservas);
        
        return "/reserva/historialReservas";
    }
    
    @GetMapping("/reservas")
    public String reservaHistorial(HttpSession session,Usuario usuario, Model model) {
        
        // Obtener las reservas del usuario autenticado
        List<Reserva> reservas = reservaService.obtenerReservasPorUsuario(session);
        
        model.addAttribute("reservas", reservas);
        
        return "/reserva/historialReservas";
    }
    
    @GetMapping("/reservas/eliminar/{idReserva}")
    public String reservaEliminar(Reserva reserva) {
        reservaService.eliminarReserva(reserva);
        return "redirect:/reservas";
    }
    
}