package com.bt.course.rest.project;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.project.entity.ProjectEnvEntity;
import com.bt.course.project.service.ProjectEnvServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/project/env")
public class ProjectEnvController {
    /*
     *  Autowired和Resource的区别？
     */

    @Autowired
    private ProjectEnvServiceImpl projectEnvService;

    // 查询项目下的所有环境
    @GetMapping("/{projectId}")
    public Response query(@PathVariable(value = "projectId") Integer projectId,
                          @RequestParam(value = "searchKey", required = false) String searchKey,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize) {
        IPage<ProjectEnvEntity> page = projectEnvService.getPage(projectId, searchKey, pageNum, pageSize);
        return ResponseFactory.getSuccess(page);
    }

    //创建环境
    @PostMapping("/{projectId}")
    public Response create(@PathVariable(value = "projectId") Integer projectId,
                           @RequestBody ProjectEnvEntity projectEnvEntity) {
        // 如果名称为空、projectId，则无法创建环境，返回一个错误信息
        if (projectEnvEntity.getName() == null || projectId == null) {
            return ResponseFactory.getError(ExceptionCodeEnum.params_error);
        }
        return ResponseFactory.getSuccess(projectEnvService.create(projectId, projectEnvEntity));
    }

    //删除环境
    @DeleteMapping("/{projectId}/{envId}")
    public Response delete(@PathVariable(value = "projectId") Integer projectId,
                           @PathVariable(value = "envId") Integer envId) {
        return ResponseFactory.getSuccess(projectEnvService.delete(envId));
    }

    //修改环境
    @PostMapping("/{projectId}/{envId}")
    public Response update(@PathVariable(value = "projectId") Integer projectId,
                           @PathVariable(value = "envId") Integer envId,
                           @RequestBody ProjectEnvEntity projectEnv) {
        return ResponseFactory.getSuccess(projectEnvService.update(envId,projectEnv));
    }
}
