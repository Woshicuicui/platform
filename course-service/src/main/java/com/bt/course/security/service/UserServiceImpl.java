package com.bt.course.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bt.course.base.exception.ServiceException;
import com.bt.course.security.entity.User;
import com.bt.course.security.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> {

    public final static String hashAlgorithmName = "MD5";

    private static final int hashIterations = 1;


    @Resource
    private UserMapper userMapper;


    /**
     * 使用分页查询所有用户
     *
     * @param searchKey
     * @param pageNum
     * @param pageSize
     * @return
     */
    public IPage<User> getPage(String searchKey, int pageNum, int pageSize) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(searchKey)) {
            wrapper.like("username", searchKey).or().like("user_nicename", searchKey).or().like("user_phone", searchKey);
        }
        wrapper.orderByDesc("user_registered");
        return this.page(new Page<>(pageNum, pageSize), wrapper);
    }

    /**
     * 根据用户账号获取用户详细信息，包括角色
     *
     * @param account
     * @return
     */
    public User getByAccount(String account) {
        return getOne(new QueryWrapper<User>().eq("username", account));
    }

    public boolean isExist(String account) {
        return userMapper.selectCount(new QueryWrapper<User>().eq("username", account)) > 0;
    }

    @Transactional
    public User register(String account, String pwd, String name) {
        if (isExist(account)) {
            throw new ServiceException("账号已存在");
        }
        String activeKey = RandomStringUtils.randomAlphanumeric(6);
        String encodePassword = md5(pwd, activeKey);
        User user = new User();
        user.setUserLogin(account);
        user.setUserPass(encodePassword);
        user.setUserRegistered(new Date());
        user.setUserActivationKey(activeKey);
        user.setUserNickname(name);
        userMapper.insert(user);
        //为用户赋予角色
        //roleService.grantUserRole(user.getId(), 1L);
        return user;
    }


    public String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

}
