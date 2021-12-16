package com.mark.blog.service;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.pojo.Collection;

public interface CollectionService {
    /**
     * 查询所有收藏链接
     * @return 收藏链接表
     */
    Result list();

    Result insert(Collection collection);
}
