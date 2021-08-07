package com.example.generator.tools;

import com.example.generator.tools.properties.PackagesProperties;
import com.example.generator.tools.properties.ProjectProperties;
import com.example.generator.tools.properties.TemplatesProperties;
import com.example.generator.tools.properties.ToolsProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
        TemplatesProperties bean4 = context.getBean(TemplatesProperties.class);
        System.out.println("");
    }

}
