package com.example.generator.tools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

//@Data
//@Component
//@ConfigurationProperties(prefix = "templates")
public class TemplatesProperties {


    public static class TemplateProperties {

        /**
         * 模板文件
         */
        private String template;

        /**
         * 当前模板所属的模块
         */
        private String module;

        /**
         * 当前模板所生成
         */
        private String filename;
        private String packet;
        private List<String> packetsToImport;
    }

}
