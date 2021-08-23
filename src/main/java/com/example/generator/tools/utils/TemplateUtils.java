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
import java.util.*;
import java.util.stream.Stream;

import static com.example.generator.tools.constant.SystemConstant.SUFFIX_OF_TEMPLATES_CONFIG_FILE;

@Slf4j
@Component
public class TemplateUtils implements ApplicationContextAware {

    private static TemplatesProperties templatesProperties;
    private static ProjectProperties projectProperties;
    private static ToolsProperties toolsProperties;

    private static Map<String, Path> filenameToFilePath = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        templatesProperties = applicationContext.getBean(TemplatesProperties.class);
        projectProperties = applicationContext.getBean(ProjectProperties.class);
        toolsProperties = applicationContext.getBean(ToolsProperties.class);


        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

        // 去除templates配置，得到当前项目名称
        HashSet<String> activeProfilesSet = new HashSet<>(Arrays.asList(activeProfiles));

        if (activeProfilesSet.size() != 1) {
            throw new RuntimeException("配置文件数量错误：" + activeProfilesSet);
        }

        loadTemplate((String) activeProfilesSet.toArray()[0]);
    }

    private void loadTemplate(String projectName) {

        // 加载默认文件夹中的内容
        FileUtils.walkThrough(Paths.get(toolsProperties.getTemplateDirectory().toString(), "_default"), path -> {
            filenameToFilePath.put(path.getFileName().toString(), path);
        });

        // 用项目配置中同名文件进行覆盖
        FileUtils.walkThrough(Paths.get(toolsProperties.getTemplateDirectory().toString(), projectName), path -> {
            filenameToFilePath.put(path.getFileName().toString(), path);
        });
    }
}
