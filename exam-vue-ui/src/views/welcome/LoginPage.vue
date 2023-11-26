<script setup>
import {reactive,ref} from "vue";
import { User,Lock } from '@element-plus/icons-vue'
import {login} from "@/net";
import router from "@/router";

const formRef = ref()

const form = reactive({
  username:'',
  password:'',
  remember:false
})
const rule = {
  username:[
    {required: true,message: '请输入用户名'}
  ],
  password:[
    {required: true,message: '请输入密码'}
  ]
}
function userLogin(){
  formRef.value.validate((valid)=>{
    if (valid){
      login(form.username,form.password,form.remember,()=>{router.push('/index')})
    }
  })
}

</script>
<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 150px">
      <div style="font-size: 50px;font-weight: bold">登录</div>
      <div style="font-size: 14px;color: grey">进入系统之前先登录</div>
    </div>

    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rule" ref="formRef">

        <el-form-item prop="username">
          <el-input v-model="form.username" maxlength="20" type="text" placeholder="用户名/邮箱">
            <template #prefix><el-icon><User/></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" maxlength="20" type="password" placeholder="密码">
            <template #prefix><el-icon><Lock/></el-icon></template>
          </el-input>
        </el-form-item>

        <el-row>
          <el-col :span="12">
            <el-form-item prop="remember">
              <el-checkbox v-model="form.remember" label="记住我"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-link>忘记密码？</el-link>
          </el-col>
        </el-row>

      </el-form>
    </div>
    <div style="margin-top: 40px">
      <el-button @click="userLogin" style="width: 270px" type="success" plain>登录</el-button>
    </div>
    <el-divider>
      <span style="font-size: 14px;color: grey">没有账号？</span>
    </el-divider>
    <el-button style="width: 270px" type="warning" plain>注册</el-button>
  </div>

</template>
<style scoped>

</style>