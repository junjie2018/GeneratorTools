package com.example.generator.tools.database.disposer;

import com.example.generator.tools.database.domain.Column;
import com.example.generator.tools.database.domain.Table;
import com.example.generator.tools.database.exception.DatabaseException;
import com.example.generator.tools.database.exception.NoTableException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.generator.tools.utils.AssertUtils.assertNotNull;

@Component
@RequiredArgsConstructor
public class PGDisposer implements Disposer {

    private final JdbcTemplate jdbcTemplate;

    public static final String PG_TYPES_TABLE = "TABLE";
    public static final String PG_FLAG_TABLE_NAME = "table_name";
    public static final String PG_FLAG_COLUMN_NAME = "column_name";
    public static final String PG_FLAG_TYPE_NAME = "type_name";
    public static final String PG_FLAG_REMARKS = "remarks";

    @Override
    public List<Table> getTables() {

        assertNotNull(jdbcTemplate, "jdbcTemplate不能为空");
        assertNotNull(jdbcTemplate.getDataSource(), "获取数据源失败");

        try {

            List<Table> tables = new ArrayList<>();

            DatabaseMetaData dbMetaData = jdbcTemplate
                    .getDataSource()
                    .getConnection()
                    .getMetaData();

            ResultSet tablesResultSet = dbMetaData.getTables(
                    null, null,
                    null, new String[]{PG_TYPES_TABLE});

            // 获取表信息
            while (tablesResultSet.next()) {
                Table table = new Table();

                table.setName(tablesResultSet.getString(PG_FLAG_TABLE_NAME));
                table.setComment(tablesResultSet.getString(PG_FLAG_REMARKS));
                table.setColumns(new ArrayList<>());

                // 获取表的列信息
                ResultSet columns = dbMetaData.getColumns(
                        null, null,
                        table.getName(), null);

                while (columns.next()) {
                    Column column = new Column();
                    column.setName(columns.getString(PG_FLAG_COLUMN_NAME));
                    column.setComment(columns.getString(PG_FLAG_REMARKS));
                    column.setType(columns.getString(PG_FLAG_TYPE_NAME));

                    table.getColumns().add(column);
                }

                tables.add(table);
            }

            if (tables.size() == 0) {
                throw new NoTableException("从数据库中获取的表数据长度为零，请检查数据库或配置");
            }

            return tables;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
