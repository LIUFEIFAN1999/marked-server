package com.mark.blog.controller;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.service.ArticleService;
import com.mark.blog.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("blog/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    /**
     * 根据id查询用户
     * @param userId 用户id
     * @return 用户信息
     */
    @PostMapping("{id}")
    public Result findUserById(@PathVariable("id") Long userId){
        return userService.findUserById(userId);
    }

    /**
     * 根据文章id查找作者
     * @param articleId 文章id
     * @return 作者信息
     */
    @PostMapping("byArticle/{articleId}")
    public Result findAuthorDetailByArticleId(@PathVariable("articleId") Long articleId) {
        return articleService.findAuthorDetailByArticleId(articleId);
    }

    @PostMapping("current")
    public Result getCurrentUser(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
    }
}
