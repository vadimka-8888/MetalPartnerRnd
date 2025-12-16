package ru.metal_partner_rnd.ecommerce_core.runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import ru.metal_partner_rnd.aspects.LoggingAspect;
import ru.metal_partner_rnd.ecommerce_core.services.StartupService;

@Component
public class AfterContextInitialized implements CommandLineRunner {

    static {
        System.out.println("Loaded by: " + LoggingAspect.class.getClassLoader());
        System.out.println("Target loaded by: " + AfterContextInitialized.class.getClassLoader());
    }

    @Autowired
    private StartupService startupService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("from run");
        startupService.start();
    }
}
