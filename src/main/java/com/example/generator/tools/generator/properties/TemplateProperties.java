package com.example.generator.tools.generator.properties;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TemplateProperties implements ApplicationContextAware {

    @Getter
    private Map<String, Template> templates;

    @Data
    public static class Template {

        /**
         * 模板文件
         */
        private String template;

        /**
         * 输出文件名或模式
         */
        private String outputFilename;

        /**
         * 输出文件目录（指定了该目录，则不会使用module、packet拼接路径，多用于模板测试）
         */
        private Path outputDirectory;

        /**
         * 当前模板所属的模块
         */
        private String module;

        /**
         * 当前模板生成的类所属的包
         */
        private String packet;

        /**
         * 当前模板生成的类额外需要导入的包
         */
        private List<String> packetsToImport;

        private Boolean javaFormat = true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //noinspection unchecked
        templates = (Map<String, Template>) applicationContext.getBean("templates");
    }

    @Configuration
    public static class TemplatesPropertiesInternalConfiguration {

        @Bean
        @ConfigurationProperties(prefix = "templates")
        public Map<String, Template> templates() {
            return new HashMap<>(0);
        }

    }
}
