<#--controller方法-->
<@fragment name="ControllerMethod">

    /**
     * 更新${entityName}
     */
    @PostMapping("/update${beanClass}")
    @Transactional
    public ResponseVo update${beanClass}(
            @RequestHeader(APICons.HEADER_TENANT_ID) String tenantId,
            @RequestBody @Valid Update${beanClass}Request request) {

        ${beanObject}Service.update${beanClass}(tenantId, request);

        return ResponseVo.createSuccess();
    }

</@fragment>

<#--service方法-->
<@fragment name="ServiceMethod">

    /**
     * 更新${entityName}
     */
    public void update${beanClass}(String tenantId, Update${beanClass}Request request) {
        judge${beanClass}ExistByIdAndTenantId(request.getId(), tenantId);

        ${beanClass} ${beanObject}Update = BeanUtil.copyProperties(request, ${beanClass}.class);

        ${beanObject}Update.setId(request.getId());

        ${beanObject}Mapper.updateById(${beanObject}Update);
    }

</@fragment>

<@fragment name="ServiceMethod2">

    /**
     * 更新${entityName}
     */
    public void update${beanClass}(String tenantId, Update${beanClass}Request request) {
        judge${beanClass}ExistById(request.getId());

        ${beanClass} ${beanObject}Update = BeanUtil.copyProperties(request, ${beanClass}.class);

        ${beanObject}Update.setId(request.getId());

        ${beanObject}Mapper.updateById(${beanObject}Update);
    }

</@fragment>