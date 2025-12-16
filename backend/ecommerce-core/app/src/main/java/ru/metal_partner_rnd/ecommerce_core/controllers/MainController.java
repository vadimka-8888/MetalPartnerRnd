package ru.metal_partner_rnd.ecommerce_core.controllers;

import ru.metal_partner_rnd.ecommerce_core.utils.PageTextLoader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.HashMap;

@Controller
public class MainController {

    private Map<String, String> textCache = new HashMap<>();

    private PageTextLoader textLoader;

    public MainController() {
        textLoader = new PageTextLoader();
        String[] names = {"about", "metal_products", "prices"};
        for (String name : names) {
            textCache.put(name, getTextForPage(name, false));
        }
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String pageHome(HttpServletRequest request) throws Exception {

        return "home";
    }

    @RequestMapping(value = "/section/{name}", method = RequestMethod.GET)
    public String pageSection(HttpServletRequest request, Model model, @PathVariable String name) throws Exception {

        textCache.containsKey("key");

        String text = getTextForPage(name, true);
        model.addAttribute("mainText", text);
        return "templatePage";
    }

    @ModelAttribute("email")
    public String setEmail() {
        return "katerina-avangardservice@mail.ru";
    }

    @ModelAttribute("phone")
    public String setPhone() {
        return "+7-989-529-71-21";
    }

    public String getTextForPage(String name, Boolean checkCache) {
        if (checkCache && textCache.containsKey(name)) {
            String value = textCache.get(name);
            if (value != null) {
                return value;
            }
        }
        String[] statusWithText = textLoader.loadPageText(name);
        return statusWithText[1];
    }
}