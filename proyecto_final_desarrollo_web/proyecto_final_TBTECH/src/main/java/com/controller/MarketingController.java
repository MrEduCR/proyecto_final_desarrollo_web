package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MarketingController {
    @GetMapping("/marketing")
    public String mostrarMarketing() {
        return "marketing/marketing"; // solo para que thymeleaf busque al marketing, no se ocupa ni service ni domain ni repo xq no hay entidades ni nada
    }
}
