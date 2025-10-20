<template>
  <div class="dashboard-container">
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

    <!-- 搜索框 -->
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

    <!-- 任务中心 -->
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
        <p v-else style="color:#999;">未找到匹配的任务</p>
      </div>
    </el-row>

    <!-- 固定试卷 -->
    <el-row class="app-item-contain">
      <h3 class="index-title-h3">固定试卷</h3>
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
        <p v-if="filteredFixedPaper.length === 0" style="color:#999;">未找到匹配的固定试卷</p>
      </div>
    </el-row>

    <!-- 时段试卷 -->
    <el-row class="app-item-contain">
      <h3 class="index-title-h3" style="border-left: solid 10px rgb(220, 208, 65);">时段试卷</h3>
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
        <p v-if="filteredTimeLimitPaper.length === 0" style="color:#999;">未找到匹配的时段试卷</p>
      </div>
    </el-row>
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
      imgpath: [] // 封面路径列表
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

.search-bar {
 // margin: 30px 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.custom-search-input {
  width: 600px;
  max-width: 80%;
  ::v-deep .el-input__inner {
    border-radius: 25px;
    padding-left: 40px !important;
    border: 2px solid #409EFF;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    transition: all 0.3s;
    height: 50px !important;
    line-height: 50px !important;
    font-size: 16px;
  }
  ::v-deep .el-input__icon {
    line-height: 50px;
    font-size: 18px;
    color: #409EFF;
  }
}
.top-image-row {
  margin-bottom: 10px;
}
.top-image-col {
  padding: 5px;
  box-sizing: border-box;
}
.top-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 6px;
}
.top-image1 {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 6px;
}
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
  width: 100%;
  height: 150px;
  display: block;
  margin: 20px 0 10px 0; /* 左对齐 */
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

/* 左对齐行 */
.el-row-left {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start; /* 左对齐 */
}
</style>
