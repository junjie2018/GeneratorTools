package com.example.generator.tools.generator.utils;

import com.example.generator.tools.directives.FragmentDirective;
import com.example.generator.tools.directives.IncludeDirective;
import com.example.generator.tools.generator.properties.TemplateProperties;
import com.example.generator.tools.generator.properties.ToolsProperties;
import com.example.generator.tools.properties.PackagesProperties;
import com.example.generator.tools.properties.ProjectProperties;
import com.example.generator.tools.utils.AssertUtils;
import com.example.generator.tools.utils.FileUtils;
import com.example.generator.tools.utils.writer.WriterWithGoogleJavaFormat;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.generator.tools.constant.SystemConstant.FRAGMENT_PATTERN;
import static com.example.generator.tools.utils.AssertUtils.assertNotEqual;

@Slf4j
@Component
public class TemplateUtils implements ApplicationContextAware {


    private static final Pattern FILENAME_PATTERN = Pattern.compile(".*(\\$\\{([a-zA-Z]+)}).*");

    private static TemplateProperties templateProperties;
    private static ToolsProperties toolsProperties;
    private static ProjectProperties projectProperties;
    private static PackagesProperties packagesProperties;
    private static Configuration CONFIGURATION;

    private final static Map<String, Path> FILENAME_TO_FILE_PATH = new HashMap<>();
    private final static Map<String, String> TEMPLATE_KEY_TO_TEMPLATE_CONTENT = new HashMap<>();
    private final static Map<String, String> FRAGMENT_KEY_TO_TEMPLATE_CONTENT = new HashMap<>();

    /**
     * ????????????
     */
    public static void renderTpl(String templateKey, Map<String, Object> renderData) {
        TemplateProperties.Template template = templateProperties.getTemplates().get(templateKey);

        AssertUtils.assertNotNull(template, "??????????????????");

        renderData.put("packet", template.getPacket());
        renderData.put("packetsToImport", template.getPacketsToImport());
        renderData.put("packagesProperties", packagesProperties);

        processRender(template, renderData);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        templateProperties = applicationContext.getBean(TemplateProperties.class);
        toolsProperties = applicationContext.getBean(ToolsProperties.class);
        projectProperties = applicationContext.getBean(ProjectProperties.class);
        packagesProperties = applicationContext.getBean(PackagesProperties.class);

        // ??????profiles active??????????????????
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

        assertNotEqual(activeProfiles.length, 1,
                "???????????????????????????" + Arrays.toString(activeProfiles));

        loadTemplate(activeProfiles[0]);
        loadFragment();
        configFreeMarker();
    }

    private void loadTemplate(String projectName) {
        // ?????????????????????????????????
        FileUtils.walkThrough(
                Paths.get(toolsProperties.getTemplateDirectory().toString(), "_default"),
                path -> {
                    FILENAME_TO_FILE_PATH.put(path.getFileName().toString(), path);
                });

        // ??????????????????????????????????????????
        Path projectTemplatePath = Paths.get(toolsProperties.getTemplateDirectory().toString(), projectName);
        if (Files.exists(projectTemplatePath)) {
            FileUtils.walkThrough(projectTemplatePath, path -> {
                FILENAME_TO_FILE_PATH.put(path.getFileName().toString(), path);
            });
        }


        // ????????????????????????????????????
        for (TemplateProperties.Template template : templateProperties.getTemplates().values()) {
            if (!FILENAME_TO_FILE_PATH.containsKey(template.getTemplate())) {
                throw new RuntimeException("???????????????????????????????????????????????????" + template.getTemplate());
            }
        }

        // ???????????????????????????????????????
        for (String filename : FILENAME_TO_FILE_PATH.keySet()) {
            Optional<TemplateProperties.Template> templateOptional = templateProperties.getTemplates().values().stream()
                    .filter(item -> item.getTemplate().equals(filename))
                    .findFirst();

            if (templateOptional.isEmpty()) {
                throw new RuntimeException("??????????????????????????????????????????????????????" + filename);
            }
        }

        // ??????template?????????templateContent?????????
        try {
            for (Map.Entry<String, TemplateProperties.Template> entry : templateProperties.getTemplates().entrySet()) {

                String templateContent = FileUtils.readAllContent(
                        FILENAME_TO_FILE_PATH.get(entry.getValue().getTemplate()));

                TEMPLATE_KEY_TO_TEMPLATE_CONTENT.put(entry.getValue().getTemplate(), templateContent);
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

        // ??????????????????
        for (Map.Entry<String, String> entry : TEMPLATE_KEY_TO_TEMPLATE_CONTENT.entrySet()) {
            stringTemplateLoader.putTemplate(entry.getKey(), entry.getValue());
        }

        // ??????????????????
        for (Map.Entry<String, String> entry : FRAGMENT_KEY_TO_TEMPLATE_CONTENT.entrySet()) {
            stringTemplateLoader.putTemplate(entry.getKey(), entry.getValue());
        }

        CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);

        CONFIGURATION.setObjectWrapper(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_31).build());
        CONFIGURATION.setSharedVariable("fragment", new FragmentDirective());
        CONFIGURATION.setSharedVariable("include", new IncludeDirective());
        CONFIGURATION.setTemplateLoader(stringTemplateLoader);
    }


    private static void processRender(TemplateProperties.Template template, Map<String, Object> renderData) {

        try {
            Path outputFileDirectory = getOutputFileDirectory(template);

            if (!Files.exists(outputFileDirectory)) {
                Files.createDirectories(outputFileDirectory);
            }

            Path outputFilePath = Paths.get(outputFileDirectory.toString(), getOutputFilename(template, renderData));

            Template templateInFreemarker = CONFIGURATION.getTemplate(template.getTemplate());

            AssertUtils.assertNotNull(template, "????????????????????????");

            // ?????????java?????????
            if (template.getJavaFormat()) {
                templateInFreemarker.process(renderData,
                        new WriterWithGoogleJavaFormat(new FileWriter(outputFilePath.toString())));
            }
            // ?????????java?????????
            else {
                templateInFreemarker.process(renderData, new FileWriter(outputFilePath.toString()));
            }

            log.info("????????????{}???????????????????????????{}", template.getTemplate(), outputFilePath);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getOutputFileDirectory(TemplateProperties.Template template) {

        // ???????????????outputDirectory????????????outputDirectory
        if (template.getOutputDirectory() != null) {
            return template.getOutputDirectory();
        }

        // ??????????????????????????????????????????????????????
        if (template.getResourceDirectory()) {
            return Paths.get(projectProperties.getRootDirectory(),
                    template.getModule(),
                    "src/main/resources",
                    template.getPacket().replace('.', '/'));
        }

        // ??????????????????????????????????????????????????????
        return Paths.get(projectProperties.getRootDirectory(),
                template.getModule(),
                "src/main/java",
                template.getPacket().replace('.', '/'));
    }

    private static String getOutputFilename(TemplateProperties.Template template, Map<String, Object> renderData) {

        String filename = template.getOutputFilename();

        // ???????????????
        Matcher matcher = FILENAME_PATTERN.matcher(filename);

        if (matcher.matches()) {

            String replaceStr = matcher.group(1);
            String renderDataKey = matcher.group(2);
            String renderDataValue = renderData.get(renderDataKey).toString();

            filename = template.getOutputFilename().replace(replaceStr, renderDataValue);
        }

        return filename;
    }
}
