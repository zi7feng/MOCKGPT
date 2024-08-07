package com.fzq.mockgpt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phone;

    private String userPassword;

    private String lastName;

    private String firstName;

    private String middleName;

    private String nickname;

    private String avatarUrl;

    private Boolean isGptPlus;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;


}
