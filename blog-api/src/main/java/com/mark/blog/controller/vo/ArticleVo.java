package com.mark.blog.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String title;

    private String description;

    private String avatar;

    private int pageView;

    private String createTime;

    private Long authorId;

    private String content;

    private List<TagVo> tags;
}
