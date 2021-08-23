package com.example.generator.tools.utils;

import com.example.generator.tools.properties.ToolsProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TemplateUtilsTest {
    @Test
    public void test() {
        ToolsProperties toolsProperties = TemplateUtils.toolsProperties;
        System.out.println("");
    }
}
