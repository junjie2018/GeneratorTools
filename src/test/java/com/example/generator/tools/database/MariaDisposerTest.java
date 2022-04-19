package com.example.generator.tools.database;

import com.example.generator.tools.database.disposer.MariaDisposer;
import com.example.generator.tools.database.domain.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles(profiles = {"wiki"})
class MariaDisposerTest {

    @Autowired
    private MariaDisposer mariaDisposer;

    @Test
    void getTables() {
        List<Table> tables = mariaDisposer.getTables();
        System.out.println("");
    }
}