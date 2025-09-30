package ru.metal_partner_rnd.ecommerce_core.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class HomeController {

    static final Logger logger = LogManager.getLogger(HomeController.class.getName());

    @RequestMapping("/")
    public String log(HttpServletRequest request) {
        logger.info("The http request have been received {}", 1);
        return "";
    }
}