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
public class InternalBean {

    private String beanClass;

    private String beanName;

    private String comment;

    private List<FieldItem> fieldItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldItem {

        private String itemName;

        private String itemType;

        private String comment;
    }

}
