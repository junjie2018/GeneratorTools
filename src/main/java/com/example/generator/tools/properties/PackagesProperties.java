package com.example.generator.tools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "packages")
public class PackagesProperties {
    private String service;
    private String mapper;
    private String entity;
    private String request;
    private String response;
    private String enums;
    private String controller;
}
