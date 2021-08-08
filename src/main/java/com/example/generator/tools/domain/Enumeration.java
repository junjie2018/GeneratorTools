package com.example.generator.tools.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enumeration {

    private String enumClass;

    private String enumObject;

    private String comment;

    private String itemType;

    private List<EnumerationItem> enumItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnumerationItem {
        private String itemType;

        private String itemName;

        private String itemValue;

        private String comment;
    }
}
