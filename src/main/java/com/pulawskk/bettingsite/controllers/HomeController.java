package com.pulawskk.bettingsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index", "/login"})
    public String indexPage() {
        return "login";
    }
}
