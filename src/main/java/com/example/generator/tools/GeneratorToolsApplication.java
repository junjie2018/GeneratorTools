package com.example.generator.tools;

import com.example.generator.tools.properties.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GeneratorToolsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GeneratorToolsApplication.class, args);
        ToolsProperties bean = context.getBean(ToolsProperties.class);
        ProjectProperties bean2 = context.getBean(ProjectProperties.class);
        PackagesProperties bean3 = context.getBean(PackagesProperties.class);
        TemplateConfigsProperties bean4 = context.getBean(TemplateConfigsProperties.class);
        TableProperties bean5 = context.getBean(TableProperties.class);
        System.out.println("");
    }

}
