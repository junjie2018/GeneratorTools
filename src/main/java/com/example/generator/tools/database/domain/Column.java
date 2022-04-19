package com.example.generator.tools.database.domain;

import lombok.Data;

@Data
public class Column {
    /**
     * 列名
     */
    private String name;
    /**
     * 列描述
     */
    private String comment;
    /**
     * 列值类型
     */
    private String type;
    /**
     * 默认类型
     */
    private String defaultValue;
    /**
     * 是否为主键
     */
    private Boolean isPrimary;
    /**
     * 是否自增
     */
    private Boolean isAutoIncrement;

    /**
     * 是否是逻辑删除键
     */
    private Boolean isLogicDelete;

    /**
     * 是否是版本控制字段
     */
    private Boolean isVersion;
}
