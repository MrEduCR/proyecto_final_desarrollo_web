package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    @GetMapping("/blog")
    public String mostrarBlog() {
        return "blog/blog"; // solo para que thymeleaf busque al blog, no se ocupa ni service ni domain ni repo
    }
}
