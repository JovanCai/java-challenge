package jp.co.axa.apidemo.application.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    /**
     * Register filter
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        // Filter all paths
        registration.addUrlPatterns("/*");
        // Filter's name
        registration.setName("xssFilter");
        // Priority, the lower, the higher priority
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
