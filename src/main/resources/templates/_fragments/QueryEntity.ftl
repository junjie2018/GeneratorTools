<#--controller方法-->
<@fragment name="ControllerMethod">

    /**
     * 根据id数组，批量查询${entityName}
     */
    @PostMapping("/query${beanClass}")
    public ResponseVo<List<${beanClass}Data>> query${beanClass}(
            @RequestHeader(APICons.HEADER_TENANT_ID) String tenantId,
            @RequestBody @Valid ${beanClass}IdsRequest request) {
        return ResponseVo.createSuccessByData(${beanObject}Service.query${beanClass}(tenantId, request));
    }

</@fragment>

<#--service方法-->
<@fragment name="ServiceMethod">

    /**
     * 根据id数组，批量查询${entityName}
     */
    public List<${beanClass}Data> query${beanClass}(String tenantId, ${beanClass}IdsRequest request) {
        List<${beanClass}> ${beanObject}List = judge${beanClass}ExistByIdsAndTenantId(request.getIds(), tenantId);

        List<${beanClass}Data> ${beanObject}DataList = new ArrayList<>();

        for (${beanClass} ${beanObject} : ${beanObject}List) {
            ${beanObject}DataList.add(BeanUtil.copyProperties(${beanObject}, ${beanClass}Data.class));
        }

        return ${beanObject}DataList;
    }

</@fragment>