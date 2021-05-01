package com.example.hair_care_tracker_api;

import com.example.hair_care_tracker_api.filter.AdminFilter;
import com.example.hair_care_tracker_api.filter.UserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point of our REST API.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class HairCareTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairCareTrackerApiApplication.class, args);
    }

    /**
     * Bean for setting up user filter and adding the urls to be filtered.
     *
     * @return filterRegistrationBean
     */
    @Bean(name = "User filter")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new UserFilter());
        filterRegistrationBean.setName("User filter");
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/profile");
        urlPatterns.add("/products");
        urlPatterns.add("/userproducts");
        urlPatterns.add("/routines");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }

    /**
     * Bean for setting up admin filter and adding the urls to be filtered.
     *
     * @return filterRegistrationBean
     */
    @Bean(name = "Admin filter")
    public FilterRegistrationBean filterRegistrationBean1() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new AdminFilter());
        filterRegistrationBean.setName("Admin filter");
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/users/*");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }

}
