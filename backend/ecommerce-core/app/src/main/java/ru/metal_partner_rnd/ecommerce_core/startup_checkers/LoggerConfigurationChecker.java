package ru.metal_partner_rnd.ecommerce_core.startup_checkers;

import ru.metal_partner_rnd.ecommerce_core.startup_checkers.Checker;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoggerConfigurationChecker implements Checker {

    @Override
    public String getName() {
        return "LoggerConfigurationChecker";
    }

    @Override
    public Map<String, String> check() {
        Map<String, String> result = new HashMap<String, String>();

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        result.put("configuration", config.getName());

        Map<String, Appender> appenders = config.getAppenders();
        result.put("all appenders", String.join(", ", appenders.keySet()));

        LoggerConfig rootConfig = config.getRootLogger();
        result.put("root level", rootConfig.getLevel().name());
        result.put("root appender refs", String.join(", ", rootConfig.getAppenderRefs().stream().map(ref -> ref.getRef() + " - " + ((ref.getLevel() != null) ? ref.getLevel().name() : "no level")).collect(Collectors.toList())));

        Map<String, LoggerConfig> loggers = config.getLoggers();
        result.put("loggers", String.join(", ", loggers.entrySet().stream().map(pair -> "logger-" + pair.getKey() + " - " + pair.getValue().getLevel().name()).collect(Collectors.toList())));

        return result;
    }
}