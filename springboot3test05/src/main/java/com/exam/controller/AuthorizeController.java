package com.exam.controller;

import com.exam.entity.RestBean;
import com.exam.service.StuUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthorizeController {
    @Resource
    StuUserService service;
    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@RequestParam String email,
                                        @RequestParam String type,
                                        HttpServletRequest request){
        String message = service.registerEmailVerifyCode(type,email,request.getRemoteAddr());
        return message==null ? RestBean.success():RestBean.failure(400,message);
    }
}
