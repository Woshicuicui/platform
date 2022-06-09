package com.bt.course.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.course.api.entity.ApiDefinitionEntity;
import com.bt.course.api.mapper.ApiDefinitionMapper;
import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.exception.ServiceException;
import com.bt.course.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApiDefinitionServiceImpl extends ServiceImpl<ApiDefinitionMapper, ApiDefinitionEntity> {

    /**
     * 保存接口
     * @param project
     * @param apiDefinition
     * @return
     */
    public Boolean create(Integer project, ApiDefinitionEntity apiDefinition) {
        // 先判断这个接口在当前项目下是否存在，如果存在不能添加
        LambdaQueryWrapper<ApiDefinitionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiDefinitionEntity::getProjectId, project);
        queryWrapper.eq(ApiDefinitionEntity::getDefinitionName, apiDefinition.getDefinitionName());
        queryWrapper.eq(ApiDefinitionEntity::getStatus, 0);
        if (this.count(queryWrapper) > 0) {
            throw new ServiceException(ExceptionCodeEnum.api_definition_exist);
        }
        // 如果不存在，我们给接口保存下来
        apiDefinition.setProjectId(project);
        apiDefinition.setStatus(0);
        apiDefinition.setCreateTime(new Date());
        apiDefinition.setUpdateTime(new Date());
        apiDefinition.setCreateUser(SecurityUtils.getCurrentUserAccount());
        apiDefinition.setUpdateUser(SecurityUtils.getCurrentUserAccount());
       return this.saveOrUpdate(apiDefinition);
    }

    public Boolean delete(Integer definitionId){
        ApiDefinitionEntity definition = this.getById(definitionId);
        definition.setStatus(1);
        definition.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        definition.setUpdateTime(new Date());
        return this.saveOrUpdate(definition);
    }

    public Boolean update(Integer definitionId,ApiDefinitionEntity apiDefinition){
        // 先判断目标接口(先用名字，暂时不用uri)是否存在，如果存在不能修改过去
        LambdaQueryWrapper<ApiDefinitionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiDefinitionEntity::getDefinitionName,apiDefinition.getDefinitionName());
        queryWrapper.eq(ApiDefinitionEntity::getStatus,0);
        if (this.count(queryWrapper) > 0) {
            throw new ServiceException(ExceptionCodeEnum.api_definition_exist);
        }
        ApiDefinitionEntity entity = this.getById(definitionId);
        entity.setDefinitionName(apiDefinition.getDefinitionName());
        entity.setProtocol(apiDefinition.getProtocol());
        entity.setBody(apiDefinition.getBody());
        entity.setRequestData(apiDefinition.getRequestData());
        entity.setUpdateTime(new Date());
        entity.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        // caseCount在此处不做修改，我们接口只改接口的配置，
        // 不能修改接口用例数，用例只有在增、删用例的时候才会做同步
         return this.updateById(entity);
    }
}
