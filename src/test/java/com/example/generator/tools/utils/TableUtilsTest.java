package com.example.generator.tools.utils;

import com.example.generator.tools.domain.Table;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TableUtilsTest {

    @Test
    public void test(){
        List<Table> tables = TableUtils.generateTablesFormDB();
        System.out.println("");
    }
}
