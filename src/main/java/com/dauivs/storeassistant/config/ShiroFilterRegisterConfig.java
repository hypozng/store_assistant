package com.dauivs.storeassistant.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroFilterRegisterConfig {

    @Bean
    public FilterRegistrationBean shiroAccessFilterRegistration(ShiroAccessFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
