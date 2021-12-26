package com.mark.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.pojo.User;
import com.mark.blog.service.LoginService;
import com.mark.blog.service.UserService;
import com.mark.blog.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final String salt = "markedBlog%$#@";

    @Override
    public Result login(User user) {
        if(user == null || StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
            return Result.fail(1000, "登录参数不合法");
        }
        String password = DigestUtils.md5Hex(user.getPassword() + salt);
        user = userService.findUser(user.getUsername(), password);
        if(user == null){
            return Result.fail(1001, "用户名不存在或密码错误");
        }
        String jwt = JwtUtils.createJWT(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+jwt, JSON.toJSONString(user), 30, TimeUnit.MINUTES);
        return Result.success(jwt);
    }

    @Override
    public User verifyLogin(String token) {
        if(token == null){
            return null;
        }
        Map<String, Object> stringObjectMap = JwtUtils.verifyJwt(token);
        if(stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(userJson == null){
            return null;
        }
        return JSON.parseObject(userJson, User.class);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        if(user==null || StringUtils.isAnyBlank(user.getUsername(), user.getEmail(), user.getPassword())){
            return Result.fail(1006, "注册信息不合法");
        }

        if(userService.findUserByName(user.getUsername()) != null){
            return Result.fail(1003, "该用户名已被注册");
        }
        user.setPassword(DigestUtils.md5Hex(user.getPassword() + salt));
        user.setRegisterTime(System.currentTimeMillis());
        user.setCurrentOnlineTime(System.currentTimeMillis());
        userService.insertUser(user);
        user = userService.findUserByName(user.getUsername());
        String jwt = JwtUtils.createJWT(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+jwt, JSON.toJSONString(user), 30, TimeUnit.MINUTES);
        return Result.success(jwt);
    }
}
