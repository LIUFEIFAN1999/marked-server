package com.mark.blog.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String username;

    private String email;

    private String avatar;

    private int totalPageView;

    private int blogAge;

    private int blogCount;

    private List<TagVo> tags;
}
