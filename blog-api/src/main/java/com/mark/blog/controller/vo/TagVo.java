package com.mark.blog.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TagVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    public TagVo(String name) {
        this.name = name;
    }
}
