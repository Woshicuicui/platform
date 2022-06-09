package com.bt.course.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.exception.ServiceException;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.project.entity.ProjectEntity;
import com.bt.course.project.entity.ProjectEnvEntity;
import com.bt.course.project.mapper.ProjectEnvMapper;
import com.bt.course.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectEnvServiceImpl extends ServiceImpl<ProjectEnvMapper, ProjectEnvEntity> {

    public Boolean create(Integer projectId, ProjectEnvEntity projectEnv) {
        // 创建之前先检查这个环境名称是否已经存在，如果存在则不能添加
        checkIsExist(projectId,projectEnv.getName());

        projectEnv.setProjectId(projectId);
        projectEnv.setCreateTime(new Date());
        projectEnv.setUpdateTime(new Date());
        projectEnv.setCreateUser(SecurityUtils.getCurrentUserAccount());
        projectEnv.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        projectEnv.setStatus(0);
        return this.saveOrUpdate(projectEnv);
    }

    public Boolean delete(Integer envId){
        // 通过envId（主键可以用this.getById(envId))查询出的记录，修改状态
        ProjectEnvEntity entity = this.getById(envId);
        entity.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        entity.setUpdateTime(new Date());
        entity.setStatus(1);
        return this.saveOrUpdate(entity);
    }

    public Boolean update(Integer envId,ProjectEnvEntity projectEnv){
        // 先取出来数据，判断一下数据是否存在，如果存在开始执行替换工作
        ProjectEnvEntity entity = this.getById(envId);
        // 如果为空，则抛环境不存在的异常
        if (entity == null) {
            throw new ServiceException(ExceptionCodeEnum.project_env_not_exist);
        }
        // 修改前判断一下目标环境是否存在，如果不存在才可以继续修改环境
        checkIsExist(entity.getProjectId(),projectEnv.getName());
        // 非用户可输入的字段不做替换
        entity.setName(projectEnv.getName());
        entity.setEnvDesc(projectEnv.getEnvDesc());
        entity.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        entity.setUpdateTime(new Date());
        return this.saveOrUpdate(entity);
    }

    public IPage<ProjectEnvEntity> getPage(Integer projectId, String searchKey, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ProjectEnvEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 用等于查询，但此处需要like 模糊查询
//        queryWrapper.eq()
        // 模糊查询，左右模糊
        // 由于searchKey 有可能是 null,我们需要在不为null的时候才做模糊查询，其他时候查询全部
        // 如果需要左模糊、或右模糊
        // queryWrapper.likeLeft(ProjectEntity::getName,searchKey);
        // queryWrapper.likeRight(ProjectEntity::getName,searchKey);
        queryWrapper.eq(ProjectEnvEntity::getProjectId,projectId);
        if (StringUtils.isNoneEmpty(searchKey)) {
            queryWrapper.like(ProjectEnvEntity::getName, searchKey);
        }
        // 创建时间倒序排列
        queryWrapper.eq(ProjectEnvEntity::getStatus,0);
        queryWrapper.orderByDesc(ProjectEnvEntity::getCreateTime);
        return this.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /**
     * 判断环境是否存在，如果存在则抛异常，如果不存在任何处理，这样其他代码就继续运行
     * @param projectId
     * @param envName
     */
    private void checkIsExist(Integer projectId,String envName){
        // 修改前判断一下目标环境是否存在，如果不存在才可以继续修改环境
        LambdaQueryWrapper<ProjectEnvEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectEnvEntity::getProjectId, projectId);
        queryWrapper.eq(ProjectEnvEntity::getName, envName);
        // 筛选一下只过滤可用状态的，避免和已删除状态的数据重复冲突导致无法创建
        queryWrapper.eq(ProjectEnvEntity::getStatus, 0);
        int count = this.count(queryWrapper);
        // 如果已存在，抛出异常，环境已存在，无法重复添加
        if (count > 0) {
            throw new ServiceException(ExceptionCodeEnum.project_env_exist);
        }
    }
}
