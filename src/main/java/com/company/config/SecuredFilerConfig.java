package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilerConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/region/*");
        bean.addUrlPatterns("/Apteka/*");
        bean.addUrlPatterns("/company/*");
        bean.addUrlPatterns("/medicine/*");
        bean.addUrlPatterns("/korzinka/*");
        bean.addUrlPatterns("/katalog/*");
        bean.addUrlPatterns("/comment/*");
        bean.addUrlPatterns("/AptekaMedicine/*");

        return bean;
    }

}
