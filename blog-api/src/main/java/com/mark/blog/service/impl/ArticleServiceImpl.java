package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.blog.controller.vo.ArticleVo;
import com.mark.blog.controller.vo.PageParams;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.controller.vo.TagVo;
import com.mark.blog.dao.mapper.ArticleMapper;
import com.mark.blog.dao.pojo.Article;
import com.mark.blog.dao.pojo.Tag;
import com.mark.blog.service.ArticleService;
import com.mark.blog.service.TagService;
import com.mark.blog.service.UserService;
import com.mark.blog.service.bo.ArticleSum;
import com.mark.blog.utils.SaveFile;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagService tagService;

    @Resource
    private UserService userService;

    @Override
    public Result listArticles(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateTime);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = transList(records);
        return Result.success(articleVoList);
    }

    @Override
    public Result findArticlesByTagId(PageParams pageParams, Long tagId) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        Page<Article> articlePage = articleMapper.findArticlesByTagId(page, tagId);
        List<ArticleVo> articleVoList = transList(articlePage.getRecords());
        return Result.success(articleVoList);
    }

    @Override
    public ArticleSum findArticleSummaryByAuthorId(Long authorId) {
        ArticleSum articleSum = new ArticleSum();
        List<ArticleVo> articleList = findArticlesByAuthorId(authorId);
        int blogCount = articleList.size();
        int totalPageView = articleList.stream().mapToInt(ArticleVo::getPageView).sum();
        articleSum.setBlogCount(blogCount);
        articleSum.setTotalPageView(totalPageView);
        return articleSum;
    }

    @Override
    public List<ArticleVo> findArticlesByAuthorId(Long authorId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", authorId);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return transList(articleList);
    }

    @Override
    public Result findHotArticlesByAuthorId(Long authorId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", authorId);
        queryWrapper.lambda().orderByDesc(Article::getPageView).last("limit 6");
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(transList(articleList));
    }

    @Override
    public Result findAuthorDetailByArticleId(Long articleId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("author_id");
        Long authorId = articleMapper.selectById(articleId).getAuthorId();
        return userService.findUserById(authorId);
    }

    @Override
    public Result findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = trans(article, true);
        return Result.success(articleVo);
    }

    @Override
    public Result addArticle(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);
        //作者id
        Long authorId = 1L;
        article.setAuthorId(authorId);
        article.setPageView(0);
        article.setCreateTime(new DateTime().getMillis());
        String content = SaveFile.string2md(article.getTitle()+".md", article.getContent());
        article.setContent(content);
        articleMapper.insert(article);
        Long articleId = findArticleIdByTitle(articleVo.getTitle());
        List<TagVo> tagVoList = articleVo.getTags();
        for (TagVo tag:tagVoList) {
            //如果标签为新标签
            Long tagId;
            List<Tag> tagList = tagService.findTagsByName(tag.getName());
            if(tagList.size()==0){
               tagService.insertTag(new Tag(null,tag.getName()));
               tagId = tagService.findTagIdByName(tag.getName());
            }
            else{
                tagId = tagList.get(0).getId();
            }
            insertArticleAndTag(articleId, tagId);
            userService.insertAuthorAndTag(authorId, tagId);
        }
        return Result.success("Save article successfully");
    }

    @Override
    public Result uploadImg(MultipartFile file){
        String path = SaveFile.saveImg(file);
        return Result.success(path);
    }

    @Override
    public Result findArticleByKeyword(String keyword) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle, "%"+keyword+"%");
        queryWrapper.orderByDesc(Article::getCreateTime);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = transList(articleList);
        return Result.success(articleVoList);
    }


    private void insertArticleAndTag(Long articleId, Long tagId) {
        articleMapper.insertArticleAndTag(articleId, tagId);
    }

    private Long findArticleIdByTitle(String title) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("title", title);
        return articleMapper.selectOne(articleQueryWrapper).getId();
    }

    private List<ArticleVo> transList(List<Article> records) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article: records) {
            articleVoList.add(trans(article, false));
        }
        return articleVoList;
    }

    private ArticleVo trans(Article article, boolean hasTag){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateTime(new DateTime(article.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
        if(hasTag){
            articleVo.setTags(tagService.listTagsByArticle(article.getId()));
        }
        return articleVo;
    }
}
