package com.example.generator.tools.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    /**
     * 表逻辑名称
     */
    private String logicName;

    /**
     * 表注释信息
     */
    private String comment;

    /**
     * 表实体名称
     * <p>
     * 默认取表的注释信息，可由用户进行配置
     */
    private String entityName;

    /**
     * 根据当前表生成Bean，则Bean的名称
     * <p>
     * 该名称去除了表前缀，并符合Java类的命名规范
     */
    private String beanClass;

    /**
     * 根据当前表生成Bean，则该Bean的对象的名称
     * <p>
     * 该名称去除了表前缀，并符合Java属性的命名规范
     */
    private String beanObject;

    /**
     * 表中所拥有的所有字段
     */
    private List<Column> columns;

    /**
     * 表中所拥有的内部类
     */
    private List<Enumeration> enumerations;

    /**
     * 表中所拥有的内部Bean
     */
    private List<InternalBean> internalBeans;

}
