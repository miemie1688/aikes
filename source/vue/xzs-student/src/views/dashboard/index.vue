<template>
  <div class="dashboard-container">
    <!-- 搜索框 -->

    <!-- 顶部六张静态图片 -->
    <el-row class="top-image-row">
      <el-col :span="12" class="top-image-col">
        <img src="@/assets/carousel/1.png" class="top-image1" />
      </el-col>
      <el-col :span="12" class="top-image-col">
        <img src="@/assets/carousel/2.png" class="top-image1" />
      </el-col>
    </el-row>
    <el-row class="top-image-row">
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/3.png" class="top-image" />
      </el-col>
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/4.png" class="top-image" />
      </el-col>
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/1.png" class="top-image" />
      </el-col>
      <el-col :span="6" class="top-image-col">
        <img src="@/assets/carousel/1.png" class="top-image" />
      </el-col>
    </el-row>

    <!-- 任务中心 -->
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
    <el-row class="app-item-contain">
      <h3 class="index-title-h3" style="border-left: solid 10px #3651d4;">任务中心</h3>
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
                <td class="index-task-table-paper">
                  {{ paperItem.examPaperName }}
                </td>
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

        <p v-else style="color:#999;">未找到匹配的任务</p>
      </div>
    </el-row>

    <!-- 固定试卷 -->
    <el-row class="app-item-contain">
  <h3 class="index-title-h3">固定试卷</h3>
  <div style="padding-left: 15px">
    <el-row :gutter="20">
      <el-col
        :span="4"
        v-for="(item, index) in filteredFixedPaper"
        :key="index"
      >
        <el-card
          :body-style="{ padding: '0px' }"
          v-loading="loading"
          style="margin-bottom: 20px;"
        >
          <img src="@/assets/exam-paper/show1.png" class="image" />
          <div style="padding: 14px;">
            <span>{{ item.name }}</span>
            <div class="bottom clearfix">
              <router-link
                target="_blank"
                :to="{ path: '/do', query: { id: item.id } }"
              >
                <el-button type="text" class="button">开始做题</el-button>
              </router-link>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <p v-if="filteredFixedPaper.length === 0" style="color:#999;">
      未找到匹配的固定试卷
    </p>
  </div>
</el-row>

<el-row class="app-item-contain">
  <h3
    class="index-title-h3"
    style="border-left: solid 10px rgb(220, 208, 65);"
  >
    时段试卷
  </h3>
  <div style="padding-left: 15px">
    <el-row :gutter="20">
      <el-col
        :span="4"
        v-for="(item, index) in filteredTimeLimitPaper"
        :key="index"
      >
        <el-card
          :body-style="{ padding: '0px' }"
          v-loading="loading"
          style="margin-bottom: 20px;"
        >
          <img src="@/assets/exam-paper/show2.png" class="image" />
          <div style="padding: 14px;">
            <span>{{ item.name }}</span>
            <p class="index-limit-paper-time">
              <span>{{ item.startTime }}</span><br />
              <span>{{ item.endTime }}</span>
            </p>
            <div class="bottom clearfix">
              <router-link
                target="_blank"
                :to="{ path: '/do', query: { id: item.id } }"
              >
                <el-button type="text" class="button">开始做题</el-button>
              </router-link>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <p v-if="filteredTimeLimitPaper.length === 0" style="color:#999;">
      未找到匹配的时段试卷
    </p>
  </div>
</el-row>

  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import indexApi from '@/api/dashboard'

export default {
  data () {
    return {
      fixedPaper: [],
      timeLimitPaper: [],
      pushPaper: [],
      taskList: [],
      loading: false,
      taskLoading: false,
      searchKeyword: '' // ✅ 搜索关键字
    }
  },
  created () {
    this.loading = true
    indexApi.index().then((re) => {
      this.fixedPaper = re.response.fixedPaper
      this.timeLimitPaper = re.response.timeLimitPaper
      this.pushPaper = re.response.pushPaper
      this.loading = false
    })

    this.taskLoading = true
    indexApi.task().then((re) => {
      this.taskList = re.response
      this.taskLoading = false
    })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      statusEnum: (state) => state.exam.examPaperAnswer.statusEnum,
      statusTag: (state) => state.exam.examPaperAnswer.statusTag
    }),

    // ✅ 过滤任务
    filteredTaskList () {
      if (!this.searchKeyword.trim()) return this.taskList
      const kw = this.searchKeyword.trim().toLowerCase()
      return this.taskList.filter(
        (task) =>
          task.title.toLowerCase().includes(kw) ||
          task.paperItems.some((p) => p.examPaperName.toLowerCase().includes(kw))
      )
    },

    // ✅ 过滤固定试卷
    filteredFixedPaper () {
      if (!this.searchKeyword.trim()) return this.fixedPaper
      const kw = this.searchKeyword.trim().toLowerCase()
      return this.fixedPaper.filter((p) => p.name.toLowerCase().includes(kw))
    },

    // ✅ 过滤时段试卷
    filteredTimeLimitPaper () {
      if (!this.searchKeyword.trim()) return this.timeLimitPaper
      const kw = this.searchKeyword.trim().toLowerCase()
      return this.timeLimitPaper.filter((p) => p.name.toLowerCase().includes(kw))
    }
  },
  methods: {
    statusTagFormatter (status) {
      return this.enumFormat(this.statusTag, status)
    },
    statusTextFormatter (status) {
      return this.enumFormat(this.statusEnum, status)
    },
    matchesSearch (text) {
      if (!this.searchKeyword.trim()) return true
      return text.toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    }
  }
}
</script>

<style lang="scss" scoped>
/* 搜索框样式 */
.search-bar {
  margin: 30px 0; /* 增大上下外边距，让其更突出 */
  display: flex;
  justify-content: center; /* 搜索框水平居中 */
  align-items: center;
}

/* 增强搜索框输入区域样式 */
.custom-search-input {
  width: 600px; /* 增大搜索框的宽度 */
  max-width: 80%; /* 适配小屏幕时，最大宽度不超过父容器的 80% */

  // 深度选择器用于修改 Element UI 内部样式
  ::v-deep .el-input__inner {
    border-radius: 25px; /* 增加圆角，更美观 */
    padding-left: 40px !important; /* 确保前缀图标和文字有足够空间 */
    border: 2px solid #409EFF; /* 增加显著的蓝色边框 */
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); /* 添加明显阴影 */
    transition: all 0.3s;
    height: 50px !important; /* 增大高度 */
    line-height: 50px !important;
    font-size: 16px; /* 增大字体 */
  }

  ::v-deep .el-input__icon {
    line-height: 50px; /* 确保图标垂直居中 */
    font-size: 18px;
    color: #409EFF;
  }
}

/* 顶部图片区域 */
.top-image-row {
  margin-bottom: 10px;
}

.top-image-col {
  padding: 5px;
  box-sizing: border-box;
}
.top-image  {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 6px;
}
.top-image1  {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 6px;
}

/* 任务与试卷样式 */
.index-title-h3 {
  font-size: 22px;
  font-weight: 400;
  color: #1f2f3d;
  border-left: solid 10px #2ce8b4;
  padding-left: 10px;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
}
.button {
  padding: 0;
  float: right;
}
.image {
  width: 50%;
  height: 80%;
  display: block;
  margin: 20px auto 10px auto;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
</style>
