<#--controller方法-->
<@fragment name="ControllerMethod">

    /**
     * 根据id数组，批量删除${entityName}
     */
    @Transactional
    @PostMapping("/delete${beanClass}")
    public ResponseVo<Void> delete${beanClass}(
            @RequestHeader(APICons.HEADER_TENANT_ID) String tenantId,
            @RequestBody @Valid ${beanClass}IdsRequest request) {

        ${beanObject}Service.delete${beanClass}(tenantId, request);

        return ResponseVo.createSuccess();
    }

</@fragment>

<#--service方法-->
<@fragment name="ServiceMethod">

    /**
     * 根据id数组，批量删除${entityName}
     */
    public void delete${beanClass}(String tenantId, ${beanClass}IdsRequest request) {
        judge${beanClass}ExistByIdsAndTenantId(request.getIds(), tenantId);

        ${beanObject}Mapper.deleteBatchIds(request.getIds());
    }

</@fragment>