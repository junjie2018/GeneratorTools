package com.example.generator.tools.properties;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TemplatesProperties implements ApplicationContextAware {

    private List<Template> templates;

    @Data
    public static class Template {

        /**
         * 模板文件
         */
        private String template;

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
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //noinspection unchecked
        templates = (List<Template>) applicationContext.getBean("templates");
    }

    @Configuration
    public static class TemplatesPropertiesInternalConfiguration {

        @Bean
        @ConfigurationProperties(prefix = "templates")
        public List<Template> templates(List<Template> templates) {
            return templates;
        }

    }
}
