
package com.EatAway.service;

import com.EatAway.domain.Categoria;
import com.EatAway.domain.Local;
import java.util.List;


public interface LocalService {
   
    public List<Local> getLocales();
    public Local getLocalID(long local);
    List<Local> encontrarTipoEstablecimiento(String tipoEstablecimiento);
    public List<Categoria> obtenerTiposEstablecimiento();
}
