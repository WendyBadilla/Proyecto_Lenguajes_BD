
package com.EatAway.dao;

import com.EatAway.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoDao  extends JpaRepository<Evento,Long> {
    
}
