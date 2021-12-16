package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.mapper.CollectionMapper;
import com.mark.blog.dao.pojo.Collection;
import com.mark.blog.service.CollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Resource
    private CollectionMapper collectionMapper;

    @Override
    public Result list() {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Collection::getId);
        List<Collection> collections = collectionMapper.selectList(queryWrapper);
        return Result.success(collections);
    }

    @Override
    public Result insert(Collection collection) {
        collectionMapper.insert(collection);
        return Result.success("收藏成功!");
    }
}
