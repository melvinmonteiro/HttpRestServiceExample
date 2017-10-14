package com.example.restservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by melvin on 10/13/17.
 */
@Configuration
@EnableWebMvc
@ImportResource(locations = "classpath:spring-servlet-integration.xml")
public class IntegrationTestWebAppContext {

}
