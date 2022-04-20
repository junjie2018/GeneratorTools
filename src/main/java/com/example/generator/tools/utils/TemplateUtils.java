package com.example.generator.tools.utils;

import com.example.generator.tools.directives.FragmentDirective;
import com.example.generator.tools.directives.IncludeDirective;
import com.example.generator.tools.domain.Enumeration;
import com.example.generator.tools.domain.Table;
import com.example.generator.tools.properties.PackagesProperties;
import com.example.generator.tools.properties.ProjectProperties;
import com.example.generator.tools.properties.TemplateConfigsProperties;
import com.example.generator.tools.generator.properties.ToolsProperties;
import com.example.generator.tools.utils.writer.WriterWithGoogleJavaFormat;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;

import static com.example.generator.tools.constant.SystemConstant.FRAGMENT_PATTERN;
import static com.example.generator.tools.utils.AssertUtils.assertNotEqual;

@Slf4j
//@Component
public class TemplateUtils implements ApplicationContextAware {

    private static TemplateConfigsProperties templateConfigsProperties;
    private static ProjectProperties projectProperties;
    private static ToolsProperties toolsProperties;
    private static PackagesProperties packagesProperties;

    private static Configuration CONFIGURATION;

    private final static Map<String, Path> FILENAME_TO_FILE_PATH = new HashMap<>();
    private final static Map<String, String> TEMPLATE_KEY_TO_TEMPLATE_CONTENT = new HashMap<>();
    private final static Map<String, String> FRAGMENT_KEY_TO_TEMPLATE_CONTENT = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        templateConfigsProperties = applicationContext.getBean(TemplateConfigsProperties.class);
        projectProperties = applicationContext.getBean(ProjectProperties.class);
        toolsProperties = applicationContext.getBean(ToolsProperties.class);
        packagesProperties = applicationContext.getBean(PackagesProperties.class);

        // 通过profiles active得到项目名称
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

        assertNotEqual(activeProfiles.length, 1,
                "配置文件数量错误：" + Arrays.toString(activeProfiles));

        loadTemplate(activeProfiles[0]);
        loadFragment();
        configFreeMarker();
    }

    private void loadTemplate(String projectName) {
        // 加载默认文件夹中的内容
        FileUtils.walkThrough(
                Paths.get(toolsProperties.getTemplateDirectory().toString(), "_default"),
                path -> {
                    FILENAME_TO_FILE_PATH.put(path.getFileName().toString(), path);
                });

        // 用项目配置中同名文件进行覆盖
        Path projectTemplatePath = Paths.get(toolsProperties.getTemplateDirectory().toString(), projectName);
        if (Files.exists(projectTemplatePath)) {
            FileUtils.walkThrough(projectTemplatePath, path -> {
                FILENAME_TO_FILE_PATH.put(path.getFileName().toString(), path);
            });
        }


        // 检查配置中模板是否都存在
        for (TemplateConfigsProperties.TemplateConfig templateConfig
                : templateConfigsProperties.getTemplateConfigs().values()) {
            if (!FILENAME_TO_FILE_PATH.containsKey(templateConfig.getTemplate())) {
                throw new RuntimeException("配置文件中配置了，而不存在该模板：" + templateConfig.getTemplate());
            }
        }

        // 检查模板文件是否都存在配置
        for (String filename : FILENAME_TO_FILE_PATH.keySet()) {
            Optional<TemplateConfigsProperties.TemplateConfig> templateConfigOptional =
                    templateConfigsProperties.getTemplateConfigs().values()
                            .stream()
                            .filter(item -> item.getTemplate().equals(filename))
                            .findFirst();

            if (templateConfigOptional.isEmpty()) {
                throw new RuntimeException("模板文件存在，而未在配置文件中配置：" + filename);
            }
        }

        // 建立templateKey到templateContent的映射
        try {
            for (Map.Entry<String, TemplateConfigsProperties.TemplateConfig> entry
                    : templateConfigsProperties.getTemplateConfigs().entrySet()) {

                String templateContent = FileUtils.readAllContent(
                        FILENAME_TO_FILE_PATH.get(entry.getValue().getTemplate()));

                TEMPLATE_KEY_TO_TEMPLATE_CONTENT.put(entry.getKey(), templateContent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFragment() {
        FileUtils.walkThrough(
                Paths.get(toolsProperties.getTemplateDirectory().toString(), "_fragments"),
                path -> {

                    String fragmentFileContent = FileUtils.readAllContent(path);

                    Path fragmentFilename = path.getFileName();

                    Matcher fragmentMatcher = FRAGMENT_PATTERN.matcher(fragmentFileContent);
                    while (fragmentMatcher.find()) {
                        FRAGMENT_KEY_TO_TEMPLATE_CONTENT.put(
                                fragmentFilename + "#" + fragmentMatcher.group(1),
                                fragmentMatcher.group(2));
                    }
                });
    }

    private void configFreeMarker() {

        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();

        // 加载模板信息
        for (Map.Entry<String, String> entry : TEMPLATE_KEY_TO_TEMPLATE_CONTENT.entrySet()) {
            stringTemplateLoader.putTemplate(entry.getKey(), entry.getValue());
        }

        // 加载片段信息
        for (Map.Entry<String, String> entry : FRAGMENT_KEY_TO_TEMPLATE_CONTENT.entrySet()) {
            stringTemplateLoader.putTemplate(entry.getKey(), entry.getValue());
        }

        CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);
        CONFIGURATION.setObjectWrapper(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_31).build());
        CONFIGURATION.setSharedVariable("fragment", new FragmentDirective());
        CONFIGURATION.setSharedVariable("include", new IncludeDirective());
        CONFIGURATION.setTemplateLoader(stringTemplateLoader);
    }











    public static void renderTpl(String templateKey, Table table) {

        TemplateConfigsProperties.TemplateConfig templateConfig = templateConfigsProperties
                .getTemplateConfigs()
                .get(templateKey);

        AssertUtils.assertNotNull(templateConfig, "未配置该模板");

        try {
            Map<String, Object> renderData = initMap(templateConfig, table);
            processRender(templateKey, templateConfig, renderData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void renderTpl(String templateKey, Enumeration enumeration) {

        TemplateConfigsProperties.TemplateConfig templateConfig = templateConfigsProperties
                .getTemplateConfigs()
                .get(templateKey);

        AssertUtils.assertNotNull(templateConfig, "未配置该模板");

        try {
            Map<String, Object> renderData = initMap(templateConfig, enumeration);
            processRender(templateKey, templateConfig, renderData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void processRender(String templateKey,
                                     Map<String, Object> renderData) throws IOException, TemplateException {

        // 得到输出文件的FileWriter
//        Path outputDirectorPath = Paths.get(projectProperties.getRootDirectory(),
//                templateConfig.getModule(),
//                "src/main/java",
//                templateConfig.getPacket().replace('.', '/'));
//
//        if (!Files.exists(outputDirectorPath)) {
//            Files.createDirectories(outputDirectorPath);
//        }
//
//        Path outputFilePath =
//                Paths.get(outputDirectorPath.toString(), getOutputFileName(templateConfig.getTemplate(), renderData));

//        Template template = CONFIGURATION.getTemplate(templateKey);
//        if (template == null) {
//            throw new RuntimeException("获取模板信息失败");
//        }
//
//        template.process(renderData, new WriterWithGoogleJavaFormat(new FileWriter(outputFilePath.toString())));
    }

    public static void processRender(String templateKey,
                                     TemplateConfigsProperties.TemplateConfig templateConfig,
                                     Map<String, Object> renderData) throws IOException, TemplateException {

        // 得到输出文件的FileWriter
        Path outputDirectorPath = Paths.get(projectProperties.getRootDirectory(),
                templateConfig.getModule(),
                "src/main/java",
                templateConfig.getPacket().replace('.', '/'));

        if (!Files.exists(outputDirectorPath)) {
            Files.createDirectories(outputDirectorPath);
        }

        Path outputFilePath =
                Paths.get(outputDirectorPath.toString(), getOutputFileName(templateConfig.getTemplate(), renderData));

        Template template = CONFIGURATION.getTemplate(templateKey);
        if (template == null) {
            throw new RuntimeException("获取模板信息失败");
        }

        template.process(renderData, new WriterWithGoogleJavaFormat(new FileWriter(outputFilePath.toString())));
    }

    private static Map<String, Object> initMap(TemplateConfigsProperties.TemplateConfig templateConfig, Table table) {
        Map<String, Object> renderDataMap = new HashMap<>();

        // Table
        renderDataMap.put("logicName", table.getLogicName());
        renderDataMap.put("comment", table.getComment());
        renderDataMap.put("entityName", table.getEntityName());
        renderDataMap.put("beanClass", table.getBeanClass());
        renderDataMap.put("beanObject", table.getBeanObject());
        renderDataMap.put("columns", table.getColumns());
        renderDataMap.put("internalBeans", table.getInternalBeans());

        // TemplatesProperties.TemplateConfig
        renderDataMap.put("packet", templateConfig.getPacket());
        renderDataMap.put("packetsToImport", templateConfig.getPacketsToImport());

        // PackagesProperties
        renderDataMap.put("packagesProperties", packagesProperties);

        return renderDataMap;
    }

    private static Map<String, Object> initMap(TemplateConfigsProperties.TemplateConfig templateConfig, Enumeration enumeration) {

        Map<String, Object> renderDataMap = new HashMap<>();

//        // Table
//        renderDataMap.put("enumClass", enumeration.getEnumClass());
//        renderDataMap.put("enumObject", enumeration.getEnumObject());
//        renderDataMap.put("comment", enumeration.getComment());
//        renderDataMap.put("itemType", enumeration.getItemType());
//        renderDataMap.put("enumItems", enumeration.getEnumItems());
//
//        // TemplatesProperties.TemplateConfig
//        renderDataMap.put("packet", templateConfig.getPacket());
//        renderDataMap.put("packetsToImport", templateConfig.getPacketsToImport());

        return renderDataMap;

    }


    private static String getOutputFileName(String outputFilePattern, Map<String, Object> renderData) {
        // 临时方案，以后会换成正则
        if (outputFilePattern.contains("${beanClass}")) {
            return outputFilePattern
                    .replace("${beanClass}", (String) renderData.get("beanClass"))
                    .replace("ftl", "java");
        } else if (outputFilePattern.contains("${enumClass}")) {
            return outputFilePattern
                    .replace("${enumClass}", (String) renderData.get("enumClass"))
                    .replace("ftl", "java");
        }
        return outputFilePattern.replace("ftl", "java");
    }
}
