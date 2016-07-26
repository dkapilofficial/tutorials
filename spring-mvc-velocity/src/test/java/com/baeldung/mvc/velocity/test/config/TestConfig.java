package com.baeldung.mvc.velocity.test.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import com.baeldung.mvc.velocity.service.ITutorialsService;

@Configuration
public class TestConfig {
	

    @Bean
    public ViewResolver viewResolver() {
        final VelocityLayoutViewResolver bean = new VelocityLayoutViewResolver();
        bean.setCache(true);
        bean.setPrefix("/WEB-INF/views/");
        bean.setLayoutUrl("/WEB-INF/layouts/layout.vm");
        bean.setSuffix(".vm");
        return bean;
    }
    
    @Bean
    public VelocityConfigurer velocityConfig() {
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoaderPath("/");
        return velocityConfigurer;
    }
    
    @Bean
    public ITutorialsService getTutServiceMock() {
        return Mockito.mock(ITutorialsService.class);
    }

}
