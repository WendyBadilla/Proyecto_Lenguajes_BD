package com.EatAway.controller;

import com.EatAway.domain.Local;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.EatAway.service.LocalService;

@Controller
public class LocalController {
    
    @Autowired
    private LocalService restbarService; 

    
    @GetMapping("/")
    public String inicio(Model model) {
        List<Local> listado = restbarService.getLocales();

        model.addAttribute("locales", listado);
        return "index";
    }

}