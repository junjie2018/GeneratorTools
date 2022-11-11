package com.example.generator.tools.generator.domain;

import com.example.generator.tools.generator.exception.ObjectNotSupportException;

public enum Type {
    STRING,
    INTEGER,
    LONG,
    OBJECT,
    ENUM,
    DECIMAL,
    LOCAL_DATE_TIME;

    public String getJavaType() {
        switch (this) {
            case STRING:
                return "String";
            case INTEGER:
                return "Integer";
            case LONG:
                return "Long";
            case DECIMAL:
                return "BigDecimal";
            case LOCAL_DATE_TIME:
                return "LocalDateTime";
            default:
                throw new ObjectNotSupportException("%s类型暂不支持，请自行处理");
        }
    }
}
