package com.example.gestionrh.URLControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class URLController {
    @GetMapping("/index")
    public String home() {
        return "index"; // Redirige vers la racine de votre application frontend React
    }


}
