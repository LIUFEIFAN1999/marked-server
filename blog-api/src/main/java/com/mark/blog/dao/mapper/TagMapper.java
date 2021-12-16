package com.mark.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mark.blog.controller.vo.TagVo;
import com.mark.blog.dao.pojo.Tag;

import java.util.List;


public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> listTagsByArticle(Long articleId);

    List<Tag> listTagsByUser(Long userId);
}
