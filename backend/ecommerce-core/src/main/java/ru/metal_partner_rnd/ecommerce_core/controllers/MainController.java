package ru.metal_partner_rnd.ecommerce_core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

@Controller
public class AdminController {

    @RequestMapping("/home")
    public String home() {
        return "home.html";
    }
}