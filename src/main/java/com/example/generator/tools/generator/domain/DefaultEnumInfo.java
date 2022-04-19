package com.example.generator.tools.generator.domain;

import lombok.Data;

import java.util.List;


@Data
public class DefaultEnumInfo implements EnumInfo {

    private List<EnumItem> enumItems;
    private ColumnInfo columnInfo;
    private Type type;

}
