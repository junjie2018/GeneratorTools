package com.example.generator.tools.utils;

import com.example.generator.tools.properties.ProjectProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JStringUtils implements ApplicationContextAware {

    public static ProjectProperties projectProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        projectProperties = applicationContext.getBean(ProjectProperties.class);
    }

    /**
     * 下划线转驼峰，并首字符大写
     */
    public static String underlineToCamelWithCapitalized(String inputStr) {

        if (StringUtils.isBlank(inputStr)) {
            return "";
        }

        inputStr = StringUtils.trim(inputStr);

        String[] segments = inputStr.split("_");
        if (segments.length > 0) {
            for (int i = 0; i < segments.length; i++) {
                segments[i] = StringUtils.capitalize(segments[i]);
            }
        }

        return StringUtils.join(segments);
    }

    /**
     * 下划线转驼峰，并首字符小写
     */
    public static String underlineToCamelWithUncapitalized(String inputStr) {

        if (StringUtils.isBlank(inputStr)) {
            return "";
        }

        inputStr = StringUtils.trim(inputStr);

        String[] segments = inputStr.split("_");
        if (segments.length > 0) {
            for (int i = 1; i < segments.length; i++) {
                segments[i] = StringUtils.capitalize(segments[i]);
            }
        }

        return StringUtils.join(segments);
    }

    /**
     * 中划线转驼峰，并首字母大写
     */
    public static String strikethroughToCamelWithUncapitalized(String inputStr) {
        if (StringUtils.isBlank(inputStr)) {
            return "";
        }

        inputStr = StringUtils.trim(inputStr);

        String[] segments = inputStr.split("-");
        if (segments.length > 0) {
            for (int i = 1; i < segments.length; i++) {
                segments[i] = StringUtils.capitalize(segments[i]);
            }
        }

        return StringUtils.join(segments);
    }

    /**
     * 移除表名前缀
     */
    public static String removeTableNamePrefix(String tableName) {
        if (tableName.startsWith(projectProperties.getTablePrefix())) {
            return tableName.substring(projectProperties.getTablePrefix().length());
        } else {
            return tableName;
        }
    }

    public static String removePrefix(String inputStr, String prefix) {
        if (StringUtils.isEmpty(prefix)) {
            return inputStr;
        }
        return inputStr.substring(prefix.length());
    }

    public static String underlineToCamel(String inputStr) {
        if (StringUtils.isBlank(inputStr)) {
            return "";
        }

        inputStr = StringUtils.trim(inputStr);

        String[] segments = inputStr.split("_");

        if (segments.length > 0) {
            for (int i = 1; i < segments.length; i++) {
                segments[i] = StringUtils.capitalize(segments[i]);
            }
        }

        return StringUtils.join(segments);

    }
}
