<template>
  <div class="dashboard-container">
    <el-row class="top-image-row">
      <el-col :span="12" class="top-image-col">
        <img src="@/assets/carousel/7.png" class="top-image1" />
      </el-col>
      <el-col :span="12" class="top-image-col">
        <a href="https://aikes.ltd/" target="_blank" class="top-image-link"> 
          <img src="@/assets/carousel/img66.png" class="top-image1" />
        </a>
      </el-col>
    </el-row>
    <el-row class="top-image-row">
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/9.png" class="top-image" />
      </el-col>
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/10.png" class="top-image" />
      </el-col>
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/8.png" class="top-image" />
      </el-col>
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/11.png" class="top-image" />
      </el-col>
    </el-row>

    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索任务或试卷"
        prefix-icon="el-icon-search"
        clearable
        size="large"
        class="custom-search-input"
      />
    </div>

    <div class="tab-container">
      <el-tabs v-model="activeTab" type="card" class="dashboard-tabs">
     <el-tab-pane label="💡思维矩阵" name="resourceCollection">
  <el-row class="app-item-contain">
    <div class="resource-collection-content">
      <h4 class="resource-category-title" style="color: var(--cartoon-pink); margin-top: 20px;">💡 AI & 机器人:</h4>
      <div class="resource-links">
        
        <div class="resource-item">
          <a href="https://www.deepseek.com/" target="_blank" class="resource-link-item">
            <span>DeepSeek</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.kimi.com/" target="_blank" class="resource-link-item">
            <span>💬 Kimi Chat AI</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.doubao.com/" target="_blank" class="resource-link-item">
            <span>💬 豆包 AI</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://gemini.google.com/" target="_blank" class="resource-link-item">
            <span>🚀 Gemini 2.5 Pro</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://chatgpt.com/" target="_blank" class="resource-link-item">
            <span>GPT-5 (AI)</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.unifolm.com/" target="_blank" class="resource-link-item">
            <span>🤖宇树科技开源社区</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.faxianai.com/" target="_blank" class="resource-link-item">
            <span>更多AI资源发现</span>
          </a>
        </div>
      </div>
      
      <h4 class="resource-category-title" style="color: var(--cartoon-blue);">🤖 竞赛 & 编程社区:</h4>
      <div class="resource-links">
        
        <div class="resource-item">
          <a href="https://aikes.ltd/" target="_blank" class="resource-link-item">
            <span>🐬爱克思算法</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.luogu.com.cn/" target="_blank" class="resource-link-item">
            <span>💻 洛谷 (Luogu)</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="http://ybt.ssoier.cn:8088/index.php" target="_blank" class="resource-link-item">
            <span>🐍 C++信息学奥数一本通</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://py.ssoier.cn/" target="_blank" class="resource-link-item">
            <span>🐍 Python 信息学奥数一本通</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://gesp.ccf.org.cn/" target="_blank" class="resource-link-item">
            <span>🏆 GESP C++等级考试</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.noi.cn/" target="_blank" class="resource-link-item">
            <span>🥇 NOI (信息学奥赛)</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.qceit.org.cn/bos/default.html?d=5443.820512563547" target="_blank" class="resource-link-item">
            <span>🖥️ 中国电子协会考评中心</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.csdn.net/" target="_blank" class="resource-link-item">
            <span>🌐 CSDN 开发者社区</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://www.runoob.com/" target="_blank" class="resource-link-item">
            <span>📘 菜鸟教程 (Runoob)</span>
          </a>
        </div>
      </div>

      <h4 class="resource-category-title" style="color: var(--cartoon-yellow); margin-top: 20px;">🔬 自然科学 & 实验平台:</h4>
      <div class="resource-links">
        
        <div class="resource-item">
          <a href="https://phet.colorado.edu/" target="_blank" class="resource-link-item">
            <span>🧪科学仿真实验</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://scratch.mit.edu/" target="_blank" class="resource-link-item">
            <span>🐾 Scratch 小猫编程</span>
          </a>
        </div>
        
        <div class="resource-item">
          <a href="https://tool.mintimate.cn/" target="_blank" class="resource-link-item">
            <span>⚙️ 在线工具箱</span>
          </a>
        </div>
      </div>
      
      <h4 class="resource-category-title" style="color: var(--cartoon-pink); margin-top: 20px;">📕人文社区:</h4>
      <div class="resource-links">
        <div class="resource-item">
          <a href="https://wordforest.cn/" target="_blank" class="resource-link-item">
            <span>☀英语学习乐园</span>
          </a>
        </div>
      </div>
    </div>
  </el-row>
</el-tab-pane>
<el-tab-pane label="🏆思维实验室" name="fixedPaper">
          <el-row class="app-item-contain">
            <div style="padding-left: 15px">
              <el-row :gutter="20" class="el-row-left">
                <el-col
                  :span="4"
                  v-for="(item, index) in filteredFixedPaper"
                  :key="index"
                >
                  <el-card :body-style="{ padding: '0px' }" v-loading="loading" style="margin-bottom: 20px;">
                    <img 
                      :src="item.coverPath 
                        ? getCoverImage(item.coverPath, require('@/assets/exam-paper/show2.png'))
                        : require('@/assets/exam-paper/show1.png')" 
                      class="image" 
                    />
                    <div style="padding: 14px;">
                      <span>{{ item.name }}</span>
                      <div class="bottom clearfix">
                        <router-link target="_blank" :to="{ path: '/do', query: { id: item.id } }">
                          <el-button type="text" class="button">开始做题</el-button>
                        </router-link>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              <p v-if="filteredFixedPaper.length === 0" style="color:#999; padding-left: 5px;">未找到匹配的固定试卷或者未登录</p>
            </div>
          </el-row>
        </el-tab-pane>
        <!-- <el-tab-pane label="定时测评卷" name="timeLimitPaper">
          <el-row class="app-item-contain">
            <div style="padding-left: 15px">
              <el-row :gutter="20" class="el-row-left">
                <el-col :span="4" v-for="(item, index) in filteredTimeLimitPaper" :key="index">
                  <el-card :body-style="{ padding: '0px' }" v-loading="loading" style="margin-bottom: 20px;">
                    <img 
                      :src="item.coverPath 
                        ? getCoverImage(item.coverPath, require('@/assets/exam-paper/show2.png'))
                        : require('@/assets/exam-paper/show2.png')" 
                      class="image" 
                    />
                    <div style="padding: 14px;">
                      <span>{{ item.name }}</span>
                      <p class="index-limit-paper-time">
                        <span>{{ item.startTime }}</span><br />
                        <span>{{ item.endTime }}</span>
                      </p>
                      <div class="bottom clearfix">
                        <router-link target="_blank" :to="{ path: '/do', query: { id: item.id } }">
                          <el-button type="text" class="button">开始做题</el-button>
                        </router-link>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              <p v-if="filteredTimeLimitPaper.length === 0" style="color:#999; padding-left: 5px;">未找到匹配的时段试卷或者未登录</p>
            </div>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="任务中心" name="taskCenter">
          <el-row class="app-item-contain">
            <div style="padding-left: 15px; padding-right: 15px;">
              <el-collapse v-loading="taskLoading" accordion v-if="filteredTaskList.length !== 0">
                <el-collapse-item
                  v-for="taskItem in filteredTaskList"
                  :title="taskItem.title"
                  :name="taskItem.id"
                  :key="taskItem.id"
                >
                  <table class="index-task-table">
                    <tr
                      v-for="paperItem in taskItem.paperItems"
                      :key="paperItem.examPaperId"
                      v-show="matchesSearch(paperItem.examPaperName)"
                    >
                      <td class="index-task-table-paper">{{ paperItem.examPaperName }}</td>
                      <td width="70px">
                        <el-tag
                          :type="statusTagFormatter(paperItem.status)"
                          v-if="paperItem.status !== null"
                          size="mini"
                        >
                          {{ statusTextFormatter(paperItem.status) }}
                        </el-tag>
                      </td>
                      <td width="80px">
                        <router-link
                          target="_blank"
                          :to="{ path: '/do', query: { id: paperItem.examPaperId } }"
                          v-if="paperItem.status === null"
                        >
                          <el-button type="text" size="small">开始答题</el-button>
                        </router-link>

                        <router-link
                          target="_blank"
                          :to="{ path: '/edit', query: { id: paperItem.examPaperAnswerId } }"
                          v-else-if="paperItem.status === 1"
                        >
                          <el-button type="text" size="small">批改试卷</el-button>
                        </router-link>

                        <router-link
                          target="_blank"
                          :to="{ path: '/read', query: { id: paperItem.examPaperAnswerId } }"
                          v-else-if="paperItem.status === 2"
                        >
                          <el-button type="text" size="small">查看试卷</el-button>
                        </router-link>
                      </td>
                    </tr>
                  </table>
                </el-collapse-item>
              </el-collapse>
              <p v-else style="color:#999; padding-left: 5px;">未找到匹配的任务或者未登录 </p>
              
            </div>
          </el-row>
        </el-tab-pane> -->
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import indexApi from '@/api/dashboard'
import { getAllCoverPaths } from '@/api/cover_path'

export default {
  data() {
    return {
      fixedPaper: [],
      timeLimitPaper: [],
      pushPaper: [],
      taskList: [],
      loading: false,
      taskLoading: false,
      searchKeyword: '',
      imgpath: [], // 封面路径列表
      activeTab: 'resourceCollection' // 默认显示资源汇集
    }
  },
  async created() {
    this.loading = true
    this.taskLoading = true
    try {
      // 模拟 indexApi.index() 和 indexApi.task() 的数据获取，实际应用中请确保 API 可用
      const re = await indexApi.index()
      const resData = re.response || {}
      this.fixedPaper = resData.fixedPaper || []
      this.timeLimitPaper = resData.timeLimitPaper || []
      this.pushPaper = resData.pushPaper || []
      this.loading = false

      const taskRes = await indexApi.task()
      this.taskList = taskRes.response || []
      this.taskLoading = false

      const coverRes = await getAllCoverPaths()
      if (Array.isArray(coverRes)) {
        this.imgpath = coverRes
      } else if (coverRes?.data && Array.isArray(coverRes.data)) {
        this.imgpath = coverRes.data
      }
    } catch (err) {
      console.error('初始化失败', err)
      this.loading = false
      this.taskLoading = false
    }
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      statusEnum: (state) => state.exam.examPaperAnswer.statusEnum,
      statusTag: (state) => state.exam.examPaperAnswer.statusTag
    }),

    filteredTaskList() {
      if (!this.searchKeyword.trim()) return this.taskList
      const kw = this.searchKeyword.trim().toLowerCase()
      // 过滤任务标题或任务内的试卷名称
      return this.taskList.filter(
        (task) =>
          task.title.toLowerCase().includes(kw) ||
          task.paperItems.some((p) => p.examPaperName.toLowerCase().includes(kw))
      )
    },
    filteredFixedPaper() {
      let papers = !this.searchKeyword.trim()
        ? this.fixedPaper
        : this.fixedPaper.filter((p) => 
            p.name.toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
          )
      // 试卷封面处理逻辑
      return papers.map((paper) => {
        const cover = this.imgpath.find((img) => img.examId === paper.id)
        return { ...paper, coverPath: cover ? cover.coverPath : '' }
      })
    },
    filteredTimeLimitPaper() {
      let papers = !this.searchKeyword.trim()
        ? this.timeLimitPaper
        : this.timeLimitPaper.filter(p =>
            p.name.toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
          )
      // 试卷封面处理逻辑
      return papers.map(paper => {
        const cover = this.imgpath.find(img => img.examId === paper.id)
        return { ...paper, coverPath: cover ? cover.coverPath : '' }
      })
    }
  },
  methods: {
    statusTagFormatter(status) {
      return this.enumFormat(this.statusTag, status)
    },
    statusTextFormatter(status) {
      return this.enumFormat(this.statusEnum, status)
    },
    matchesSearch(text) {
      // 用于任务中心内部表格行过滤
      if (!this.searchKeyword.trim()) return true
      return text.toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    },
    // 试卷封面获取逻辑保持不变
    getCoverImage(path, defaultImg) {
      if (path && path.startsWith('@/assets/')) {
        try {
          // 注意：实际生产环境可能需要更复杂的路径处理
          // 这里的假设是 path 类似于 '@/assets/exam-paper/show1.png'
          const parts = path.split('@/assets/')
          if (parts.length > 1) {
              // 尝试动态 require 相对路径
              // 这是一个在 Vue CLI/Webpack 环境中加载图片的常见模式
            return require('@/assets/' + parts[1])
          }
          return defaultImg
        } catch (e) {
          return defaultImg
        }
      }
      return defaultImg
    }
  }
}
</script>

<style lang="scss" scoped>

// 定义卡通科技风格的颜色变量
// 颜色变量（卡通风）
/* 卡通科技风格（简约）Dashboard页面通用样式 */

:root {
  --cartoon-blue: #5ec3fa;
  --cartoon-yellow: #ffd93b;
  --cartoon-pink: #ff71ce;
  --cartoon-green: #05ffa1;
  --cartoon-purple: #845ec2;
  --cartoon-orange: #ffb86b; // 橙色
  --cartoon-bg: #f5f6fa;
  --cartoon-card: #fff6fb;
}

.resource-link-item {
    text-decoration: none; /* 移除默认下划线 */
    color: inherit; /* 继承父元素的颜色，保持和普通文字一致 */
    cursor: pointer; /* 确保鼠标光标仍是手形 */
    display: block; /* 使整个区域可点击 */
}

.top-image-link {
  display: block; 
  height: 100%;
  text-decoration: none; 
}
/* 页面背景与整体布局 */
.dashboard-container {
  padding: 0;
  background: repeating-linear-gradient(
      135deg,
      #f9f9fa 0 15px,
      #f5f6fa 0 30px
    ),
    var(--cartoon-bg);
  min-height: 100vh;
  border-radius: 24px;
  box-shadow: 0 0 0 4px #fff1fb, 0 10px 42px 0 rgba(133, 94, 194, 0.11);
}

/* 顶部图片区域 */
.top-image-row {
  margin-bottom: 12px;
}
.top-image-col {
  padding: 7px;
  box-sizing: border-box;
}
.top-image,
.top-image1 {
  width: 100%;
  object-fit: cover;
  border-radius: 17px;
  border: none;
  box-shadow: 0 6px 18px 0 #ffd93b28, 0 2px 8px 0 #ff71ce14;
  background: #eef8ff;
  transition: transform 0.3s, box-shadow 0.3s; /* 略微加长过渡时间 */
}
.top-image:hover,
.top-image1:hover {
  transform: scale(1.05) rotate(-1.5deg); /* 略微更夸张的缩放和旋转 */
  box-shadow: 0 12px 28px 0 #ffb86b33, 0 5px 20px 0 #845ec220;
}
.top-image {
  height: 135px;
}
.top-image1 {
  height: 175px;
}

/* 搜索栏 */
.search-bar {
  margin: 30px 0 36px 16px;
  display: flex;
  justify-content: flex-start;
}
.custom-search-input {
  width: 360px;
  max-width: 92%;
}
.custom-search-input .el-input__inner {
  border-radius: 40px;
  border: 3px solid var(--cartoon-blue);
  background: #fff;
  padding-left: 55px !important; /* 增大左边距给图标留白 */
  height: 55px !important; /* 略微增加高度 */
  font-size: 20px;
  color: #543c52;
  box-shadow: 0 3px 15px 0 #d1e6ff;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
.custom-search-input .el-input__inner:focus {
  border-color: var(--cartoon-pink);
  background: #fff;
  box-shadow: 0 8px 30px 0 #ff71ce30;
}
.custom-search-input .el-input__icon {
  font-size: 26px; /* 增大图标尺寸 */
  color: var(--cartoon-purple); /* 使用紫色，更具科技感 */
  left: 18px;
  top: 1px;
}

/* Tabs 简约卡通风格 */
.tab-container {
  margin-top: 32px;
}
.dashboard-tabs .el-tabs__header {
  margin-bottom: 0;
  border-bottom: none;
}
.dashboard-tabs .el-tabs__nav {
  background: var(--cartoon-card);
  border-radius: 16px 16px 0 0;
  box-shadow: 0 2px 8px 0 #ffd93b15;
}
.dashboard-tabs .el-tabs__item {
  font-size: 20px;
  font-weight: 800; /* 略微加粗 */
  color: #6c3483;
  height: 55px; /* 略高 */
  line-height: 55px;
  border: none;
  border-radius: 12px 12px 0 0;
  margin-right: 0;
  background: linear-gradient(90deg, #ecf1ff 0%, #fff6fb 100%);
  letter-spacing: 1px;
  box-shadow: none;
  transition: color 0.2s, background 0.22s;
}
.dashboard-tabs .el-tabs__item.is-active {
  color: var(--cartoon-pink);
  background: #fff;
  box-shadow: 0 -6px 20px #ff71ce20;
}
.dashboard-tabs .el-tabs__content {
  padding: 20px 0; /* 增加内容区上下内边距 */
  background: var(--cartoon-card);
  border: none;
  border-radius: 0 0 16px 16px;
  box-shadow: 0 5px 20px 0 rgba(80, 150, 255, 0.1),
    0 10px 50px 0 rgba(255, 113, 206, 0.15);
  min-height: 450px;
}
.app-item-contain {
  margin: 0 !important;
  padding: 0 !important;
  width: 100%;
}
.el-row-left {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-left: 0 !important;
  padding-left: 0 !important;
}

/* 卡片样式（思维实验室/定时测评卷） */
.el-card {
  border-radius: 20px; /* 略大圆角 */
  overflow: hidden;
  box-shadow: 0 6px 20px 0 #ffd93b25, 0 3px 10px 0 #845ec218;
  border: 3px solid transparent; 
  background: var(--cartoon-card); 
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
}
.el-card:hover {
  transform: scale(1.03) rotate(0); 
  box-shadow: 0 15px 30px 0 #ffb86b33, 0 4px 12px #05ffa120;
  border: 3px solid var(--cartoon-pink); /* 悬停时添加亮色边框 */
}
.image {
  width: 100%;
  height: 175px; /* 略微增加图片高度 */
  display: block;
  border-radius: 17px 17px 0 0;
  background: #ddefff;
  object-fit: cover;
  border-bottom: none;
}
.el-card > div[style*="padding: 14px"] {
  padding: 15px !important;
}
.el-card > div[style*="padding: 14px"] span {
  font-weight: 800; /* 标题加粗 */
  color: #4b306e; /* 颜色更深 */
  font-size: 1.1em;
}
.bottom {
  margin-top: 15px;
  line-height: 18px;
}
/* 思维实验室/定时测评卷 - 开始做题按钮 */
.button {
  padding: 8px 16px; /* 增大按钮尺寸 */
  float: right;
  font-size: 17px;
  font-weight: bold;
  color: var(--cartoon-blue); /* 调整：常态青色 */
  border-radius: 10px; /* 增大圆角 */
  background: var(--cartoon-yellow); /* 按钮底色改为黄色 */
  border: none;
  box-shadow: 0 4px 16px 0 #ffd93b50; 
  transition: background 0.22s, box-shadow 0.22s, color 0.22s;
}
.button:hover {
  color: var(--cartoon-orange); /* 调整：悬停橙色 */
  background: var(--cartoon-pink); /* 悬停时背景变粉色 */
  text-decoration: none;
  box-shadow: 0 6px 20px 0 #ff71ce55;
}

/* 时段试卷时间样式 */
.index-limit-paper-time {
  font-size: 14px;
  color: #000; /* 调整：黑色 */
  margin-top: 8px;
  line-height: 1.5;
  background: var(--cartoon-orange); /* 保持橙色背景，更醒目 */
  border-radius: 8px;
  padding: 4px 8px;
  display: inline-block;
  font-weight: 600;
}

/* 任务中心折叠面板 */
.el-collapse {
  border: none;
}
.el-collapse-item {
  margin-bottom: 15px; /* 增大间隔 */
  border-radius: 15px; /* 增大圆角 */
  overflow: hidden;
  box-shadow: 0 3px 12px 0 #05ffa118, 0 1px 6px #ffd93b12;
  border: 1px solid #e0f0ff; /* 增加一个浅边框 */
  background: linear-gradient(90deg, #f6faff 70%, #fff6fb 100%); 
}
.el-collapse-item .el-collapse-item__header {
  background-color: #fff; /* 头部背景改为白色 */
  border-bottom: none;
  font-size: 18px;
  font-weight: 800;
  color: #4b306e;
  padding-left: 30px;
  height: 55px; /* 略高 */
  line-height: 55px;
  letter-spacing: 0.5px;
  border-radius: 15px;
}
.el-collapse-item .el-collapse-item__header.is-active {
  background: var(--cartoon-blue); /* 激活时使用蓝色 */
  color: #fff;
  border-radius: 15px 15px 0 0;
}
.el-collapse-item .el-collapse-item__content {
  padding: 15px 30px 5px;
  background: #fff; /* 内容区使用白色背景 */
  border-radius: 0 0 15px 15px;
}

/* 任务中心表格 */
.index-task-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 10px; /* 增大行间距 */
}
.index-task-table tr {
  background: linear-gradient(90deg, #f9f9ff 70%, #fff 100%); /* 表格行背景 */
  border-radius: 12px;
  transition: background-color 0.18s;
  overflow: hidden; 
}
.index-task-table tr:hover {
  background: #e0faff; /* 悬停时变浅蓝 */
}
.index-task-table td {
  padding: 13px 0;
  vertical-align: middle;
  font-size: 16px;
  color: #5d3483;
  font-weight: 500;
}
.index-task-table-paper {
  font-weight: 800;
  padding-left: 15px;
  color: #2e1f41;
}
.el-tag {
  border-radius: 8px;
  font-weight: bold;
  color: #fff;
  border: none;
  font-size: 14px;
  box-shadow: 0 2px 8px var(--cartoon-green);
}

/* 任务中心操作按钮 */
.el-button--text {
  font-size: 16px;
  font-weight: bold;
  color: var(--cartoon-blue); /* 调整：常态青色 */
  border-radius: 8px;
  background: var(--cartoon-yellow); /* 按钮底色改为黄色 */
  margin-left: 4px;
  padding: 6px 10px;
  transition: background 0.18s, color 0.18s;
}
.el-button--text:hover {
  color: var(--cartoon-orange); /* 调整：悬停橙色 */
  background: var(--cartoon-pink); /* 悬停变粉色 */
}

/* 资源汇集模块 */
.resource-collection-content {
  padding: 15px 25px !important; 
}
.resource-collection-content .resource-category-title {
  font-size: 22px; 
  font-weight: 800;
  margin-bottom: 18px;
  letter-spacing: 1px;
  padding-top: 15px;
  padding-bottom: 5px;
  border-bottom: 2px dotted #845ec230; 
}
.resource-links {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
}
.resource-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px 20px;
  background: linear-gradient(135deg, #fff 0%, #e9faff 100%);
  border: 2px solid var(--cartoon-blue); 
  border-radius: 15px;
  flex: 1 1 220px;
  max-width: calc(33.333% - 15px);
  box-shadow: 0 4px 12px 0 #5ec3fa20;
  transition: all 0.3s;
}
.resource-item span {
  font-size: 17px;
  font-weight: 700;
  color: #3b307a;
}
.resource-item:hover {
  transform: scale(1.02) translateY(-3px); 
  box-shadow: 0 8px 20px 0 #ff71ce30;
  border-color: var(--cartoon-pink); 
}
.external-link-btn {
  text-decoration: none;
}
/* 资源汇集 - 前往体验按钮 */
.resource-button {
  padding: 8px 16px;
  font-size: 15px;
  font-weight: 700;
  color: var(--cartoon-blue); /* 调整：常态青色 */
  background: var(--cartoon-yellow);
  border-radius: 8px;
  box-shadow: 0 3px 10px 0 #ffd93b30;
  transition: background 0.22s, color 0.22s;
}
.resource-button:hover {
  background: var(--cartoon-pink);
  color: var(--cartoon-orange); /* 调整：悬停橙色 */
  text-decoration: none;
}

/* 清除浮动 */
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
</style>