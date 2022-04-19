package com.example.generator.tools.generator.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.example.generator.tools.utils.JStringUtils.underlineToCamel;

public interface TableInfo {
    /**
     * 获取数据库中表名称
     */
    String getTableName();

    /**
     * 设置数据库中表名称
     */
    void setTableName(String tableName);

    /**
     * 获取数据库中表描述
     */
    String getTableComment();

    /**
     * 设置数据库中的表描述
     */
    void setTableComment(String tableComment);

    /**
     * 获取表下所有的列信息
     */
    List<ColumnInfo> getColumnInfos();

    /**
     * 设置表下所有的列信息
     */
    void setColumnInfos(List<ColumnInfo> columnInfos);

    /**
     * 表中所拥有的内部类
     */
    List<EnumInfo> getEnumInfos();

    /**
     * 表中所拥有的内部Bean
     */
    List<InternalInfo> getInternalInfos();

    /**
     * 将表名转换成一个类的名称
     * 要求：去除表前缀，且首字母大写，下划线转驼峰
     */
    default String getTableClass() {
        return StringUtils.capitalize(underlineToCamel(getTableName()));
    }

    /**
     * 将表名转换成一个对象的名称
     * 要求：去除表前缀，且首字母小写，下划线转驼峰
     */
    default String getTableObject() {
        return StringUtils.uncapitalize(underlineToCamel(getTableName()));
    }

    /**
     * 将表名转换成一个类的名称
     * 要求：不去除表前缀，且首字母大写，下划线转驼峰
     */
    default String getTableClassWithPrefix() {
        return StringUtils.capitalize(underlineToCamel(getTableName()));
    }

    /**
     * 将表名转换成一个对象的名称
     * 要求：不去除表前缀，且首字母小写，下划线转驼峰
     */
    default String getTableObjectWithPrefix() {
        return StringUtils.uncapitalize(underlineToCamel(getTableName()));
    }
}
