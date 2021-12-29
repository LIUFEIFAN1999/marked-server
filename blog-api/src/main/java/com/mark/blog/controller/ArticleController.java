package com.mark.blog.controller;

import com.mark.blog.controller.vo.ArticleVo;
import com.mark.blog.controller.vo.PageParams;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.service.impl.ArticleServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("blog/articles")
public class ArticleController {

    @Resource
    private ArticleServiceImpl articleService;

    /**
     * 首页文章列表
     * @param pageParams 页号与大小
     * @return 文章列表
     */
    @PostMapping("list")
    public Result listArticles(@RequestBody PageParams pageParams) {
        return articleService.listArticles(pageParams);
    }


    /**
     * 根据文章id查找文章
     * @param articleId 文章id
     * @return 文章信息
     */
    @PostMapping("id/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }


    /**
     *根据标签id查询文章
     * @param pageParams 分页
     * @param tagId 标签id
     * @return 标签对应文章列表
     */
    @PostMapping("list/{tagId}")
    public Result listArticlesByTagId(@RequestBody PageParams pageParams, @PathVariable("tagId") Long tagId){
        return articleService.findArticlesByTagId(pageParams, tagId);
    }

    /**
     * 根据作者id查找文章
     * @param authorId 作者id
     * @return 作者的所有文章
     */
    @PostMapping("byAuthor/{authorId}")
    public Result findArticlesByAuthorId(@PathVariable("authorId") Long authorId){
        return articleService.findHotArticlesByAuthorId(authorId);
    }

    /**
     * 根据作者id查询最热的6篇文章
     * @param authorId 作者id
     * @return 作者最热6篇文章
     */
    @PostMapping("hot/byAuthor/{authorId}")
    public Result findHotArticlesByAuthorId(@PathVariable("authorId") Long authorId){
        return articleService.findHotArticlesByAuthorId(authorId);
    }

    /**
     * 添加文章
     * @param articleVo 文章对象
     * @param token 身份认证
     * @return 添加是否成功
     */
    @PostMapping("add")
    public Result AddArticle(@RequestBody ArticleVo articleVo, @RequestHeader("Authorization") String token){
        return articleService.addArticle(articleVo, token);
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return 文件路径
     */
    @PostMapping("uploadImg")
    public Result uploadImg(@RequestParam("image") MultipartFile file) {
        return articleService.uploadImg(file);
    }

    /**
     * 根据关键字查询
     * @param keyword 关键词
     * @return 文章列表
     */
    @PostMapping("list/keyword/{keyword}")
    public Result findArticleByKeyword(@PathVariable("keyword") String keyword){
        return articleService.findArticleByKeyword(keyword);
    }
}
