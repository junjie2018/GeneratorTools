package com.example.generator.tools.database;

import com.example.generator.tools.database.disposer.PGDisposer;
import com.example.generator.tools.database.domain.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles(profiles = {"pdm"})
class PGDisposerTest {

    @Autowired
    private PGDisposer pgDisposer;

    @Test
    void getTables() {
        List<Table> tables = pgDisposer.getTables();
        System.out.println("");
    }
}