package com.mark.blog.controller;


import com.mark.blog.controller.vo.Result;
import com.mark.blog.dao.pojo.Collection;
import com.mark.blog.service.CollectionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("blog/collection")
public class CollectionController {

    @Resource
    private CollectionService collectionService;

    @PostMapping("list")
    public Result list(){
        return collectionService.list();
    }

    @PostMapping("add")
    public Result addLink(@RequestBody Collection collection){
        return collectionService.insert(collection);
    }
}
