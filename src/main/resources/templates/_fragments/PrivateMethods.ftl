<@fragment name="judgeEntityExistByIdAndTenantId">

    private ${beanClass} judge${beanClass}ExistByIdAndTenantId(String id, String tenantId) {
        LambdaQueryWrapper<${beanClass}> queryWrapper = new LambdaQueryWrapper<${beanClass}>()
                .eq(${beanClass}::getId, id)
                .eq(${beanClass}::getOrgId, tenantId)
                .select(${beanClass}::getId);

        ${beanClass} ${beanObject} = ${beanObject}Mapper.selectOne(queryWrapper);

        if (${beanObject} == null) {
            throw new RuntimeException("Wrong");
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
            throw new RuntimeException("Wrong");
        }

        return ${beanObject}List;
    }

</@fragment>