package com.mark.blog.service;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.pojo.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {

    Result login(User user);

    User verifyLogin(String token);

    Result logout(String token);

    Result register(User user);
}
