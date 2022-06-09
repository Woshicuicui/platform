package com.bt.course.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.exception.ServiceException;
import com.bt.course.project.entity.ProjectHostEntity;
import com.bt.course.project.mapper.ProjectHostMapper;
import com.bt.course.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Queue;

@Service
public class ProjectHostServiceImpl extends ServiceImpl<ProjectHostMapper, ProjectHostEntity> {

    /**
     * 创建host
     *
     * @param envId       环境ID
     * @param projectHost host配置
     * @return 结果
     */
    public Boolean create(Integer envId, ProjectHostEntity projectHost) {
        // www.besttest.cn 127.0.0.1
        // www.besttest.cn 192.168.1.1
        // ip是可以重复的，但是host域名是不能重复
        // 需要对host做一个重复性校验
        LambdaQueryWrapper<ProjectHostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectHostEntity::getHost, projectHost.getHost());
        queryWrapper.eq(ProjectHostEntity::getEnvId, envId);
        queryWrapper.eq(ProjectHostEntity::getStatus, 0);
        // todo 检查 ip是否是IPV4类型的地址，如果不是报IP检测出错

        // 查环境下的host 是否存在，如果存在则不能添加
        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new ServiceException(ExceptionCodeEnum.project_host_exist);
        }
        projectHost.setCreateTime(new Date());
        projectHost.setUpdateTime(new Date());
        projectHost.setCreateUser(SecurityUtils.getCurrentUserAccount());
        projectHost.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        projectHost.setStatus(0);
        return this.save(projectHost);
    }

    public Boolean delete(Integer id) {
        ProjectHostEntity hostEntity = this.getById(id);
        hostEntity.setStatus(1);
        hostEntity.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        hostEntity.setUpdateTime(new Date());
        return this.saveOrUpdate(hostEntity);
    }

    public Boolean update(Integer hostId, ProjectHostEntity projectHost) {
        ProjectHostEntity hostEntity = this.getById(hostId);
        hostEntity.setHost(projectHost.getHost());
        hostEntity.setIp(projectHost.getIp());
        hostEntity.setUpdateUser(SecurityUtils.getCurrentUserAccount());
        hostEntity.setUpdateTime(new Date());
        return this.saveOrUpdate(hostEntity);
    }

    public List<ProjectHostEntity> queryAllHost(Integer envId){
        // 查询所有的host
        // 通过环境ID envId查询
        LambdaQueryWrapper<ProjectHostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectHostEntity::getStatus,0);
        queryWrapper.eq(ProjectHostEntity::getEnvId,envId);
        queryWrapper.orderByDesc(ProjectHostEntity::getHost);
        return this.list(queryWrapper);

    }
}
