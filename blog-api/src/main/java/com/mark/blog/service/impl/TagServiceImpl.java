package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.controller.vo.TagVo;
import com.mark.blog.dao.mapper.TagMapper;
import com.mark.blog.dao.pojo.Tag;
import com.mark.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagVo> listTagsByArticle(Long articleId){
        List<Tag> tagList = tagMapper.listTagsByArticle(articleId);
        return transList(tagList);
    }

    @Override
    public Result listTags() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Tag::getId);
        List<Tag> records = tagMapper.selectList(queryWrapper);
        List<TagVo> tagVoList = transList(records);
        return Result.success(tagVoList);
    }

    @Override
    public List<TagVo> listTagsByUser(Long userId) {
        List<Tag> tagList = tagMapper.listTagsByUser(userId);
        return transList(tagList);
    }

    @Override
    public List<Tag> findTagsByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return tagList;
    }

    @Override
    public void insertTag(Tag tag) {
        tagMapper.insert(tag);
    }

    @Override
    public Long findTagIdByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return tagMapper.selectOne(queryWrapper).getId();
    }

    public List<TagVo> transList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag: tagList) {
            tagVoList.add(trans(tag));
        }
        return tagVoList;
    }

    public TagVo trans(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
}
