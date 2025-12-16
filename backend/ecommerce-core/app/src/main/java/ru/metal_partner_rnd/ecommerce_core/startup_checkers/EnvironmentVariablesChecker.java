package ru.metal_partner_rnd.ecommerce_core.startup_checkers;

import ru.metal_partner_rnd.ecommerce_core.startup_checkers.Checker;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.HashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnvironmentVariablesChecker implements Checker {

    @Autowired
    private Environment env;

    @Override
    public String getName() {
        return "EnvironmentVariablesChecker";
    }

    @Override
    public Map<String, String> check() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("logging.level.root", env.getProperty("logging.level.root"));
        properties.put("logging.level", env.getProperty("logging.level"));
        properties.put("logging.pattern.console", env.getProperty("logging.pattern.console"));
        return properties;
    }
}