package com.example.generator.tools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Data
@Component
@ConfigurationProperties(prefix = "tools")
public class ToolsProperties {

    /**
     * 模板文件根目录
     */
    private Path templateDirectory;

    /**
     * 枚举的模式
     */
    private Pattern enumCommentPattern;

    /**
     * 数字的模式
     */
    private Pattern numberPattern;

}