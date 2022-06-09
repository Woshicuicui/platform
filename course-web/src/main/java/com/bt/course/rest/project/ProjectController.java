package com.bt.course.rest.project;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.project.entity.ProjectEntity;
import com.bt.course.project.service.ProjectServiceImpl;
import com.bt.course.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    /**
     * 新增服务
     * RequestMapping ==> 支持 post、get、delete、put等等所有类型
     */
    @PostMapping
    public Response create(@RequestBody ProjectEntity project) {
        if (StringUtils.isEmpty(project.getName())) {
            return ResponseFactory.getError(ExceptionCodeEnum.params_error);
        }
        String user = SecurityUtils.getCurrentUserAccount();
        project.setCreateUser(user);
        project.setUpdateUser(user);
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());

        // 为了支持逻辑删除能力,给status 赋值
        project.setStatus(0);
        return ResponseFactory.getSuccess(projectService.save(project));
    }

    // 删除
    @DeleteMapping("/{projectId}")
    public Response delete(@PathVariable("projectId") Integer projectId) {
        // 判断一下projectId 是否存在，如果不存在就返回一个错误
        if (projectId == null) {
            return ResponseFactory.getError(ExceptionCodeEnum.params_error);
        }
        // 通过ID删除 ，删除的是 实体类中标记的 TableId
        // 物理删除
//        projectService.removeById(projectId);
        // 用逻辑删除
        return ResponseFactory.getSuccess(projectService.delete(projectId));
    }

    // 修改
    @PostMapping("/{projectId}")
    public Response update(@PathVariable("projectId") Integer projectId,
                           @RequestBody ProjectEntity project) {
        if (projectId == null) {
            return ResponseFactory.getError(ExceptionCodeEnum.params_error);
        }
        return ResponseFactory.getSuccess(projectService.updateProject(project, projectId));
    }

    /**
     * 查询所有
     * 因为数据可能过多，需要做分页
     * 页面大小
     * 当前页码
     *
     * @param searchKey 模糊查询匹配,支持非必填
     */
    @GetMapping
    public Response queryAll(@RequestParam(value = "searchKey", required = false) String searchKey,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        IPage<ProjectEntity> page = projectService.getPage(searchKey, pageNum, pageSize);
        return ResponseFactory.getSuccess(page);
    }

    /**
     * 查询单个服务
     * 需要状态为 status = 0 才行
     */
    @GetMapping("/{projectId}")
    public Response query(@PathVariable("projectId") Integer projectId) {
        return ResponseFactory.getSuccess(projectService.getOne(projectId));
    }
}
