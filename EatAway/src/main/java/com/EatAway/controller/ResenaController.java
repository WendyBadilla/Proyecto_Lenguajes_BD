package com.EatAway.controller;

import com.EatAway.domain.Resena;
import com.EatAway.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ResenaController {
    
    @Autowired
    private ResenaService resenaService;
    
    @PostMapping("/enviar")
    public String resenaGuardar(Resena resena) {
        resenaService.saveResena(resena);
        
        return "redirect:/";
    }
}