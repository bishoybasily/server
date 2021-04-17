package com.gmail.bishoybasily.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@SpringBootApplication
public class ServerApplication implements WebMvcConfigurer {

    private final String webContentPath = "/web/content/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(String.format("file:///%s", webContentPath))
                .setCachePeriod(0);
    }

    @Bean
    public ITemplateResolver templateResolver() {
        AbstractConfigurableTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(webContentPath);
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean
    public ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    @Bean
    public ViewResolver viewResolver(ISpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        return resolver;
    }

    @Controller
    public static class MainController {

        @RequestMapping("/")
        public String home() {
            return "index";
        }

        @GetMapping(value = "/**/{[path:[^\\.]*}")
        public String redirect() {
            return "forward:/";
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}


