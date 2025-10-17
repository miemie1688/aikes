<template>
  <el-container class="full-height-container">
    <el-header height="61px" class="student-header">
      <div class="header-content">
        <div>
          <a href="/"><img src="@/assets/logo.png" height="50"/></a>
        </div>

        <div class="head-user">
          <el-dropdown trigger="click" placement="bottom">
            <el-badge :is-dot="messageCount!==0" >
              <el-avatar class="el-dropdown-avatar" size="medium" :src="userInfo.imagePath === null ? require('@/assets/avatar.png') : userInfo.imagePath"></el-avatar>
            </el-badge>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="$router.push({path:'/user/index'})">个人中心</el-dropdown-item>
              <el-dropdown-item @click.native="$router.push({path:'/user/message'})">
                <el-badge :value="messageCount" v-if="messageCount!==0">
                  <span>消息中心</span>
                </el-badge>
                <span v-if="messageCount===0">消息中心</span>
              </el-dropdown-item>
              <el-dropdown-item @click.native="logout" divided>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-header>

    <el-container class="main-content-container">
      <el-aside width="130px" class="student-aside">
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
      </el-aside>

      <el-main class="student-main">
        <router-view/>
      </el-main>
    </el-container>

    <div class="foot-copyright">
      <span>湖州爱克斯科技</span>
    </div>
  </el-container>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import loginApi from '@/api/login'
import userApi from '@/api/user'
export default {
  name: 'Layout',
  data () {
    return {
      defaultUrl: '/index',
      userInfo: {
        imagePath: null
      }
    }
  },
  created () {
    let _this = this
    // 确保在组件创建时设置正确的默认激活菜单项
    this.defaultUrl = this.routeSelect(this.$route.path)
    this.getUserMessageInfo()
    userApi.getCurrentUser().then(re => {
      _this.userInfo = re.response
    })
  },
  watch: {
    // 监听路由变化，更新默认激活的菜单项
    $route (to, from) {
      this.defaultUrl = this.routeSelect(to.path)
    }
  },
  methods: {
    routeSelect (path) {
      // 路由选择逻辑：判断当前路径是否属于顶级菜单项，以便高亮显示
      const topPaths = ['/index', '/paper/index', '/record/index', '/question/index']
      
      // 检查当前路径是否以任何一个顶级路径开头（处理子路由）
      for (const p of topPaths) {
        if (path.startsWith(p) && path !== '/') { // '/' 通常应重定向到 '/index'
          return p
        }
      }
      
      // 特殊处理根路径
      if (path === '/') {
          return '/index'
      }

      // 如果没有匹配，则不激活任何菜单项（或返回一个默认值）
      return null
    },
    logout () {
      let _this = this
      loginApi.logout().then(function (result) {
        if (result && result.code === 1) {
          _this.clearLogin()
          _this.$router.push({ path: '/login' })
        }
      })
    },
    ...mapActions('user', ['getUserMessageInfo']),
    ...mapMutations('user', ['clearLogin'])
  },
  computed: {
    ...mapState('user', {
      messageCount: state => state.messageCount
    })
  }
}
</script>

<style lang="scss" scoped>
/* 样式调整以实现全屏左侧导航栏布局 */
.full-height-container {
  min-height: 100vh; /* 确保容器至少占据视口高度 */
  display: flex;
  flex-direction: column; /* 让头部、内容区和底部依次垂直排列 */
}

.student-header {
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
  background-color: #fff;
  z-index: 100;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .08);
  flex-shrink: 0; /* 阻止头部被压缩 */
}

.header-content {
  display: flex;
  justify-content: space-between; /* Logo 和用户信息在两端 */
  align-items: center;
  height: 60px;
}

.main-content-container {
  flex: 1; /* 核心：让这个容器占据剩余所有垂直空间 */
  overflow: hidden; /* 隐藏容器自身的溢出，由内部的 el-main 控制滚动 */
}

.student-aside {
  // 设置侧边栏高度，确保它填满 main-content-container 的剩余高度
  height: 66rem;
  overflow-y: auto; /* 如果侧边栏内容过多，允许垂直滚动 */
  background-color: #545c64; /* 使用菜单的背景色以保持一致性 */
  flex-shrink: 0; /* 阻止侧边栏被压缩 */
}

.el-menu-vertical {
  height: 100%; /* 菜单组件填满侧边栏容器 */
  border-right: none; /* 移除 Element UI 菜单自带的右边框 */
}

.student-main {
  background-color: #f5f7fa; /* 主内容区背景色 */
  padding: 20px;
  overflow-y: auto; /* 允许主内容区域垂直滚动 */
  flex: 1; /* 确保主内容区占据剩余所有水平空间 */
}

.head-user {
  display: flex;
  align-items: center;
}

.foot-copyright {
  text-align: center;
  padding: 10px 0;
  font-size: 14px;
  color: #909399;
  border-top: 1px solid #e6e6e6;
  flex-shrink: 0; /* 阻止底部被压缩 */
}
</style>