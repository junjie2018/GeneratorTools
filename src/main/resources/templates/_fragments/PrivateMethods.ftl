<@fragment name="judgeEntityExistByIdAndTenantId">

    private ${beanClass} judge${beanClass}ExistByIdAndTenantId(String id, String tenantId) {
        LambdaQueryWrapper<${beanClass}> queryWrapper = new LambdaQueryWrapper<${beanClass}>()
                .eq(${beanClass}::getId, id)
                .eq(${beanClass}::getOrgId, tenantId)
                .select(${beanClass}::getId);

        ${beanClass} ${beanObject} = ${beanObject}Mapper.selectOne(queryWrapper);

        if (${beanObject} == null) {
            throw new BusinessException();
        }

        return ${beanObject};
    }

</@fragment>

<@fragment name="judgeEntityExistByIdsAndTenantId">

    private List<${beanClass}> judge${beanClass}ExistByIdsAndTenantId(List<String> ids, String tenantId) {
        LambdaQueryWrapper<${beanClass}> queryWrapper = new LambdaQueryWrapper<${beanClass}>()
                .eq(${beanClass}::getOrgId, tenantId)
                .in(${beanClass}::getId, ids);

        List<${beanClass}> ${beanObject}List = ${beanObject}Mapper.selectList(queryWrapper);

        if (${beanObject}List.size() != ids.size()) {
            throw new BusinessException();
        }

        return ${beanObject}List;
    }

</@fragment>

<@fragment name="judgeEntityExistByIds">

    private List<${beanClass}> judge${beanClass}ExistByIds(List<String> ids) {
        LambdaQueryWrapper<${beanClass}> queryWrapper = new LambdaQueryWrapper<${beanClass}>()
                .in(${beanClass}::getId, ids);

        List<${beanClass}> ${beanObject}List = ${beanObject}Mapper.selectList(queryWrapper);

        if (${beanObject}List.size() != ids.size()) {
            throw new BusinessException();
        }

        return ${beanObject}List;
    }

</@fragment>

<@fragment name="judgeEntityExistById">

    public ${beanClass} judge${beanClass}ExistById(String ${beanObject}Id) {
        LambdaQueryWrapper<${beanClass}> queryWrapper = new LambdaQueryWrapper<${beanClass}>()
                .eq(${beanClass}::getId, ${beanObject}Id)
                .select(${beanClass}::getId);

        ${beanClass} ${beanObject} = ${beanObject}Mapper.selectOne(queryWrapper);

        if (${beanObject} == null) {
            throw new BusinessException();
        }

        return ${beanObject};
    }

</@fragment>