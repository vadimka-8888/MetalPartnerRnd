package ru.metal_partner_rnd.ecommerce_core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImplementation implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("In CommandLineRunnerImplementation");
    }
}
