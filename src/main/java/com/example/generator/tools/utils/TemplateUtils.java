package com.example.generator.tools.utils;

import com.example.generator.tools.properties.ProjectProperties;
import com.example.generator.tools.properties.TemplatesProperties;
import com.example.generator.tools.properties.ToolsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Component
public class TemplateUtils implements ApplicationContextAware {

    public static TemplatesProperties templatesProperties;
    public static ProjectProperties projectProperties;
    public static ToolsProperties toolsProperties;

    public static

    public static void test() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        templatesProperties = applicationContext.getBean(TemplatesProperties.class);
        projectProperties = applicationContext.getBean(ProjectProperties.class);
        toolsProperties = applicationContext.getBean(ToolsProperties.class);


        applicationContext.getEnvironment().getActiveProfiles();

        loadTemplate();
    }

    private void loadTemplate() {

        // 加载默认文件夹中的内容
        FileUtils.walkThrough(Paths.get(toolsProperties.getTemplateDirectory().toString(), "_default"), path -> {

        });



        // 加载项目文件夹中的内容，并覆盖默认文件夹中的模板配置


        Set<String> activeProfiles =
                new HashSet<String>(Arrays.asList(applicationContext.getEnvironment().getActiveProfiles()));

        FileUtils.listDirectories(toolsProperties.getTemplateDirectory(), path -> {
            Path fileName = path.getFileName();
            System.out.println("");
        });

    }
}
