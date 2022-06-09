package com.bt.course.rest.security;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bt.course.base.response.Response;
import com.bt.course.base.response.ResponseFactory;
import com.bt.course.security.entity.User;
import com.bt.course.security.service.UserServiceImpl;
import com.bt.course.security.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 获取所有用户详细信息
     */
    @GetMapping("users")
    public Response list(@RequestParam(value = "searchKey", required = false) String searchKey,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "25") int pageSize) {

        IPage<User> userPage = userService.getPage(searchKey, pageNum, pageSize);
        return ResponseFactory.getSuccess(userPage);
    }

    /**
     * 添加一个用户
     */
    @PostMapping("users")
    public Response add(@RequestParam(value = "account") String account,
                        @RequestParam(value = "pwd") String pwd,
                        @RequestParam(value = "name") String name) {
        return ResponseFactory.getSuccess(userService.register(account, pwd, name));
    }

    /**
     * 查询用户详细信息
     */
    @GetMapping("users/{userId}")
    public Response user(@PathVariable Long userId) {
        User user = userService.getById(userId);
        user.setUserPass(null);
        return ResponseFactory.getSuccess(user);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("users/{userId}")
    public Response delete(@PathVariable Long userId) {
        return userService.removeById(userId) ? ResponseFactory.getSuccess() : ResponseFactory.getError();
    }

    /**
     * 修改用户
     * @param userId
     * @param vo
     * @return
     */
    @PutMapping("users/{userId}")
    public Response update(@PathVariable Long userId, @RequestBody UserVo vo) {
        User user = User.builder().id(userId).userNickname(vo.getUserNicename())
                .userEmail(vo.getUserEmail())
                .userPhone(vo.getUserPhone()).userStatus(vo.getUserStatus()).build();
        return userService.updateById(user) ? ResponseFactory.getSuccess() : ResponseFactory.getError();
    }

}
