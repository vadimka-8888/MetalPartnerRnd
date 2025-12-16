package ru.metal_partner_rnd.ecommerce_core;

//import java.io.InputStream;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import org.thymeleaf.templatemode.TemplateMode;

@SpringBootApplication
public class EcommerceCoreApplication implements WebMvcConfigurer {

	static {
		//forced log config file loading - spring reads properties from application.properties after the log4j2 is initialized
		System.setProperty("log4j.configurationFile", "logging/log4j2-spring.xml");
		System.setProperty("logging.logFileName.general", "/var/log/metal_partner_rnd/environment");
		System.setProperty("logging.logFileName.buisness", "/var/log/metal_partner_rnd/buisness");
		System.setProperty("logging.layoutFileName", "logging/logging_configs/main_log4j_layout.json");
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EcommerceCoreApplication.class);
		System.out.println("in the context");
		application.run(args);
	}

	@Autowired
	ApplicationContext  applicationContext;

	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//static resources

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
		registry.addResourceHandler("/icons/**").addResourceLocations("classpath:/static/icons/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("classpath:/static/messages");
		return messageSource;
	}

	//thymeleaf artifacts

	@Bean
	public SpringResourceTemplateResolver mainTemplateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine mainTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(mainTemplateResolver());
		templateEngine.setEnableSpringELCompiler(true);

		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver mainViewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setOrder(1);
		//viewResolver.setViewNames(new String[] {".html"});

		return viewResolver;
	}
}