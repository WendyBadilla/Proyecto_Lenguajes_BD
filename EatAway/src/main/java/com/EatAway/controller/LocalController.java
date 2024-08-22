package com.EatAway.controller;

import com.EatAway.domain.Categoria;
import com.EatAway.domain.Fotos;
import com.EatAway.domain.Local;
import com.EatAway.domain.Resena;
import com.EatAway.domain.Ubicacion;
import com.EatAway.service.FotosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.EatAway.service.LocalService;
import com.EatAway.service.ResenaService;
import com.EatAway.service.UbicacionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocalController {

    @Autowired
    private LocalService restbarService;
    
    @Autowired
    private ResenaService resenaService;
    
    @Autowired
    private UbicacionService ubicacionService;
    
    @Autowired
    private FotosService fotosService;

    @GetMapping("/")
    public String inicio(Model model) {
        List<Local> listado = restbarService.getLocales();
        
        // Obtener tipos de local
        List<Categoria> categorias = restbarService.obtenerTiposEstablecimiento();

        model.addAttribute("locales", listado);
        model.addAttribute("categorias", categorias);
         
        return "index";
    }

    @GetMapping("/detalle/{idLocal}")
    public String restbarDetalle(@PathVariable("idLocal") long idLocal, Model model) {
        // Obtener el local por ID
        Local localDetails = restbarService.getLocalID(idLocal);

        List<Resena> resenas = resenaService.getResenasPorLocal(idLocal);
        
        List<Ubicacion> ubicacion = ubicacionService.getUbicacionesPorLocal(idLocal);
        
        List<Fotos> fotos = fotosService.getFotosPorLocal(idLocal);
        
        // Añadir el local al modelo
        model.addAttribute("local", localDetails);
        model.addAttribute("listadoResena", resenas);
        model.addAttribute("listadoUbicaciones", ubicacion);
        model.addAttribute("listadoFotos", fotos);
        
        return "/locales/detalle";
    }

    @GetMapping("/filtro")
public String filtrarLocales(@RequestParam(required = false) String tipoEstablecimiento, Model model) {
    List<Local> locales;
    
    if (tipoEstablecimiento == null || tipoEstablecimiento.isEmpty()) {
        // Si no se selecciona nada, carga todos los locales
        locales = restbarService.getLocales();
    } else {
        // Filtra los locales por el tipo seleccionado
        locales = restbarService.encontrarTipoEstablecimiento(tipoEstablecimiento);
    }
    
    model.addAttribute("locales", locales);
    return "inicio/fragmentosInicio :: cardsLocal";
}



}
