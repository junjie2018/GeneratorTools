package com.example.generator.tools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    /**
     * 项目根目录
     * <p>
     * 目前仅仅支持双层结构，即：
     * <p>
     * |-- parent:
     * |--|-- common
     * |--|-- server
     * |--|-- client
     */
    private String rootDirectory;

    /**
     * server模块名称
     */
    private String serverModule;

    /**
     * common模块名称
     */
    private String commonModule;

}
