
package com.EatAway.service;

import com.EatAway.domain.Reserva;
import jakarta.servlet.http.HttpSession;
import java.util.List;


public interface ReservaService {
    
    public void agregarReserva(Reserva reserva);
    
    List<Reserva> obtenerReservasPorUsuario(HttpSession session);
    
    public void eliminarReserva(Reserva reserva);
}
