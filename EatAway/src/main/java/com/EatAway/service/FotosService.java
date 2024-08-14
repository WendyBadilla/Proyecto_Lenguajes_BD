
package com.EatAway.service;

import com.EatAway.domain.Fotos;
import java.util.List;

public interface FotosService {
    
    List<Fotos> getFotosPorLocal(long local);
}
