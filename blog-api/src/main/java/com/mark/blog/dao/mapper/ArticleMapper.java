package com.mark.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends BaseMapper<Article> {

    Page<Article> findArticlesByTagId(IPage<Article> page, @Param("tagId") Long tagId);

    void insertArticleAndTag(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

}
