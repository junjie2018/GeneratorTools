<#-- 导入包 -->
<@fragment name="importPackets">

    <#if packetsToImport??>
        <#list packetsToImport as packetToImport>
            import ${packetToImport};
        </#list>
    </#if>

</@fragment>