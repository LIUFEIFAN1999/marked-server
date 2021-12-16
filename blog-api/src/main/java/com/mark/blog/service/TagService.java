package com.mark.blog.service;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.controller.vo.TagVo;
import com.mark.blog.dao.pojo.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    /**
     * 查找文章标签
     * @param articleId 文章id
     * @return 文章的全部标签
     */
    List<TagVo> listTagsByArticle(Long articleId);

    /**
     * 查询所有标签列表
     * @return 标签列表结果
     */
    Result listTags();

    /**
     * 通过用户查询标签
     * @param userId 用户id
     * @return 标签列表
     */
    List<TagVo> listTagsByUser(Long userId);

    List<Tag> findTagsByName(String name);

    void insertTag(Tag tag);

    Long findTagIdByName(String name);
}
