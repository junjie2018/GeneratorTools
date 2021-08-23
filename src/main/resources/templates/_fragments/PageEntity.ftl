<#--service方法-->
<@fragment name="ServiceMethod">

    /**
     * 分页查询${entityName}
     */
    public Page<${beanClass}Data> page${beanClass}(String tenantId, Page${beanClass}Request request) {
        LambdaQueryWrapper<${beanClass}> queryWrapper = new LambdaQueryWrapper<${beanClass}>()
                .eq(${beanClass}::getOrgId, tenantId)
                .orderByDesc(${beanClass}::getGmtCreateTime);

        Page<${beanClass}> entityPage = new Page<>(
                request.getCurrent(),
                request.getLimit());

        ${beanObject}Mapper.selectPage(entityPage, queryWrapper);

        return PageUtils.entityPageToResponseDataPage(entityPage, ${beanClass}Data.class);

    }

</@fragment>

<#--controller方法-->
<@fragment name="ControllerMethod">

    /**
     * 分页查询${entityName}
     */
    @PostMapping("/page${beanClass}")
    public ResponseVo<Page<${beanClass}Data>> page${beanClass}(
            @RequestHeader(APICons.HEADER_TENANT_ID) String tenantId,
            @RequestBody @Valid Page${beanClass}Request request) {

        return ResponseVo.createSuccessByData(${beanObject}Service.page${beanClass}(tenantId, request));

    }

</@fragment>
