package com.baeldung.bootcustomfilters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.bootcustomfilters.filters.RequestResponseLoggingFilter;

// uncomment this and comment the @Component in the filter class definition to register only for a url pattern
//@Configuration
public class FilterConfig {

    //@Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter(){
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new RequestResponseLoggingFilter());
        
        registrationBean.addUrlPatterns("/users/*");
        
        return registrationBean;
        
    }
    
}
