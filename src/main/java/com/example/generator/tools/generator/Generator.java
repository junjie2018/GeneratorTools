package com.example.generator.tools.generator;

import com.example.generator.tools.database.domain.Column;
import com.example.generator.tools.database.domain.Table;
import com.example.generator.tools.generator.disposer.TypeDisposer;
import com.example.generator.tools.generator.domain.*;
import com.example.generator.tools.generator.utils.TemplateUtils;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Generator {

    private Class<? extends TableInfo> tableInfoClass;
    private Class<? extends ColumnInfo> columnInfoClass;
    private Class<? extends EnumInfo> enumInfoClass;
    private Class<? extends InternalInfo> internalInfoClass;
    private Class<? extends TypeDisposer> typeDisposerClass;

    private BiConsumer<Table, ? super TableInfo> initTableInfo;
    private Consumer<? super ColumnInfo> initColumnInfo;
    private BiConsumer<? super ColumnInfo, ? super EnumInfo> initEnumInfo;
    private BiConsumer<? super ColumnInfo, ? super InternalInfo> initInternalInfo;


    public void init(
            Class<? extends TableInfo> tableInfoClass,
            Class<? extends ColumnInfo> columnInfosClass,
            Class<? extends EnumInfo> enumInfoClass,
            Class<? extends InternalInfo> internalInfoClass,
            Class<? extends TypeDisposer> typeDisposerClass) {
        init(tableInfoClass, columnInfosClass, enumInfoClass, internalInfoClass, typeDisposerClass,
                null, null, null, null);
    }

    public void init(
            Class<? extends TableInfo> tableInfoClass,
            Class<? extends ColumnInfo> columnInfosClass,
            Class<? extends EnumInfo> enumInfoClass,
            Class<? extends InternalInfo> internalInfoClass,
            Class<? extends TypeDisposer> typeDisposerClass,
            BiConsumer<Table, ? super TableInfo> initTableInfo) {
        init(tableInfoClass, columnInfosClass, enumInfoClass, internalInfoClass, typeDisposerClass,
                initTableInfo, null, null, null);
    }

    public void init(
            Class<? extends TableInfo> tableInfoClass,
            Class<? extends ColumnInfo> columnInfosClass,
            Class<? extends EnumInfo> enumInfoClass,
            Class<? extends InternalInfo> internalInfoClass,
            Class<? extends TypeDisposer> typeDisposerClass,
            BiConsumer<Table, ? super TableInfo> initTableInfo,
            Consumer<? super ColumnInfo> initColumnInfo) {
        init(tableInfoClass, columnInfosClass, enumInfoClass, internalInfoClass, typeDisposerClass,
                initTableInfo, initColumnInfo, null, null);
    }

    private void init(
            Class<? extends TableInfo> tableInfoClass,
            Class<? extends ColumnInfo> columnInfosClass,
            Class<? extends EnumInfo> enumInfoClass,
            Class<? extends InternalInfo> internalInfoClass,
            Class<? extends TypeDisposer> typeDisposerClass,
            BiConsumer<Table, ? super TableInfo> initTableInfo,
            Consumer<? super ColumnInfo> initColumnInfo,
            BiConsumer<? super ColumnInfo, ? super EnumInfo> initEnumInfo,
            BiConsumer<? super ColumnInfo, ? super InternalInfo> initInternalInfo) {
        this.tableInfoClass = tableInfoClass;
        this.columnInfoClass = columnInfosClass;
        this.enumInfoClass = enumInfoClass;
        this.internalInfoClass = internalInfoClass;
        this.typeDisposerClass = typeDisposerClass;

        this.initTableInfo = initTableInfo;
        this.initColumnInfo = initColumnInfo;
        this.initEnumInfo = initEnumInfo;
        this.initInternalInfo = initInternalInfo;
    }

    public List<TableInfo> doTransfer(List<Table> tables) {

        List<TableInfo> result = new ArrayList<>();

        TypeDisposer typeDisposer = getInstance(typeDisposerClass);

        for (Table table : tables) {

            TableInfo tableInfo = getInstance(tableInfoClass);

            if (initTableInfo != null) {
                initTableInfo.accept(table, tableInfo);
            }

            tableInfo.setTableName(table.getName());
            tableInfo.setTableComment(table.getComment());

            List<ColumnInfo> columnInfos = new ArrayList<>();

            for (Column column : table.getColumns()) {
                ColumnInfo columnInfo = getInstance(columnInfoClass);

                columnInfo.setColumnName(column.getName());
                columnInfo.setColumnComment(column.getComment());

                if (initColumnInfo != null) {
                    initColumnInfo.accept(columnInfo);
                }

                // 判断是否为枚举
                if (typeDisposer.isEnum(column)) {
                    EnumInfo enumInfo = getInstance(enumInfoClass);

                    if (initEnumInfo != null) {
                        initEnumInfo.accept(columnInfo, enumInfo);
                    }

                    typeDisposer.disposeEnum(enumInfo, columnInfo);

                    columnInfo.setType(Type.ENUM);
                    columnInfo.setEnumInfo(enumInfo);
                }
                // 判断是否为内部类
                else if (typeDisposer.isInternal(column)) {
                    InternalInfo internalInfo = getInstance(internalInfoClass);

                    if (initInternalInfo != null) {
                        initInternalInfo.accept(columnInfo, internalInfo);
                    }

                    typeDisposer.disposeInternal(internalInfo, columnInfo);

                    columnInfo.setType(Type.OBJECT);
                    columnInfo.setInternalInfo(internalInfo);
                }
                // 其他处理
                else {
                    columnInfo.setType(typeDisposer.getType(column));
                }

                columnInfos.add(columnInfo);
            }

            tableInfo.setTableName(table.getName());
            tableInfo.setTableComment(table.getComment());
            tableInfo.setColumnInfos(columnInfos);

            result.add(tableInfo);
        }

        return result;
    }


    public void renderTpl(String templateKey, TableInfo tableInfo) {
        TemplateUtils.renderTpl(templateKey, buildMap(tableInfo));
    }

    public void renderTpl(String templateKey, EnumInfo enumInfo) {
        TemplateUtils.renderTpl(templateKey, buildMap(enumInfo));
    }

    public void renderTpl(String templateKey, InternalInfo internalInfo) {
        TemplateUtils.renderTpl(templateKey, buildMap(internalInfo));
    }

    private Map<String, Object> buildMap(TableInfo tableInfo) {
        Map<String, Object> result = new HashMap<>();

        result.put("tableName", tableInfo.getTableName());
        result.put("tableComment", tableInfo.getTableComment());
        result.put("tableClass", tableInfo.getTableClass());
        result.put("tableObject", tableInfo.getTableObject());
        result.put("tableClassWithPrefix", tableInfo.getTableClassWithPrefix());
        result.put("tableObjectWithPrefix", tableInfo.getTableObjectWithPrefix());
        result.put("columnInfos", tableInfo.getColumnInfos());
        result.put("enumInfos", tableInfo.getEnumInfos());
        result.put("internalInfos", tableInfo.getInternalInfos());

        return result;
    }

    private Map<String, Object> buildMap(EnumInfo enumInfo) {
        Map<String, Object> result = new HashMap<>();

        result.put("enumName", enumInfo.getEnumName());
        result.put("enumComment", enumInfo.getEnumComment());
        result.put("enumClass", enumInfo.getEnumClass());
        result.put("enumObject", enumInfo.getEnumObject());
        result.put("enumItemName", enumInfo.getEnumItemType());
        result.put("enumItems", enumInfo.getEnumItems());

        return result;
    }

    private Map<String, Object> buildMap(InternalInfo internalInfo) {
        Map<String, Object> result = new HashMap<>();

        result.put("internalName", internalInfo.getInternalName());
        result.put("internalComment", internalInfo.getInternalComment());
        result.put("internalClass", internalInfo.getInternalClass());
        result.put("internalObject", internalInfo.getInternalObject());

        return result;
    }

    private <T> T getInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}

