package com.example.generator.tools.generator;

import com.example.generator.tools.domain.Enumeration;
import com.example.generator.tools.domain.Table;
import com.example.generator.tools.utils.TableUtils;
import com.example.generator.tools.utils.TemplateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles(profiles = {"srm"})
public class SRMGenerator {

    public List<Table> tables;

    @BeforeEach
    public void setUp() {
        tables = TableUtils.generateTablesFormDB();
    }

    @Test
    public void test() {

        for (Table table : tables) {
            TemplateUtils.renderTpl("bean-class-service", table);
            TemplateUtils.renderTpl("bean-class-mapper", table);
            TemplateUtils.renderTpl("bean-class", table);
            TemplateUtils.renderTpl("bean-class-ids-request", table);
            TemplateUtils.renderTpl("create-bean-class-request", table);
            TemplateUtils.renderTpl("update-bean-class-request", table);
            TemplateUtils.renderTpl("page-bean-class-request", table);
            TemplateUtils.renderTpl("bean-class-data", table);

            for (Enumeration enumeration : table.getEnumerations()) {
                TemplateUtils.renderTpl("enum", enumeration);
            }
        }
    }

}
