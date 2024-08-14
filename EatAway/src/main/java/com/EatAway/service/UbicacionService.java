
package com.EatAway.service;

import com.EatAway.domain.Ubicacion;
import java.util.List;

public interface UbicacionService {
    
    List<Ubicacion> getUbicacionesPorLocal(long local);
    
}
