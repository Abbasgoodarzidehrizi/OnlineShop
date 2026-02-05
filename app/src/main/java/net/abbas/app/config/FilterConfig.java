package net.abbas.app.config;

import jakarta.servlet.Filter;
import net.abbas.app.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {
    private final JwtFilter jwtFilter;

    @Autowired
    public FilterConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(jwtFilter);
        filterRegistrationBean.addUrlPatterns("/api/panel/*");
        filterRegistrationBean.setName("jwtFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;

    }
}
