package ru.metal_partner_rnd.ecommerce_core.services;

import ru.metal_partner_rnd.ecommerce_core.startup_checkers.EnvironmentVariablesChecker;
import ru.metal_partner_rnd.ecommerce_core.startup_checkers.LoggerConfigurationChecker;
import ru.metal_partner_rnd.ecommerce_core.startup_checkers.ResourceChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.io.OutputStream;
import java.io.IOException;

@Component
public class StartupService {

    @Autowired
    private EnvironmentVariablesChecker envCheck;

    @Autowired
    private LoggerConfigurationChecker configCheck;

    @Autowired
    private ResourceChecker resourceCheck;
    
    public void start() {
        envCheck.check();
        configCheck.check();
        resourceCheck.check();
    }
}