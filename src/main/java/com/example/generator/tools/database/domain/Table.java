package com.example.generator.tools.database.domain;

import com.example.generator.tools.utils.TableUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    /**
     * 表名
     */
    private String name;
    /**
     * 表描述
     */
    private String comment;

    /**
     * 该表包含的列
     */
    private List<Column> columns;

}
