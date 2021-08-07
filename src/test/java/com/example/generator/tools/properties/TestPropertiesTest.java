package com.example.generator.tools.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class TestPropertiesTest {

    @Autowired
    private TestProperties testProperties;
    @Autowired
    private PackagesProperties packagesProperties;

    @Test
    public void test() {
        System.out.println("");
    }

}