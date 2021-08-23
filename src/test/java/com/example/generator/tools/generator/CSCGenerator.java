package com.example.generator.tools.generator;

import com.example.generator.tools.domain.Table;
import com.example.generator.tools.utils.TableUtils;
import com.example.generator.tools.utils.TemplateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CSCGenerator {

    public static List<Table> tables;

    @BeforeAll
    public static void setUp() {
        tables = TableUtils.generateTablesFormDB();
    }

    public void generateEntity() {

    }

}
