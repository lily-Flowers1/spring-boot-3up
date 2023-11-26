package com.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.dto.StuUser;
import com.exam.mapper.StuUserMapper;
import com.exam.service.StuUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StuUserServiceImpl extends ServiceImpl<StuUserMapper, StuUser> implements StuUserService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StuUser stuUser = this.findAccountByNameOrEmail(username);
        if (stuUser==null)throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(username)
                .password(stuUser.getStuPasswd())
                .roles(stuUser.getStuRole())
                .build();
    }
    public StuUser findAccountByNameOrEmail(String text){
        return this.query()
                .eq("stu_username",text).or()
                .eq("stu_email",text)
                .one();
    }
}
