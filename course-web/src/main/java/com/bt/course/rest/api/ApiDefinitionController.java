package com.bt.course.rest.api;

import com.bt.course.api.entity.ApiDefinitionEntity;
import com.bt.course.api.service.ApiDefinitionServiceImpl;
import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/definition")
public class ApiDefinitionController {

    @Autowired
    private ApiDefinitionServiceImpl apiDefinitionService;

    // todo 给大家留做作业完成
    //查询所有接口表
    public Response queryAll() {

        return ResponseFactory.getSuccess();
    }

    //查询单个接口详情
    public Response query() {

        return ResponseFactory.getSuccess();

    }

    //添加接口
    @PostMapping("/{projectId}")
    public Response create(@PathVariable(value = "projectId") Integer projectId,
                           @RequestBody ApiDefinitionEntity apiDefinition) {
        // todo 非空判断，需要校验一下必填字段
        return ResponseFactory.getSuccess(apiDefinitionService.create(projectId, apiDefinition));
    }

    //删除接口
    @DeleteMapping("/{projectId}/{definitionId}")
    public Response delete(@PathVariable("projectId")Integer projectId,
                           @PathVariable("definitionId") Integer definitionId) {
        return ResponseFactory.getSuccess(apiDefinitionService.delete(definitionId));
    }

    //修改接口
    @PostMapping("/{projectId}/{definitionId}")
    public Response update(@PathVariable("definitionId") Integer definitionId,
                           @RequestBody ApiDefinitionEntity apiDefinition) {
        return ResponseFactory.getSuccess(apiDefinitionService.update(definitionId,apiDefinition));
    }

}
