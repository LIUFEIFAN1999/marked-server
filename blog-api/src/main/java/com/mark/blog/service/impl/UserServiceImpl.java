package com.mark.blog.service.impl;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.controller.vo.UserVo;
import com.mark.blog.dao.mapper.UserMapper;
import com.mark.blog.dao.pojo.User;
import com.mark.blog.service.ArticleService;
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
    public void insertAuthorAndTag(Long authorId, Long tagId) {
        if(userMapper.findAuthorByTag(tagId)==null){
            userMapper.insertAuthorAndTag(authorId, tagId);
        }
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
