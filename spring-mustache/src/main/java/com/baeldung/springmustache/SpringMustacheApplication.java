package com.baeldung.springmustache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.samskivert.mustache.Mustache;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung"})
public class SpringMustacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMustacheApplication.class, args);
    }

    @Bean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader templateLoader, Environment environment) {

        MustacheEnvironmentCollector collector = new MustacheEnvironmentCollector();
        collector.setEnvironment(environment);

        Mustache.Compiler compiler = Mustache.compiler()
          .defaultValue("Some Default Value")
          .withLoader(templateLoader)
          .withCollector(collector);
        return compiler;

    }
}

