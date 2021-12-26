package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.controller.vo.UserVo;
import com.mark.blog.dao.mapper.UserMapper;
import com.mark.blog.dao.pojo.User;
import com.mark.blog.service.ArticleService;
import com.mark.blog.service.LoginService;
import com.mark.blog.service.TagService;
import com.mark.blog.service.UserService;
import com.mark.blog.service.bo.ArticleSum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleService articleService;

    @Resource
    private LoginService loginService;

    /**
     * 根据文章id查找作者名字
     * @param articleId 文章id
     * @return 作者名称
     */
    public String findUserByArticleId(Long articleId){
        return userMapper.findUserByArticleId(articleId);
    }

    /**
     * 根据id查找用户
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public Result findUserById(Long userId) {
        User user = userMapper.selectById(userId);
        UserVo userVo = trans(user);
        return Result.success(userVo);
    }

    @Override

    public User findUser(String username, String password) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        userLambdaQueryWrapper.eq(User::getPassword, password);
        userLambdaQueryWrapper.select(User::getId, User::getUsername, User::getAvatar, User::getEmail);
        userLambdaQueryWrapper.last("limit 1");
        return userMapper.selectOne(userLambdaQueryWrapper);
    }

    @Override
    public void insertAuthorAndTag(Long authorId, Long tagId) {
        if(userMapper.findAuthorByTag(tagId)==null){
            userMapper.insertAuthorAndTag(authorId, tagId);
        }
    }

    @Override
    public Result findUserByToken(String token) {
        User user = loginService.verifyLogin(token);
        if(user == null){
            return Result.fail(1002, "token不合法");
        }
        return Result.success(user);
    }

    @Override
    public User findUserByName(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    /**
     * 将User转为UserVo
     * @param user User对象
     * @return UserVo对象
     */
    public UserVo trans(User user){
        UserVo userVo = new UserVo();
        Long userId = user.getId();
        BeanUtils.copyProperties(user, userVo);
        int blogAge = (int)(new Date().getTime() - user.getRegisterTime())/(24*60*60*1000);
        userVo.setBlogAge(blogAge);
        userVo.setTags(tagService.listTagsByUser(userId));
        ArticleSum articleSum = articleService.findArticleSummaryByAuthorId(userId);
        userVo.setBlogCount(articleSum.getBlogCount());
        userVo.setTotalPageView(articleSum.getTotalPageView());
        return userVo;
    }
}
