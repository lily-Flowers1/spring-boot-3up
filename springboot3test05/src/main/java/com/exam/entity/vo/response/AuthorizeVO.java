package com.exam.entity.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizeVO {
    String stuUsername;
    String stuRole;
    String token;
    Date expire;

}
