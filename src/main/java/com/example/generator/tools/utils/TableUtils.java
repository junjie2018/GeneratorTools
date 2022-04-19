package com.example.generator.tools.utils;

import com.example.generator.tools.domain.*;
import com.example.generator.tools.domain.Enumeration.EnumerationItem;
import com.example.generator.tools.properties.ProjectProperties;
import com.example.generator.tools.properties.TableProperties;
import com.example.generator.tools.properties.ToolsProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static com.example.generator.tools.database.disposer.PGDisposer.*;
import static com.example.generator.tools.utils.AssertUtils.*;
import static com.example.generator.tools.constant.SystemConstant.*;
import static com.example.generator.tools.utils.JStringUtils.*;
import static com.example.generator.tools.utils.JStringUtils.underlineToCamelWithCapitalized;

@Slf4j
@Component
public class TableUtils implements ApplicationContextAware {

    private static JdbcTemplate jdbcTemplate;
    private static TableProperties tableProperties;
    private static ToolsProperties toolsProperties;
    private static ProjectProperties projectProperties;

    public static List<Table> generateTablesFormDB() {


        List<Table> tables = new ArrayList<>();

        try {

            List<TableInDb> tableInDbs = getTableInDbs();

            for (TableInDb tableInDb : getTableInDbs()) {

                List<Column> columns = new ArrayList<>();
                List<Enumeration> enumerations = new ArrayList<>();
                List<InternalBean> internalBeans = new ArrayList<>();

                for (ColumnInTable columnInTable : tableInDb.getColumns()) {

                    Enumeration enumeration = null;
                    InternalBean internalBean = null;
                    String fieldType = null;

                    if (StringUtils.isNoneBlank(columnInTable.getComment())
                            && toolsProperties.getEnumCommentPattern().matcher(columnInTable.getComment()).matches()) {

                        enumeration = disposeEnumeration(columnInTable.getName(), columnInTable.getComment());
                        fieldType = enumeration.getEnumClass();
                        enumerations.add(enumeration);

                    } else if (DataType.convert(columnInTable.getType()).equals(DataType.JSONB)) {

                        internalBean = InternalBean.builder()
                                .beanClass(underlineToCamelWithCapitalized(columnInTable.getName()))
                                .beanName(underlineToCamelWithUncapitalized(columnInTable.getName()))
                                .comment(columnInTable.getComment())
                                .fieldItems(new ArrayList<>())
                                .build();
                        // 生成代码时fieldType暂时还是用JSONObject
                        // fieldType=internalBean.getBeanClass();
                        fieldType = JAVA_TYPE_JSON;
                        internalBeans.add(internalBean);

                    } else {

                    }

                    columns.add(Column.builder()
                            .logicName(columnInTable.getName())
                            .dataType(DataType.convert(columnInTable.getType()))
                            .comment(columnInTable.getComment())
                            .beanClass(underlineToCamelWithCapitalized(columnInTable.getName()))
                            .beanObject(underlineToCamelWithUncapitalized(columnInTable.getName()))
                            .fieldType(fieldType)
                            .enumeration(enumeration)
                            .internalBean(internalBean)
                            .build());
                }

                tables.add(Table.builder()
                        .logicName(tableInDb.getName())
                        .comment(tableInDb.getComment())
                        .entityName(getEntityName(tableInDb.getName(), tableInDb.getComment()))
                        .beanClass(underlineToCamelWithCapitalized(removeTableNamePrefix(tableInDb.getName())))
                        .beanObject(underlineToCamelWithUncapitalized(removeTableNamePrefix(tableInDb.getName())))
                        .columns(columns)
                        .enumerations(enumerations)
                        .internalBeans(internalBeans)
                        .build());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tables;
    }

    private static Enumeration disposeEnumeration(String columnName, String columnComment) {

        Matcher enumColumnMatcher = toolsProperties
                .getEnumCommentPattern()
                .matcher(columnComment);

        if (!enumColumnMatcher.matches()) {
            throw new RuntimeException("Wrong When Parse ColumnComment");
        }

        // 判断枚举是否是数字类型
        boolean numberEnumFlag = true;
        for (String enumItem : enumColumnMatcher.group(2).split("，")) {
            String[] enumItems = enumItem.split("：");
            if (!toolsProperties.getNumberPattern().matcher(enumItems[0]).matches()) {
                numberEnumFlag = false;
            }
        }

        List<EnumerationItem> enumerationItems = new ArrayList<>();

        // 填充枚举项信息
        for (String enumItem : enumColumnMatcher.group(2).split("，")) {

            String[] enumItems = enumItem.split("：");

            EnumerationItem enumItemInfo = EnumerationItem.builder()
                    .itemType(numberEnumFlag ? JAVA_TYPE_INTEGER : JAVA_TYPE_STRING)
                    .itemName(numberEnumFlag ? "TMP_" + enumItems[0] : StringUtils.upperCase(enumItems[0]))
                    .comment(enumItems[1])
                    .itemValue(enumItems[0])
                    .build();

            enumerationItems.add(enumItemInfo);

        }

        return Enumeration.builder()
                .enumClass(underlineToCamelWithCapitalized(columnName))
                .enumObject(underlineToCamelWithUncapitalized(columnName))
                .comment(enumColumnMatcher.group(1))
                .itemType(numberEnumFlag ? JAVA_TYPE_INTEGER : JAVA_TYPE_STRING)
                .enumItems(enumerationItems)
                .build();

    }

    private static String getEntityName(String logicName, String comment) {
        Optional<TableProperties.Table> tableOptional = tableProperties.getTables().stream()
                .filter(item -> logicName.equals(item.getLogicName()))
                .findFirst();

        if (tableOptional.isPresent()) {
            TableProperties.Table table = tableOptional.get();
            return StringUtils.isBlank(table.getEntityName()) ?
                    comment :
                    table.getEntityName();

        } else {
            return comment;
        }
    }

    private static List<TableInDb> getTableInDbs() throws Exception {

        assertNotNull(jdbcTemplate, "jdbcTemplate不能为空");
        assertNotNull(jdbcTemplate.getDataSource(), "获取数据源失败");

        List<TableInDb> tableInDbs = new ArrayList<>();

        DatabaseMetaData dbMetaData = jdbcTemplate
                .getDataSource()
                .getConnection()
                .getMetaData();

        // 原PG写法，因为在Maria中不支持这种写法，故修改为如下写法
//         ResultSet tables = dbMetaData.getTables(
//                null, null,
//                null, new String[]{PG_TYPES_TABLE});

        ResultSet tables = dbMetaData.getTables(null, null, projectProperties.getTablePrefix() + "%", null);

        // 获取表信息
        while (tables.next()) {
            TableInDb tableInDb = TableInDb.builder()
                    .name(tables.getString(PG_FLAG_TABLE_NAME))
                    .comment(tables.getString(PG_FLAG_REMARKS))
                    .columns(new ArrayList<>())
                    .build();

            // 获取表的列信息
            ResultSet columns = dbMetaData.getColumns(
                    null, null,
                    tableInDb.getName(), null);

            while (columns.next()) {
                tableInDb.add(columns.getString(PG_FLAG_COLUMN_NAME),
                        columns.getString(PG_FLAG_TYPE_NAME),
                        columns.getString(PG_FLAG_REMARKS));
            }

            tableInDbs.add(tableInDb);
        }

        if (tableInDbs.size() == 0) {
            throw new RuntimeException("未获取到表信息，请检查数据或或表配置");
        }

        return tableInDbs;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TableInDb {
        private String name;
        private String comment;
        private List<ColumnInTable> columns;

        public void add(String name, String comment, String type) {
            columns.add(ColumnInTable.builder()
                    .name(name)
                    .type(comment)
                    .comment(type)
                    .build());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ColumnInTable {
        private String name;
        private String comment;
        private String type;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        tableProperties = applicationContext.getBean(TableProperties.class);
        toolsProperties = applicationContext.getBean(ToolsProperties.class);
        projectProperties = applicationContext.getBean(ProjectProperties.class);
    }
}
