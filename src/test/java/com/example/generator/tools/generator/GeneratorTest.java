package com.example.generator.tools.generator;

import com.alibaba.fastjson.JSON;
import com.example.generator.tools.database.disposer.MariaDisposer;
import com.example.generator.tools.database.domain.Table;
import com.example.generator.tools.generator.disposer.MariaTypeDisposer;
import com.example.generator.tools.generator.disposer.TypeDisposer;
import com.example.generator.tools.generator.domain.*;
import com.example.generator.tools.generator.utils.TemplateUtils;
import com.example.generator.tools.properties.ProjectProperties;
import com.example.generator.tools.properties.TableProperties;
import com.example.generator.tools.properties.TemplateConfigsProperties;
import com.example.generator.tools.generator.properties.ToolsProperties;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@ActiveProfiles(profiles = {"wiki"})
class GeneratorTest {

    @Autowired
    private MariaDisposer pgDisposer;
    @Autowired
    private ToolsProperties toolsProperties;
    @Autowired
    private ProjectProperties projectProperties;
    @Autowired
    private TableProperties tableProperties;
    @Autowired
    private TemplateConfigsProperties templateConfigsProperties;


    @Test
    void doTransfer() throws TemplateException, IOException {
        Generator generator = new Generator();

        generator.init(
                DefaultTableInfo.class,
                DefaultColumnInfo.class,
                DefaultEnumInfo.class,
                DefaultInternalInfo.class,
                MariaTypeDisposer.class,

                (table, tableInfo) -> {
                    DefaultTableInfo defaultTableInfo = (DefaultTableInfo) tableInfo;
                    defaultTableInfo.setTablePrefix(projectProperties.getTablePrefix());
                    for (TableProperties.Table tableProperty : tableProperties.getTables()) {
                        if (tableProperty.getLogicName().equals(table.getName())) {
                            if (tableProperty.getEntityName() != null) {
                                defaultTableInfo.setTableCommentCustom(tableProperty.getEntityName());
                            }
                        }
                    }
                });

        List<Table> tables = pgDisposer.getTables();


        List<TableInfo> tableInfos = generator.doTransfer(tables);


        for (TableInfo tableInfo : tableInfos) {
//            TemplateConfigsProperties.TemplateConfig templateConfig = templateConfigsProperties
//                    .getTemplateConfigs()
//                    .get("bean-class-service");

//            Map<String, Object> stringObjectMap = generator.buildMap(tableInfo);
//            String outputFileName = TemplateUtils.getOutputFilename("${tableClass}Controller.java", stringObjectMap);
            System.out.println("");
//            generator.renderTpl("tips", tableInfo);
            generator.renderTpl("service", tableInfo);
        }


        System.out.println(JSON.toJSONString(tableInfos));


    }

    @Test
    public void test() {
        Matcher matcher = TypeDisposer.ENUM_COMMENT_PATTERN.matcher("是否删除（0：是，1：否）");
        Matcher matcher1 = toolsProperties.getEnumCommentPattern().matcher("是否删除（0：是，1：否）");
        String pattern = "^([A-Za-z\\u4e00-\\u9fa5 ]{1,})（(([A-Za-z0-9-]+：[\\u4e00-\\u9fa5A-Za-z0-9-]{1,}，?)+)）$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher2 = r.matcher("是否删除（0：是，1：否）");
        System.out.println(matcher1.group(0));
        System.out.println("");
    }
}