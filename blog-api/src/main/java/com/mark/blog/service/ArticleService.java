package com.mark.blog.service;

import com.mark.blog.controller.vo.ArticleVo;
import com.mark.blog.controller.vo.PageParams;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.service.bo.ArticleSum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams 分页信息
     * @return 文章列表
     */
    Result listArticles(PageParams pageParams);

    /**
     * 根据Tag查找文章
     * @param pageParams 分页信息
     * @param tagId 标签id
     * @return 文章列表
     */
    Result findArticlesByTagId(PageParams pageParams, Long tagId);

    /**
     * 根据作者id查找文章数量
     * @param authorId 作者id
     * @return 文章数量
     */
    ArticleSum findArticleSummaryByAuthorId(Long authorId);

    /**
     * 根据作者id查找所有文章
     * @param authorId 作者id
     * @return 文章列表
     */
    List<ArticleVo> findArticlesByAuthorId(Long authorId);

    /**
     * 根据作者查找最热6篇文章
     * @param authorId 作者id
     * @return 浏览量最多的6篇文章，少于6篇展示全部
     */
    Result findHotArticlesByAuthorId(Long authorId);

    /**
     * 根据文章id查找作者详细信息
     * @param articleId 文章id
     * @return 作者信息
     */
    Result findAuthorDetailByArticleId(Long articleId);

    Result findArticleById(Long articleId);

    Result addArticle(ArticleVo articleVo, String token);

    Result uploadImg(MultipartFile file);

    Result findArticleByKeyword(String keyword);
}
