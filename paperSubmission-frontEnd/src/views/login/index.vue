<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <div class="title-container">
        <h3 class="title">期刊投稿系统</h3>
      </div>
      <!-- <el-form-item prop="role">
        <span class="svg-container">
          <i class="el-icon-s-cooperation" />
        </span>
        <el-select v-model="loginForm.role">
          <el-option label="普通用户" value="0" />
          <el-option label="审稿人" value="1" />
          <el-option label="超管" value="2" />
        </el-select>
      </el-form-item> -->
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-form-item>

        <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:10px;" @click.native.prevent="handleLogin">登陆</el-button>
        <el-button type="info" style="width:100%;margin:0px;" @click.native.prevent="dialogRegisterFormVisible=true">注册</el-button>
      </el-form-item>

      <!-- <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span> password: any</span>
      </div> -->

    </el-form>

    <el-dialog title="用户注册" :visible.sync="dialogRegisterFormVisible">
      <el-form :model="registerForm">
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="registerForm.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="registerForm.userName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="真名" :label-width="formLabelWidth">
          <el-input v-model="registerForm.realName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="registerForm.password" show-password autocomplete="off" />
        </el-form-item>
        <!-- <el-form-item label="活动区域" :label-width="formLabelWidth">
          <el-select v-model="registerForm.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai" />
            <el-option label="区域二" value="beijing" />
          </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRegisterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleRegister">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 4) {
        callback(new Error('The password can not be less than 4 digits'))
      } else {
        callback()
      }
    }
    return {
      dialogRegisterFormVisible: false,
      registerForm: {
        'userName': '',
        'password': '',
        'email': '',
        realName: ''
      },
      loginForm: {
        // role: '0',
        username: 'admin',
        password: '12345'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      formLabelWidth: '120px',
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(async() => {
            this.$router.push({ path: '/' })
            //
            await this.$store.dispatch('user/getInfo')
            this.$goeasy.connect({
              id: this.$store.getters.userInfo.id,
              onSuccess: function() { // 连接成功
                console.log('GoEasy connect successfully.') // 连接成功
              },
              onFailed: function(error) { // 连接失败
                console.log('Failed to connect GoEasy, code:' + error.code + ',error:' + error.content)
              },
              onProgress: function(attempts) { // 连接或自动重连中
                console.log('GoEasy is connecting', attempts)
              }
            })
            this.$goeasy.pubsub.subscribe({
              channel: 'my_channel_' + this.$store.getters.userInfo.id,
              onSuccess: function() {
                console.log('GoEasy 订阅成功')
              },
              onFailed: function(error) {
                console.log('GoEasy 订阅失败: ' + error.content)
              },
              onMessage: function(message) {
                console.log('GoEasy Channel:' + message.channel + ' content:' + message.content)
              }
            })
            //
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          this.$message.error('登录信息校验不通过')
          return false
        }
      })
    },
    async handleRegister() {
      const res = await this.$store.dispatch('user/register', this.registerForm)
      if (res.code === 200) {
        this.$message.success('注册成功，请登录')
        this.dialogRegisterFormVisible = false
      }
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  width: 100%;
  height: 100%;
  background-image: url("../../assets/fmt16.png");
  background-size: cover;
  background-position: center;
  position: relative;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
