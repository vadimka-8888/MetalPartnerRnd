package ru.metal_partner_rnd.ecommerce_core.startup_checkers;

import ru.metal_partner_rnd.ecommerce_core.startup_checkers.Checker;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.HashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResourceChecker implements Checker {

    @Override
    public String getName() {
        return "ResourceChecker";
    }

    @Override
    public Map<String, String> check() {
        String prefix = "/templates/";
        String suffix = ".html";
        String[] templates = new String[] {"home", "error", "templatePage", "admin/index", "admin/prices", "admin/article", "admin/company_data"};

        Map<String, String> result = new HashMap<String, String>();
        for (String item : templates) {
            ClassPathResource clpResource = new ClassPathResource(prefix + item + suffix);
            result.put(item, Boolean.toString(clpResource.exists()));
        }
        return result;
    }
}