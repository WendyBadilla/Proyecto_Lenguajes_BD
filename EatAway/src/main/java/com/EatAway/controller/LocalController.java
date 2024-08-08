package com.EatAway.controller;

import com.EatAway.domain.Local;
import com.EatAway.domain.Resena;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.EatAway.service.LocalService;
import com.EatAway.service.ResenaService;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LocalController {

    @Autowired
    private LocalService restbarService;
    
    @Autowired
    private ResenaService resenaService;

    @GetMapping("/")
    public String inicio(Model model) {
        List<Local> listado = restbarService.getLocales();

        model.addAttribute("locales", listado);
        return "index";
    }

    @GetMapping("/detalle/{idLocal}")
    public String restbarDetalle(@PathVariable("idLocal") long idLocal, Model model) {
        // Obtener el local por ID
        Local localDetails = restbarService.getLocalID(idLocal);

        List<Resena> resenas = resenaService.getResenasPorLocal(idLocal);
        
        // Añadir el local al modelo
        model.addAttribute("local", localDetails);
        model.addAttribute("listadoResena", resenas);
        
        return "/locales/detalle";
    }

}
