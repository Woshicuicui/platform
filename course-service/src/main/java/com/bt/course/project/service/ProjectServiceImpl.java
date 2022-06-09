package com.bt.course.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.exception.ServiceException;
import com.bt.course.project.entity.ProjectEntity;
import com.bt.course.project.mapper.ProjectMapper;
import com.bt.course.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectEntity> {


    public Boolean updateProject(ProjectEntity project, Integer projectId) {
        // 需要判断id是否传入，如果没传入就报错
        if (projectId == null) {
            throw new ServiceException(ExceptionCodeEnum.params_error);
        }
        // 查project是否存在
        // 两种写法，第一种，用mapper去查 需要引入mapper

        // 第二种 this.getById  不需要引入
//        ProjectEntity project1 = this.getById(project.getId());
//        // 判断项目是否存在
//        if (project1 == null) {
//            throw new ServiceException(ExceptionCodeEnum.project_not_exist);
//        }
        // 通过id查询出的数量来判断
        // 查询 id = projectId
        // 第三种通过判断id存在数量
//        QueryWrapper<ProjectEntity> queryWrapper =
//                new QueryWrapper<ProjectEntity>().eq("id", projectId);
//        // 如果等于0 则代表没有
//        if (this.count(queryWrapper) == 0) {
//            throw new ServiceException(ExceptionCodeEnum.project_not_exist);
//        }

        // 第四种 业务逻辑，加限制的方式修改数据 ,避免人为调用接口修改状态
        ProjectEntity projectEntity = this.getById(projectId);
        // 仅允许修改项目名称、项目描述、名称缩写
        projectEntity.setName(project.getName());
        projectEntity.setShortName(project.getShortName());
        projectEntity.setProjectDesc(project.getProjectDesc());
        projectEntity.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        projectEntity.setUpdateTime(new Date());
        return this.saveOrUpdate(projectEntity);
    }

    public Boolean delete(Integer projectId) {
        //做逻辑删除
        // 把status标记从0 改为 1
        ProjectEntity project = this.getById(projectId);
        // 更改 删除标记
        project.setStatus(1);
        // 更新 修改时间
        project.setUpdateTime(new Date());
        // 更新 修改人
        project.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        // 如果当前项目不存在则写入insert一条数据 如果存在则使用updateById更新
        return this.saveOrUpdate(project);
    }

    /**
     * 分页查询所有项目
     *
     * @param searchKey
     * @param pageNum
     * @param pageSize
     * @return
     */
    public IPage<ProjectEntity> getPage(String searchKey, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ProjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 用等于查询，但此处需要like 模糊查询
//        queryWrapper.eq()
        // 模糊查询，左右模糊
        // 由于searchKey 有可能是 null,我们需要在不为null的时候才做模糊查询，其他时候查询全部
        if (StringUtils.isNoneEmpty(searchKey)) {
            queryWrapper.like(ProjectEntity::getName, searchKey);
            // 如果需要左模糊、或右模糊
            // queryWrapper.likeLeft(ProjectEntity::getName,searchKey);
//        queryWrapper.likeRight(ProjectEntity::getName,searchKey);
        }
        // 创建时间倒序排列
        queryWrapper.eq(ProjectEntity::getStatus,0);
        queryWrapper.orderByDesc(ProjectEntity::getCreateTime);
        return this.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    public ProjectEntity getOne(Integer projectId) {
        LambdaQueryWrapper<ProjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 通过id查询 id = projectId
        queryWrapper.eq(ProjectEntity::getId, projectId);
        // 通过status = 0 查询
        queryWrapper.eq(ProjectEntity::getStatus, 0);
        return getOne(queryWrapper);
    }
}
