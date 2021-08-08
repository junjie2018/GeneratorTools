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
public class TableProperties implements ApplicationContextAware {

    private List<Table> tables;


    @Data
    public static class Table {
        /**
         * 表中文名称
         */
        private String tableName;

        /**
         * 表逻辑名称
         */
        private String logicName;

        /**
         * 对应的实体名称
         */
        private String entityName;

        /**
         * 不需要生成的模板
         */
        private List<String> templatesExclude;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //noinspection unchecked
        tables = (List<Table>) applicationContext.getBean("tables");
    }

    @Configuration
    public static class TablePropertiesConfiguration {

        @Bean()
        @ConfigurationProperties(prefix = "tables")
        public List<Table> tables(List<Table> tables) {
            return tables;
        }
    }

}
