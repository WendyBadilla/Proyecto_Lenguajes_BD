
package com.EatAway.controller;

import com.EatAway.domain.Promociones;
import com.EatAway.service.PromocionesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PromocionesController {
    
    @Autowired
    private PromocionesService promocionesService; 
    
    @GetMapping("/promociones")
    public String inicio(Model model) {
        List<Promociones> listado = promocionesService.getPromociones();

        model.addAttribute("promociones", listado);
        return "/evento/promociones";
    }
}
