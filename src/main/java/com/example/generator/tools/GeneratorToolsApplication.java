package com.example.generator.tools;

import com.example.generator.tools.properties.PackagesProperties;
import com.example.generator.tools.properties.TestProperties;
import com.example.generator.tools.properties.ToolsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GeneratorToolsApplication {

    @Autowired
    private ToolsProperties toolsProperties;
    @Autowired
    private TestProperties testProperties;
    @Autowired
    private PackagesProperties packagesProperties;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GeneratorToolsApplication.class, args);
        ToolsProperties bean = context.getBean(ToolsProperties.class);
        TestProperties bean2 = context.getBean(TestProperties.class);
        PackagesProperties bean3 = context.getBean(PackagesProperties.class);
        System.out.println("");
    }

}
