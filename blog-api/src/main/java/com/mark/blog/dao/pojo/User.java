package com.mark.blog.dao.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private Long registerTime;
    private Long currentOnlineTime;
    private String password;
}
