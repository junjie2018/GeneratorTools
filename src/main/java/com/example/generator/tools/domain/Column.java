package com.example.generator.tools.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column {

    private String logicName;

    private DataType dataType;

    private String comment;

    private String beanClass;

    private String beanObject;

    private String fieldType;

    private Enumeration enumeration;

    private InternalBean internalBean;

}
