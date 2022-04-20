## 使用该模板进行测试

### TableInfo

<#if tableClass??>
    ${tableClass}
    ${tableObject}
    ${tableName}
    ${tableComment}
    ${tableClassWithPrefix}
    ${tableObjectWithPrefix}

    <#if columnInfos??>
        <#list columnInfos as columnInfo>

         其他字段
            /**
            * ${columnInfo.columnComment}
            */
            private ${columnInfo.fieldType} ${columnInfo.columnObject};

        </#list>
    </#if>
</#if>

### EnumInfo

<#if enumClass??>
    ${enumClass}
</#if>

### InternalInfo

<#if internalClass??>
    ${internalClass}
</#if>

