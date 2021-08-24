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
        TemplateUtils.renderTpl("bean-class-service", tables.get(0));
        TemplateUtils.renderTpl("bean-class-mapper", tables.get(0));
        TemplateUtils.renderTpl("bean-class", tables.get(0));
        TemplateUtils.renderTpl("bean-class-ids-request", tables.get(0));
        TemplateUtils.renderTpl("create-bean-class-request", tables.get(0));
        TemplateUtils.renderTpl("update-bean-class-request", tables.get(0));
        TemplateUtils.renderTpl("page-bean-class-request", tables.get(0));
        TemplateUtils.renderTpl("bean-class-data", tables.get(0));
    }

}
