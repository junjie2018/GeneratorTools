package com.example.generator.tools;

import com.example.generator.tools.properties.ToolsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GeneratorToolsApplication {

    @Autowired
    private ToolsProperties toolsProperties;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GeneratorToolsApplication.class, args);
        ToolsProperties bean = context.getBean(ToolsProperties.class);
        System.out.println("");
    }

}
