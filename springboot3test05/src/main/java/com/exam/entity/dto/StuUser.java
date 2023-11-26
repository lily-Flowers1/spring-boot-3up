package com.exam.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.exam.entity.vo.response.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student_user")
@AllArgsConstructor
public class StuUser implements BaseData{
    @TableId(type = IdType.AUTO)
    private int stuId;
    private String stuUsername;
    private String stuPasswd;
    private String stuEmail;
    private String stuRole;
    private Date stuRegisterTime;
}
