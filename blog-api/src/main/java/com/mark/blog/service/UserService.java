package com.mark.blog.service;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.pojo.User;

public interface UserService {

    Result findUserById(Long id);

    User findUser(String username, String password);

    void insertAuthorAndTag(Long authorId, Long tagId);

    Result findUserByToken(String token);

    User findUserByName(String username);

    void insertUser(User user);
}
