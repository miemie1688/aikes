<template>
  <el-container class="full-height-container">
    <el-aside
      width="200px"
      class="student-aside"
    >
      <el-menu
        class="el-menu-vertical"
        mode="vertical"
        :default-active="defaultUrl"
        :router="true"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="/index">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/paper/index">
          <i class="el-icon-document"></i>
          <span slot="title">试卷中心</span>
        </el-menu-item>
        <el-menu-item index="/record/index">
          <i class="el-icon-s-order"></i>
          <span slot="title">考试记录</span>
        </el-menu-item>
        <el-menu-item index="/question/index">
          <i class="el-icon-question"></i>
          <span slot="title">错题本</span>
        </el-menu-item>
      </el-menu>

      <div class="aside-logout-button">
        <el-button
          v-if="isLoggedIn"
          type="warning"
          icon="el-icon-switch-button"
          size="small"
          round
          @click="logout"
        >
          退出登录
        </el-button>
        
        <el-button
          v-else
          type="primary"
          icon="el-icon-user"
          size="small"
          round
          @click="$router.push({ path: '/login' })"
        >
          去登录
        </el-button>
      </div>

      <div class="aside-footer-user">
        
        <el-dropdown trigger="click" placement="right">
          <el-badge :is-dot="messageCount !== 0">
            <el-avatar
              class="el-dropdown-avatar"
              size="medium"
              :src="avatarPath"
            ></el-avatar>
          </el-badge>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="$router.push({ path: '/user/index' })">个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push({ path: '/user/message' })">
              <el-badge :value="messageCount" v-if="messageCount !== 0">
                <span>消息中心</span>
              </el-badge>
              <span v-if="messageCount === 0">消息中心</span>
            </el-dropdown-item>
            
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <div class="aside-footer-logo">
        <a href="/">
          <img
            src="@/assets/logo3.png"
            class="logo-img"
            alt="Logo"
          />
        </a>
      </div>
    </el-aside>

    <el-container class="main-content-container">
      <el-main class="student-main">
        <router-view />
      </el-main>
    </el-container>

    <div class="foot-copyright">
      
      <span>@湖州爱克斯科技</span>
    </div>
    
  </el-container>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import loginApi from '@/api/login'
import userApi from '@/api/user'
// 引入试卷封面路径 API，用于判断登录状态
import { getAllCoverPaths } from '@/api/cover_path'

export default {
  name: 'Layout',
  data () {
    return {
      defaultUrl: '/index',
      userInfo: {
        imagePath: null
      },
      // 【新增】显式追踪登录状态
      userIsLoggedIn: false
    }
  },
  created () {
    this.defaultUrl = this.routeSelect(this.$route.path)
    this.getUserMessageInfo()
    
    // 【修改】尝试调用一个受保护的 API（getAllCoverPaths）来判断是否登录
    getAllCoverPaths().then(() => {
      // 成功：认为用户已登录
      this.userIsLoggedIn = true
      
      // 登录成功后，再获取用户信息（用于头像等）
      userApi.getCurrentUser().then(re => {
        this.userInfo = re.response
      }).catch(() => {
        // 如果获取用户信息失败，重置头像信息
        this.userInfo = { imagePath: null }
      })
      
    }).catch(() => {
      // 失败（如 401 未授权）：认为用户未登录
      this.userIsLoggedIn = false
      this.userInfo = { imagePath: null }
    })
  },
  watch: {
    $route (to) {
      this.defaultUrl = this.routeSelect(to.path)
    }
  },
  methods: {
    routeSelect (path) {
      const topPaths = ['/index', '/paper/index', '/record/index', '/question/index', '/user/index']
      for (const p of topPaths) {
        if (path.startsWith(p) && p !== '/index' && path !== '/') {
          return p
        }
      }
      if (path === '/' || path === '/index' || path.startsWith('/index/')) {
        return '/index'
      }
      return null
    },
    logout () {
      loginApi.logout().then(() => {
        this.clearLogin()
        // 退出登录后，重置登录状态和用户信息
        this.userIsLoggedIn = false
        this.userInfo = { imagePath: null }
        this.$router.push({ path: '/login' })
      })
    },
    ...mapActions('user', ['getUserMessageInfo']),
    ...mapMutations('user', ['clearLogin'])
  },
  computed: {
    ...mapState('user', {
      messageCount: state => state.messageCount
    }),
    
    // 【修改】直接使用状态变量判断是否登录
    isLoggedIn () {
      return this.userIsLoggedIn
    },
    
    avatarPath () {
      return this.userInfo && this.userInfo.imagePath
        ? this.userInfo.imagePath
        : require('@/assets/avatar.png')
    }
  }
}
</script>

<style lang="scss" scoped>
.full-height-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  position: relative;
}

/* 固定侧边栏 */
.student-aside {
  position: fixed;
  left: 0;
  top: 0;
  width: 140px !important;
  height: 100vh;
  background-color: #545c64;
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
  display: flex;
  flex-direction: column;
  z-index: 100;
  overflow-y: hidden; /* 不让侧边栏内容整体滚动 */
}

/* 主内容区域，让出侧边栏宽度 */
.main-content-container {
  flex: 1;
  margin-left: 130px; /* 与侧边栏宽度保持一致 */
  display: flex;
  min-height: 100vh;
  flex-direction: column;
}

/* 菜单组件 (可滚动) */
.el-menu-vertical {
  flex-grow: 1;
  border-right: none;
  overflow-y: auto;
  padding-top: 10px;
  &::-webkit-scrollbar {
    width: 0px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #6a737d;
    border-radius: 3px;
  }
  &::-webkit-scrollbar-track {
    background: #545c64;
  }
  ::v-deep .el-menu-item {
    .el-icon-arrow-down {
      display: none !important;
    }
    padding-right: 20px !important;
  }
}

/* **新增：独立退出/登录按钮样式** */
.aside-logout-button {
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-shrink: 0; /* 确保不被菜单挤压 */
  padding: 10px 0;
}

/* 用户头像区域 */
.aside-footer-user {
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  padding: 10px 0;
  border-top: 1px solid #4a515a;
}
.el-dropdown-avatar {
  cursor: pointer;
}

/* Logo 区域 */
.aside-footer-logo {
  height: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  padding-bottom: 5px;
  background-color: #434a50;
}
.logo-img {
  height: 50px;
  width: auto;
  vertical-align: middle;
}

/* 右侧主内容区 */
.student-main {
  background-color: #f5f7fa;
  padding: 20px;
  overflow-y: auto;
  flex: 1;
}

/* 底部版权信息 */
.foot-copyright {
  text-align: center;
  padding: 10px 0;
  font-size: 14px;
  color: #909399;
  border-top: 1px solid #e6e6e6;
  flex-shrink: 0;
  background: #fff;
  margin-left: 200px; /* 与主内容区域对齐 */
}
</style>