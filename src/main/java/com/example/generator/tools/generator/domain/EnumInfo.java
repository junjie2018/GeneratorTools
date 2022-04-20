package com.example.generator.tools.generator.domain;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface EnumInfo {

    /**
     * 获取列
     */
    @JSONField(serialize = false)
    ColumnInfo getColumnInfo();

    /**
     * 设置列
     */
    void setColumnInfo(ColumnInfo columnInfo);

    /**
     * 获取枚举项的类型
     */
    Type getType();

    /**
     * 设置枚举类型
     */
    void setType(Type type);

    /**
     * 获取枚举项
     */
    List<EnumItem> getEnumItems();

    /**
     * 设置枚举项
     */
    void setEnumItems(List<EnumItem> enumItems);

    /**
     * 由列名而来
     */
    default String getEnumName() {
        return getColumnInfo().getColumnName();
    }

    /**
     * 由列描述而来
     */
    default String getEnumComment() {
        return getColumnInfo().getColumnComment();
    }

    /**
     * 由列名转换而来，用作类名称
     * 要求：首字母大写，下划线转驼峰
     */
    default String getEnumClass() {
        return getColumnInfo().getColumnClass() + "Enum";
    }

    /**
     * 由列名转换而来，用作对象名称
     * 要求：首字母大写，下划线转驼峰
     */
    default String getEnumObject() {
        return getColumnInfo().getColumnObject() + "Enum";
    }

    /**
     * 获取枚举项对应的Java类型
     */
    default String getEnumItemType() {
        return getType().getJavaType();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class EnumItem {
        private Type type;

        private String itemType;

        private String itemName;

        private String itemValue;

        private String itemComment;
    }
}
