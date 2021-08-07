package com.example.generator.tools.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class PropertiesTest {

    @Autowired
    private PackagesProperties packagesProperties;

    @Autowired
    private TemplatesProperties templatesProperties;

    @Test
    public void test() {
        System.out.println("");
    }

}