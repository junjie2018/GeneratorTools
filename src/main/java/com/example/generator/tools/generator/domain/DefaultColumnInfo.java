package com.example.generator.tools.generator.domain;

import lombok.Data;

@Data
public class DefaultColumnInfo implements ColumnInfo {

    private String columnName;
    private String columnComment;
    private Type type;
    private EnumInfo enumInfo;
    private InternalInfo internalInfo;

}
