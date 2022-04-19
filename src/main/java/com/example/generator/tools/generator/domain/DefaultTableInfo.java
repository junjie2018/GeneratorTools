package com.example.generator.tools.generator.domain;

import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.generator.tools.utils.JStringUtils.*;

@Data
public class DefaultTableInfo implements TableInfo {


    private String tableName;
    private String tableComment;
    @Setter
    private List<ColumnInfo> columnInfos;

    private String tablePrefix;
    private String tableCommentCustom;

    public void init(String tablePrefix, String tableCommentCustom) {
        this.tablePrefix = tablePrefix;
        this.tableCommentCustom = tableCommentCustom;
    }

    @Override
    public String getTableClass() {
        return StringUtils.capitalize(underlineToCamel(removePrefix(tableName, tablePrefix)));
    }

    @Override
    public String getTableObject() {
        return StringUtils.uncapitalize(underlineToCamel(removePrefix(tableName, tablePrefix)));
    }

    @Override
    public String getTableClassWithPrefix() {
        return StringUtils.capitalize(underlineToCamel(tableName));
    }

    @Override
    public String getTableObjectWithPrefix() {
        return StringUtils.uncapitalize(underlineToCamel(tableName));
    }

    @Override
    public List<EnumInfo> getEnumInfos() {
        return columnInfos.stream()
                .filter(item -> item.getType().equals(Type.ENUM))
                .map(ColumnInfo::getEnumInfo).collect(Collectors.toList());
    }

    @Override
    public List<InternalInfo> getInternalInfos() {
        return columnInfos.stream()
                .filter(item -> item.getType().equals(Type.OBJECT))
                .map(ColumnInfo::getInternalInfo).collect(Collectors.toList());
    }
}
