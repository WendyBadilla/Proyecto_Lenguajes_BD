
package com.EatAway.service;

import com.EatAway.domain.Resena;
import java.util.List;
import jakarta.servlet.http.HttpSession;

public interface ResenaService {
    
    List<Resena> getResenasPorLocal(long local);
    
    public void saveResena(Resena resena);
    
    List<Resena> obtenerResenasPorUsuario(HttpSession session);
    
    public void eliminarResena(Resena Resena);
    
    public void editarResena(Resena Resena);
}
