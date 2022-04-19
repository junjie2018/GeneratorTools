package com.example.generator.tools.generator.domain;

import com.alibaba.fastjson.annotation.JSONField;

public interface InternalInfo {

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
     * 数据库中列名称
     */
    default String getInternalName() {
        return getColumnInfo().getColumnName();
    }

    /**
     * 由列描述而来
     */
    default String getInternalComment() {
        return getColumnInfo().getColumnComment();
    }

    /**
     * 由列名转换而来，用作类名称
     * 要求：首字母大写，下划线转驼峰
     */
    default String getInternalClass() {
        return getColumnInfo().getColumnClass();
    }

    /**
     * 由列名转换而来，用作对象名称
     * 要求：首字母小写，下划线转驼峰
     */
    default String getInternalObject() {
        return getColumnInfo().getColumnObject();
    }
}
