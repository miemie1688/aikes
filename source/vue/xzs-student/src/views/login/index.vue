<template>
  <div class="layout">
    <div class="video-container">
      <video
        src="@/assets/login4.mp4"
        autoplay
        muted
        loop
        playsinline
        class="login-video"
      ></video>
    </div>

    <div class="login-container dark-theme">

      <div class="lowin lowin-blue dark-form">

        <div class="lowin-brand dark-brand">
          <img src="@/assets/logo2.png" alt="logo" style="margin-top: 12px">
        </div>

        <div class="lowin-wrapper dark-wrapper">
          <div class="lowin-box lowin-login dark-box">
            <div class="lowin-box-inner dark-box-inner">
              <el-form ref="loginForm" :model="loginForm" :rules="loginRules">
                <p class="welcome-title">欢迎登录</p>

                <div class="lowin-group dark-group">
                  <label class="dark-label">用户名 </label>
                  <el-input
                    ref="userName"
                    v-model="loginForm.userName"
                    class="lowin-input dark-input"
                    placeholder="请输入用户名"
                    name="userName"
                    type="text"
                    tabindex="1"
                    auto-complete="on"
                  />
                </div>

                <div class="lowin-group password-group dark-group">
                  <label class="dark-label">密码 <a href="#" class="forgot-link dark-link">忘记密码?</a></label>
                  <el-input
                    class="lowin-input dark-input"
                    :key="passwordType"
                    ref="password"
                    v-model="loginForm.password"
                    :type="passwordType"
                    placeholder="请输入密码"
                    name="password"
                    tabindex="2"
                    auto-complete="on"
                    @keyup.native="checkCapslock"
                    @blur="capsTooltip = false"
                    @keyup.enter.native="handleLogin"
                  />
                </div>

                <el-button
                  :loading="loading"
                  type="text"
                  class="lowin-btn login-btn dark-btn"
                  @click.native.prevent="handleLogin"
                >
                  登录
                </el-button>

                <div class="text-foot dark-text-foot">
                  还没有账号?
                  <router-link to="/register" class="register-link dark-link">
                    注册
                  </router-link>
                </div>
              </el-form>
            </div>
          </div>
        </div>
      </div>

      <div class="account-foot-copyright dark-copyright">
        <span>湖州爱克斯科技</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
// 假设您有一个名为 loginApi 的模块，实际项目中请确保路径正确
import loginApi from '@/api/login'

export default {
  name: 'Login',
  data () {
    const validateUsername = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('用户名不能少于5个字符'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('密码不能少于5个字符'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        userName: '',
        password: '',
        remember: false
      },
      loginRules: {
        userName: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      showDialog: false
    }
  },
  mounted () {
    if (this.loginForm.userName === '') {
      this.$refs.userName.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
  },
  methods: {
    checkCapslock ({ shiftKey, key } = {}) {
      if (key && key.length === 1) {
        // eslint-disable-next-line no-mixed-operators
        if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
          this.capsTooltip = true
        } else {
          this.capsTooltip = false
        }
      }
      if (key === 'CapsLock' && this.capsTooltip === true) {
        this.capsTooltip = false
      }
    },
    showPwd () {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin () {
      let _this = this
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          // 此处使用模拟的 API 调用，实际项目中请确保 loginApi 存在且路径正确
          loginApi.login(this.loginForm).then(function (result) {
            if (result && result.code === 1) {
              _this.setUserName(_this.loginForm.userName)
              _this.$router.push({ path: '/' })
            } else {
              _this.loading = false
              _this.$message.error(result.message)
            }
          }).catch(function () {
            // 模拟网络错误或API调用失败
            _this.loading = false
            _this.$message.error('登录失败，请检查网络或服务器状态。')
          })
        } else {
          return false
        }
      })
    },
    ...mapMutations('user', ['setUserName'])
  }
}
</script>

<style lang="scss">
/* 定义核心科技蓝 */
$tech-blue: #00ffff;
$dark-bg-blue: #0d1a26;

/* 解决 Element UI input 内部样式覆盖问题 (非 scoped) */
.lowin-input.dark-input{
  /* 核心输入框样式，使用科技蓝边框和背景 */
  .el-input__inner{
    background-color: rgba(10, 25, 40, 0.5) !important; /* 略微深蓝的半透明背景 */
    border: 1px solid $tech-blue !important; /* 科技蓝边框 */
    color: #eee !important; /* 浅色文字 */
    padding: 12px 15px !important;
    height: auto !important;
    border-radius: 4px !important;
    font-size: 16px;
    /* 弱发光效果 */
    box-shadow: 0 0 5px rgba(0, 255, 255, 0.3) inset !important;
    transition: all 0.3s ease;
  }
  .el-input__inner:focus {
    border-color: $tech-blue !important;
    box-shadow: 0 0 8px rgba(0, 255, 255, 0.7) inset !important; /* 聚焦时更亮 */
  }
}

/* 调整 Element UI 按钮样式 */
.lowin-btn.dark-btn.el-button--text {
    background-color: $tech-blue !important; /* 按钮背景为科技蓝 */
    color: $dark-bg-blue !important; /* 按钮文字改为深蓝色，与背景对比 */
    font-weight: bold;
    border: 1px solid $tech-blue !important; /* 边框 */
    padding: 15px 20px !important;
    font-size: 18px !important;
    border-radius: 4px !important;
    margin-top: 30px !important;
    /* 蓝色发光效果 */
    box-shadow: 0 0 15px rgba(0, 255, 255, 0.8) !important;
    transition: all 0.3s ease;
}
.lowin-btn.dark-btn.el-button--text:hover {
    background-color: #00e0e0 !important; /* 稍微变暗或变色 */
    opacity: 1 !important;
    box-shadow: 0 0 20px rgba(0, 255, 255, 1) !important; /* 悬停时发光更强 */
}
</style>

<style scoped>
/* ---------------------------------------------------- */
/* 核心布局和深色主题修改 - 科技风格 */
/* ---------------------------------------------------- */
:root {
  --tech-blue: #00ffff;
  --dark-bg: #0d1a26; /* 略微带蓝的深色背景 */
}

.layout{
  display: flex;
  height: 100vh;
  width: 100%;
  margin: 0;
  position: relative;
}
.lowin-btn {
  display: block;
  margin: 20px auto 0;
  text-align: center;
}

/* 视频容器：保持不变 */
.video-container {
  flex: 0 0 50%;
  max-width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

/* 视频元素：保持不变 */
.login-video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 登录容器：使用更深的、带蓝色的背景和微妙的背景发光 */
.login-container.dark-theme {
  flex: 0 0 50%;
  max-width: 50%;
  height: 100%;

  display: flex;
  flex-direction: column;

  position: relative;
  background-color: var(--dark-bg);
  color: #eee;
  /* 增加一个微妙的背景纹理或发光，模拟科技感 */
  background-image: radial-gradient(circle at center, rgba(0, 255, 255, 0.05) 0%, rgba(0, 0, 0, 0.0) 70%);
}

/* lowin 整体风格 */
.lowin.dark-form {
  flex-grow: 1;
  width: 100%;
  height: 100%;
  margin: 0;

  background-color: transparent;
  color: #eee;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

/* 隐藏 lowin-brand 的 logo */
.lowin .lowin-brand.dark-brand {
  display: none;
}

/* 登录框容器：科技 UI 框体效果 */
.lowin .lowin-wrapper.dark-wrapper {
 width: 100%; /* 增加宽度，使其几乎占满父容器 */
 max-width: 1800px; /* 增加最大宽度，如果屏幕够宽，它可以更大 */
 height: 100%;
 margin: 0 auto;
 padding: 40px;

 /* 核心：科技 UI 框体效果 */
 background-color: rgba(10, 25, 40, 0.8); /* 略微深蓝的半透明背景 */
 border: 1px solid var(--tech-blue); /* 科技蓝边框 */
 border-radius: 8px;
 box-shadow:
   0 0 20px rgba(0, 255, 255, 0.5), /* 外部光晕 */
  inset 0 0 10px rgba(0, 255, 255, 0.3); /* 内部微光 */
 backdrop-filter: blur(5px); /* 磨砂玻璃效果 */
}

/* 登录卡片背景透明，阴影移除 */
.lowin .lowin-box.dark-box {
  position: static;
  box-shadow: none;
}
.lowin .lowin-box.dark-box::after {
  display: none;
}
.lowin .lowin-box-inner.dark-box-inner {
  background-color: transparent;
  box-shadow: none;
  padding: 0;
  border-radius: 0;
}

/* 标题/欢迎文本样式 - 科技蓝 */
.lowin-box-inner p.welcome-title {
  font-size: 80px;
  font-weight: 600;
  color: var(--tech-blue);
  margin-bottom: 40px;
  text-align: center;
  width: 100%;
  padding: 0;
  text-shadow: 0 0 10px rgba(0, 255, 255, 0.7); /* 标题发光 */
}

/* 输入框标签和 group 调整 */
.lowin .lowin-box label.dark-label {
  color: var(--tech-blue); /* 标签改为科技蓝 */
  font-weight: 400;
  margin-bottom: 8px;
  font-size: 16px;
  text-shadow: 0 0 5px rgba(0, 255, 255, 0.3); /* 标签微弱发光 */
}
.lowin .lowin-box .lowin-group.dark-group {
  margin-bottom: 25px;
}

/* 忘记密码链接 */
.lowin .lowin-box label a.dark-link {
  color: var(--tech-blue); /* 科技蓝链接 */
  border-bottom: 1px dashed transparent;
  font-weight: 400;
}
.lowin .lowin-box label a.dark-link:hover {
  color: #fff;
  text-decoration: underline;
}

/* 注册链接/页脚文本 */
.lowin .text-foot.dark-text-foot {
  text-align: center;
  padding: 10px;
  font-weight: 400;
  margin-top: 30px;
  color: #888;
  font-size: 14px;
}
.lowin .text-foot.dark-text-foot .dark-link {
  color: var(--tech-blue); /* 科技蓝链接 */
  border-bottom: 1px dashed transparent;
}
.lowin .text-foot.dark-text-foot .dark-link:hover {
  color: #fff;
  text-decoration: underline;
}

/* 版权信息 */
.account-foot-copyright.dark-copyright {
  flex-shrink: 0;
  width: 100%;
  text-align: center;
  color: #4a5d6f; /* 保持低调但带蓝的深灰色 */
  padding: 10px 0 20px 0;
  font-size: 12px;
  text-shadow: none;
}

/* 保留的原 lowin 样式 (用于动画/兼容) */
.lowin * {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.lowin .lowin-wrapper {
  -webkit-transition: all 1s;
  -o-transition: all 1s;
  transition: all 1s;
  -webkit-perspective: 1000px;
  perspective: 1000px;
  position: relative;
}
.lowin .lowin-box {
  width: 100%;
  position: absolute;
  left: 0;
}

/* 响应式样式调整，确保小屏幕下登录容器全屏 */
@media screen and (max-width: 768px) {
  .video-container {
      display: none;
  }
  .login-container.dark-theme {
      flex: 0 0 100%;
      max-width: 100%;
  }
  .lowin .lowin-wrapper.dark-wrapper {
    width: 95%; /* 小屏幕下也增加宽度 */
 padding: 30px;
   max-width: none;
  }
}
</style>
