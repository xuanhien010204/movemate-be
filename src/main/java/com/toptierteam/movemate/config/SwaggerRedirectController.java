package com.toptierteam.movemate.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/swagger")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/")
    public String redirectRootToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
