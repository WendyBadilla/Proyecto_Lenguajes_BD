
package com.EatAway.service;

import com.EatAway.domain.Resena;
import java.util.List;


public interface ResenaService {
    
    List<Resena> getResenasPorLocal(long local);
    
    public void saveResena(Resena resena);
}
