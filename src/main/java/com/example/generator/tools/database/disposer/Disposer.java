package com.example.generator.tools.database.disposer;

import com.example.generator.tools.database.domain.Table;

import java.util.List;

public interface Disposer {
    List<Table> getTables();
}
