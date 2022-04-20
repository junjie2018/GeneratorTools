package com.example.generator.tools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "packages")
public class PackagesProperties {
    /**
     * service包
     */
    private String service;

    /**
     * mapper包
     */
    private String mapper;

    /**
     * entity包
     */
    private String entity;

    /**
     * request包
     */
    private String request;

    /**
     * response包
     */
    private String response;

    /**
     * response包
     */
    private String responseData;

    /**
     * enums包
     */
    private String enums;

    /**
     * controller包
     */
    private String controller;
}
