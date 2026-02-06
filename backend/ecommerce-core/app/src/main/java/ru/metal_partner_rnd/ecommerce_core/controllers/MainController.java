package ru.metal_partner_rnd.ecommerce_core.controllers;

//import ru.metal_partner_rnd.ecommerce_core.utils.PageTextLoader;
import ru.metal_partner_rnd.ecommerce_core.utils.*;

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
import java.util.ArrayList;

@Controller
public class MainController {

    private Map<String, String> textCache = new HashMap<String, String>();

    private PageTextLoader textLoader;
    private AuxiliaryFilesIntroducer supporter; //path - set of pages to be used in tamplate
    boolean pageOpened;

    public MainController() {
        textLoader = new PageTextLoader();
        supporter = new GeneralSupporter();
        pageOpened = false;
        
        String[] names = {"about", "metal", "pricelist"};
        for (String name : names) {
            textCache.put(name, getTextForPage(name, false));
        }
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String pageHome(HttpServletRequest request, Model model) throws Exception {
        String text = getTextForPage("home", true);

        model.addAttribute("js", supporter.getJs(0, ""));
        //model.addAttribute("mainText", text);

        return  "start/home";
    }

    @RequestMapping(value = "/section/{name}", method = RequestMethod.GET)
    public String page(HttpServletRequest request, Model model, @PathVariable String name) throws Exception {
        String text = getTextForPage(name, true);

        model.addAttribute("fileNames", supporter.getFilesToInsert(name));
        model.addAttribute("js", supporter.getJs(1, name));
        model.addAttribute("headerState", pageOpened ? "static" : "starting");
        pageOpened = true;
        //model.addAttribute("mainText", text);

        return "page/page";
    }

    @ModelAttribute("email")
    public String setEmail() {
        return "katerina-avangardservice@mail.ru";
    }

    @ModelAttribute("phone")
    public String setPhone() {
        return "+7-989-529-71-21";
    }

    @ModelAttribute("myObjects")
    public ArrayList<Integer> setMyObjects() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            res.add(i);
        }
        return res;
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