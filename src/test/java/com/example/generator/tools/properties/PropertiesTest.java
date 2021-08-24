package com.example.generator.tools.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PropertiesTest {

    @Autowired
    private PackagesProperties packagesProperties;

    @Autowired
    private ProjectProperties projectProperties;

    @Autowired
    private TableProperties tableProperties;

    @Autowired
    private TemplateConfigsProperties templateConfigsProperties;

    @Autowired
    private ToolsProperties toolsProperties;


    @Test
    public void test() {
        System.out.println("");
    }

}