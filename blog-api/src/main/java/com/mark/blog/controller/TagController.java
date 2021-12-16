package com.mark.blog.controller;

import com.mark.blog.controller.vo.Result;
import com.mark.blog.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("blog/tags")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 查询所有标签
     * @return 所有标签
     */
    @GetMapping("list")
    public Result listTags(){
        return tagService.listTags();
    }
}
