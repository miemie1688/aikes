<template>
  <div class="dashboard-container">
    <el-row class="top-image-row">
      <el-col :span="12" class="top-image-col">
        <img src="@/assets/carousel/7.png" class="top-image1" />
      </el-col>
      <el-col :span="12" class="top-image-col">
        <img src="@/assets/carousel/12.png" class="top-image1" />
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
        <el-tab-pane label="固定试卷" name="fixedPaper">
      
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
              <p v-if="filteredFixedPaper.length === 0" style="color:#999;">未找到匹配的固定试卷或者未登录</p>
            </div>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="时段试卷" name="timeLimitPaper">
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
              <p v-if="filteredTimeLimitPaper.length === 0" style="color:#999;">未找到匹配的时段试卷或者未登录</p>
            </div>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="任务中心" name="taskCenter">
          <el-row class="app-item-contain">
           
            <div style="padding-left: 15px">
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
              <p v-else style="color:#999;">未找到匹配的任务或者未登录  </p>
              
            </div>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="资源汇集" name="resourceCollection">
          <el-row class="app-item-contain">
           
            <div class="resource-collection-content" style="padding: 0 15px;">
              
              <h4 class="resource-category-title" style="color: #5ec3fa;">AI:💡</h4>
              <div class="resource-links">
                <div class="resource-item">
                  <span>Gemini 2.5 Pro</span>
                  <a href="https://gemini.google.com/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
                <div class="resource-item">
                  <span>DeepSeek满血版</span>
                  <a href="https://ai.dangbei.com/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
                <div class="resource-item">
                  <span>GPT-5</span>
                  <a href="https://chatgpt.com/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
                <div class="resource-item">
                  <span>更多AI资源</span>
                  <a href="https://www.faxianai.com/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
              </div>

              <h4 class="resource-category-title" style="color: #ffd93b; margin-top: 20px;">社区资源:</h4>
              <div class="resource-links">
                <div class="resource-item">
                  <span>菜鸟教程</span>
                  <a href="https://www.runoob.com/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
                <div class="resource-item">
                  <span>CSDN</span>
                  <a href="https://www.csdn.net/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
                <div class="resource-item">
                  <span>在线工具箱</span>
                  <a href="https://tool.mintimate.cn/" target="_blank" class="external-link-btn">
                    <el-button type="text" class="resource-button">前往体验</el-button>
                  </a>
                </div>
              </div>

             

            </div>
          </el-row>
        </el-tab-pane>

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
      activeTab: 'fixedPaper' // 新增：用于控制当前活动的 Tab，默认显示固定试卷
    }
  },
  async created() {
    this.loading = true
    this.taskLoading = true
    try {
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
      // 试卷封面处理逻辑保持不变
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
      // 试卷封面处理逻辑保持不变
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
      if (!this.searchKeyword.trim()) return true
      return text.toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    },
    // 试卷封面获取逻辑保持不变
    getCoverImage(path, defaultImg) {
      if (path && path.startsWith('@/assets/')) {
        try {
          const imageName = path.split('/').pop()
          return require('@/assets/' + imageName)
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
  --cartoon-orange: #ffb86b;
  --cartoon-bg: #f5f6fa;
  --cartoon-card: #fff6fb;
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
  transition: transform 0.22s, box-shadow 0.22s;
}
.top-image:hover,
.top-image1:hover {
  transform: scale(1.04) rotate(-2deg);
  box-shadow: 0 10px 24px 0 #ffb86b22, 0 4px 18px 0 #845ec212;
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
  padding-left: 50px !important;
  height: 52px !important;
  font-size: 20px;
  color: #543c52;
  box-shadow: 0 2px 12px 0 #d1e6ff;
  transition: all 0.28s cubic-bezier(0.4, 0, 0.2, 1);
}
.custom-search-input .el-input__inner:focus {
  border-color: var(--cartoon-pink);
  background: #fff8fa;
  box-shadow: 0 6px 24px 0 #ffd6f6;
}
.custom-search-input .el-input__icon {
  font-size: 24px;
  color: var(--cartoon-blue);
  left: 16px;
  top: 2px;
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
  font-weight: 700;
  color: #6c3483;
  height: 50px;
  line-height: 50px;
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
  box-shadow: 0 -5px 18px #ff71ce12;
}
.dashboard-tabs .el-tabs__content {
  padding: 0;
  background: var(--cartoon-card);
  border: none;
  border-radius: 0 0 16px 16px;
  box-shadow: 0 4px 18px 0 rgba(80, 150, 255, 0.08),
    0 8px 48px 0 rgba(255, 113, 206, 0.12);
  min-height: 410px;
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

/* 卡片样式（无色边框） */
.el-card {
  border-radius: 18px;
  overflow: hidden;
  box-shadow: 0 4px 18px 0 #ffd93b18, 0 2px 8px 0 #845ec214;
  border: none;
  background: linear-gradient(120deg, #fff6fb 80%, #f6faff 100%);
  transition: all 0.22s;
}
.el-card:hover {
  transform: scale(1.045) rotate(1.5deg);
  box-shadow: 0 10px 24px 0 #ffb86b22, 0 2px 8px #05ffa110;
}
.image {
  width: 100%;
  height: 165px;
  display: block;
  border-radius: 18px 18px 0 0;
  background: #ddefff;
  object-fit: cover;
  border-bottom: none;
}
.el-card > div[style*="padding: 14px"] {
  padding: 15px !important;
}
.el-card > div[style*="padding: 14px"] span {
  font-weight: bold;
  color: #7a3b70;
  font-size: 1.05em;
}
.bottom {
  margin-top: 15px;
  line-height: 18px;
}
.button {
  padding: 6px 14px;
  float: right;
  font-size: 17px;
  font-weight: bold;
  color: var(--cartoon-blue);
  border-radius: 8px;
  background: var(--cartoon-yellow);
  border: none;
  box-shadow: 0 4px 16px 0 #ffb86b33;
  transition: background 0.22s, color 0.22s;
}
.button:hover {
  color: var(--cartoon-pink);
  background: var(--cartoon-green);
  text-decoration: underline;
}

/* 时段试卷时间样式 */
.index-limit-paper-time {
  font-size: 13px;
  color: var(--cartoon-purple);
  margin-top: 6px;
  line-height: 1.5;
  background: #e9faff;
  border-radius: 6px;
  padding: 3px 7px;
  display: inline-block;
}

/* 任务中心折叠面板 */
.el-collapse {
  border: none;
}
.el-collapse-item {
  margin-bottom: 11px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px 0 #05ffa112, 0 1px 4px #ffd93b0c;
  border: none;
  background: linear-gradient(90deg, #ecf1ff 70%, #fff6fb 100%);
}
.el-collapse-item .el-collapse-item__header {
  background-color: #e6f7ff;
  border-bottom: none;
  font-size: 17px;
  font-weight: 700;
  color: #6c3483;
  padding-left: 26px;
  height: 48px;
  line-height: 48px;
  letter-spacing: 0.5px;
}
.el-collapse-item .el-collapse-item__header.is-active {
  background: var(--cartoon-blue);
  color: #fff;
}
.el-collapse-item .el-collapse-item__content {
  padding: 13px 22px 2px;
  background: #fff6fb;
  border-radius: 0 0 12px 12px;
}

/* 任务中心表格 */
.index-task-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 7px;
}
.index-task-table tr {
  background: linear-gradient(90deg, #fff5e6 70%, #e9faff 100%);
  border-radius: 10px;
  transition: background-color 0.18s;
}
.index-task-table tr:hover {
  background: #f6d6ff;
}
.index-task-table td {
  padding: 11px 0;
  vertical-align: middle;
  font-size: 15px;
  color: #5d3483;
  font-weight: 500;
}
.index-task-table-paper {
  font-weight: 700;
  padding-left: 13px;
  color: #2e1f41;
}
.el-tag {
  border-radius: 6px;
  font-weight: bold;
  background: var(--cartoon-green);
  color: #fff;
  border: none;
  font-size: 13px;
  box-shadow: 0 2px 8px var(--cartoon-green);
}
.el-button--text {
  font-size: 15px;
  font-weight: bold;
  color: var(--cartoon-blue);
  border-radius: 7px;
  background: var(--cartoon-yellow);
  margin-left: 4px;
  transition: background 0.18s, color 0.18s;
}
.el-button--text:hover {
  color: var(--cartoon-pink);
  background: var(--cartoon-green);
}

/* 资源汇集模块 */
.resource-collection-content .resource-category-title {
  font-size: 20px;
  font-weight: 800;
  margin-bottom: 12px;
  letter-spacing: 1px;
  padding-top: 10px;
  border-top: 2px dashed #f6f1ff;
}
.resource-links {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 10px;
}
.resource-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 15px;
  background: linear-gradient(135deg, #f0f8ff 0%, #fff8f8 100%);
  border: none;
  border-radius: 10px;
  flex: 1 1 200px;
  max-width: calc(33.333% - 10px);
  box-shadow: 0 2px 8px 0 #ffb86b14;
  transition: all 0.22s;
}
.resource-item span {
  font-size: 16px;
  font-weight: 700;
  color: #3b307a;
}
.resource-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 18px 0 #ffb86b22;
}
.external-link-btn {
  text-decoration: none;
}
.resource-button {
  padding: 5px 10px;
  font-size: 14px;
  font-weight: 600;
  color:#05ffa1;
  background: var(--cartoon-purple);
  border-radius: 6px;
  box-shadow: 0 2px 8px 0 #845ec214;
}
.resource-button:hover {
  background: var(--cartoon-pink);
  color:#5ec3fa;
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