package com.example.generator.tools.generator.domain;

import org.apache.commons.lang3.StringUtils;

import static com.example.generator.tools.utils.JStringUtils.underlineToCamel;

public interface ColumnInfo {
    /**
     * 获取数据库中列名称
     */
    String getColumnName();

    /**
     * 设置数据库中列名称
     */
    void setColumnName(String columnName);

    /**
     * 获取数据库中列描述
     */
    String getColumnComment();

    /**
     * 设置数据库中列描述
     */
    void setColumnComment(String columnComment);

    /**
     * 获取转换后的类型
     */
    Type getType();

    /**
     * 设置转换后的类型
     */
    void setType(Type type);

    /**
     * 获取枚举
     */
    EnumInfo getEnumInfo();


    /**
     * 设置枚举
     */
    void setEnumInfo(EnumInfo enumInfo);

    /**
     * 获取内部类
     */
    InternalInfo getInternalInfo();

    /**
     * 设置内部类
     */
    void setInternalInfo(InternalInfo internalInfo);

    /**
     * 获取Java Bean中字段的类型
     */
    default String getFieldType() {
        if (getType().equals(Type.ENUM) || getType().equals(Type.OBJECT)) {
            return getColumnClass();
        }
        return getType().getJavaType();
    }

    /**
     * 将列名转换成一个类的名称
     * 要求：首字母大写，下划线转驼峰
     */
    default String getColumnClass() {
        return StringUtils.capitalize(underlineToCamel(getColumnName()));
    }

    /**
     * 将列名转换成一个对象的名称
     * 要求：首字母小写，下划线转驼峰
     */
    default String getColumnObject() {
        return StringUtils.uncapitalize(underlineToCamel(getColumnName()));
    }
}
