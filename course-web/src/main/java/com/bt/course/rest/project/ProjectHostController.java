package com.bt.course.rest.project;

import com.bt.course.base.exception.ExceptionCodeEnum;
import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.project.entity.ProjectHostEntity;
import com.bt.course.project.service.ProjectHostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/project/host")
public class ProjectHostController {

    @Autowired
    private ProjectHostServiceImpl projectHostService;

    //新增host
    @PostMapping("/{envId}")
    public Response create(@PathVariable(value = "envId")Integer envId,
                           @RequestBody ProjectHostEntity projectHost){
        // 参数校验 环境Id、host、ip任一一个为空，则会报错
        if (envId == null|| projectHost.getHost() == null || projectHost.getIp() == null){
            return ResponseFactory.getError(ExceptionCodeEnum.params_error);
        }
        return ResponseFactory.getSuccess(projectHostService.create(envId,projectHost));
    }

    //删除host
    @DeleteMapping("/{envId}/{hostId}")
    public Response delete(@PathVariable(value = "envId")Integer envId,
                           @PathVariable(value = "hostId")Integer hostId){

        return ResponseFactory.getSuccess(projectHostService.delete(hostId));
    }
    //修改host
    @PostMapping("/{envId}/{hostId}")
    public Response update(@PathVariable(value = "envId")Integer envId,
                           @PathVariable(value = "hostId")Integer hostId,
                           @RequestBody ProjectHostEntity projectHost){
        return ResponseFactory.getSuccess(projectHostService.update(hostId,projectHost));
    }
    //查询host
    @GetMapping("/{envId}")
    public Response query(@PathVariable(value = "envId")Integer envId){
        return ResponseFactory.getSuccess(projectHostService.queryAllHost(envId));
    }

}
