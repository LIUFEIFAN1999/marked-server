package com.mark.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mark.blog.dao.pojo.Tag;
import com.mark.blog.dao.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    String findUserByArticleId(Long articleId);

    void insertAuthorAndTag(@Param("authorId") Long authorId, @Param("tagId") Long tagId);

    Long findAuthorByTag(@Param("tagId") Long id);
}
