<#--controller方法-->
<@fragment name="ControllerMethod">
    /**
     * 创建${entityName}
     *
     * @return 创建的${entityName}的Id
     */
    @PostMapping("/create${beanClass}")
    @Transactional
    public ResponseVo<String> create${beanClass}(
            @RequestHeader(APICons.HEADER_TENANT_ID) String tenantId,
            @RequestAttribute(APICons.REQUEST_USER_ID) String userId,
            @RequestBody @Valid Create${beanClass}Request request) {

        return ResponseVo.createSuccessByData(${beanObject}Service.create${beanClass}(userId, tenantId, request));

    }

</@fragment>

<#--service方法-->
<@fragment name="ServiceMethod">

    /**
     * 创建${entityName}
     */
    public String create${beanClass}(String userId, String tenantId, Create${beanClass}Request request) {


        ${beanClass} ${beanObject}Insert = ${beanClass}.builder()
                <#list columns as column>

                <#if column.logicName == "org_id">
                .${column.beanObject}(tenantId)<#continue>
                </#if>

                <#if column.logicName == "id"
                        || column.logicName == "creator"
                        || column.logicName == "modifier"
                        || column.logicName == "is_delete"
                        || column.logicName == "gmt_create_time"
                        || column.logicName == "gmt_modify_time">
                    <#continue>
                </#if>

                .${column.beanObject}(request.get${column.beanClass}())

                </#list>
                .build();


        ${beanObject}Mapper.insert(${beanObject}Insert);

        return ${beanObject}Insert.getId();
    }

</@fragment>