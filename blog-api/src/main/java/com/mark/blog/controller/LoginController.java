package com.mark.blog.controller;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.pojo.User;
import com.mark.blog.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public Result login(@RequestBody User user){
        return loginService.login(user);
    }

    @PostMapping("logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }

    @PostMapping("register")
    public Result register(@RequestBody User user){
        return loginService.register(user);
    }

    @PostMapping("verify")
    public Result verify(@RequestHeader("Authorization") String token){
        return Result.success(loginService.verifyLogin(token));
    }
}
