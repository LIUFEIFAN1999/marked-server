package com.mark.blog.service;

import com.mark.blog.controller.vo.Result;

public interface UserService {

    Result findUserById(Long id);

    void insertAuthorAndTag(Long authorId, Long tagId);

}
