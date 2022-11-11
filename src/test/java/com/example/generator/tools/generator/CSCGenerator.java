package com.example.generator.tools.generator;

import com.example.generator.tools.domain.Table;
import com.example.generator.tools.utils.TableUtils;
import com.example.generator.tools.utils.TemplateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * 不要参考该代码
 */
@Deprecated
@SpringBootTest
@ActiveProfiles(profiles = {"csc"})
public class CSCGenerator {

    public List<Table> tables;

    @BeforeEach
    public void setUp() {
        tables = TableUtils.generateTablesFormDB();
    }

    @Test
    public void generateEntity() {
        TemplateUtils.renderTpl("service", tables.get(0));
    }

}
