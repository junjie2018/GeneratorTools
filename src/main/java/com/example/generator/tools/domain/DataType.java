package com.example.generator.tools.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DataType {
    /**
     * varchar
     */
    VARCHAR(Collections.singletonList("varchar")),

    /**
     * int
     */
    INT(Arrays.asList("int2", "int4", "int8", "int")),

    /**
     * numeric
     */
    NUMERIC(Arrays.asList("numeric", "longtext", "bigint")),

    /**
     * jsonb
     */
    JSONB(Collections.singletonList("jsonb")),

    /**
     * date
     */
    DATE(Arrays.asList("date", "timestamp", "timestamptz", "datetime")),
    ;

    @Getter
    private final List<String> typeNameFromJdbcTemplate;


    public static DataType convert(String typeName) {

        for (DataType columnType : DataType.values()) {
            if (columnType.getTypeNameFromJdbcTemplate().contains(typeName.toLowerCase())) {
                return columnType;
            }
        }

        throw new RuntimeException("转换成DataType失败，未定义该类型：" + typeName);
    }
}
