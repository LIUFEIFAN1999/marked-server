package com.mark.blog.dao.pojo;

import lombok.Data;

@Data
public class Article {

    private Long id;

    private String title;

    private String description;

    private String avatar;

    private int pageView;

    private Long createTime;

    private Long authorId;

    private String content;
}
