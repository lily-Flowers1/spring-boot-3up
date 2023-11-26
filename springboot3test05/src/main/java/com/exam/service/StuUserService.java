package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.dto.StuUser;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface StuUserService extends IService<StuUser>, UserDetailsService {
    StuUser findAccountByNameOrEmail(String text);
    String registerEmailVerifyCode(String type,String email,String ip);
}
