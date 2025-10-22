<template>
  
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="阶段：" prop="level" required>
        <el-select v-model="form.level" placeholder="阶段" @change="levelChange">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="学科：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="学科">
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id"
                     :label="item.name+' ( '+item.levelName+' )'"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="试卷类型：" prop="paperType" required>
        <el-select v-model="form.paperType" placeholder="试卷类型">
          <el-option v-for="item in paperTypeEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="时间限制：" required v-show="form.paperType===4">
        <el-date-picker v-model="form.limitDateTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="试卷名称：" prop="name" required>
        <el-input v-model="form.name"/>
      </el-form-item>

      <el-form-item label="试卷封面：" prop="coverPath" required>
        <el-select v-model="form.coverPath" placeholder="选择预设封面">
          <el-option v-for="item in coverEnum" :key="item.key" :value="item.value" :label="item.label">
            {{ item.label }}
          </el-option>
        </el-select>
        <div v-if="form.coverPath" style="margin-top: 10px;">
          <span style="font-size: 14px; color: #606266;">封面预览:</span>
          <img :src="form.coverPath" alt="封面预览" class="cover-preview-img"/>
        </div>
      </el-form-item>
      <el-form-item :key="index" :label="'标题'+(index+1)+'：'" required v-for="(titleItem,index) in form.titleItems">
        <el-input v-model="titleItem.name" style="width: 80%"/>
        <el-button type="text" class="link-left" style="margin-left: 20px" size="mini" @click="addQuestion(titleItem)">
          添加题目
        </el-button>
        <el-button type="text" class="link-left" size="mini" @click="form.titleItems.splice(index,1)">删除</el-button>
        <el-card class="exampaper-item-box" v-if="titleItem.questionItems.length!==0">
          <el-form-item :key="questionIndex" :label="'题目'+(questionIndex+1)+'：'"
                        v-for="(questionItem,questionIndex) in titleItem.questionItems" style="margin-bottom: 15px">
            <el-row>
              <el-col :span="23">
                <QuestionShow :qType="questionItem.questionType" :question="questionItem"/>
              </el-col>
              <el-col :span="1">
                <el-button type="text" size="mini" @click="titleItem.questionItems.splice(questionIndex,1)">删除
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-card>
      </el-form-item>

      <el-form-item label="建议时长：" prop="suggestTime" required>
        <el-input v-model="form.suggestTime" placeholder="分钟"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button type="success" @click="addTitle">添加标题</el-button>
      </el-form-item>
    </el-form>

    <el-dialog :visible.sync="questionPage.showDialog" width="70%">
      <el-form :model="questionPage.queryParam" ref="queryForm" :inline="true">
        <el-form-item label="ID：">
          <el-input v-model="questionPage.queryParam.id" clearable></el-input>
        </el-form-item>
        <el-form-item label="题型：">
          <el-select v-model="questionPage.queryParam.questionType" clearable>
            <el-option v-for="item in questionTypeEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryForm">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="questionPage.listLoading" :data="questionPage.tableData"
                @selection-change="handleSelectionChange" border fit highlight-current-row style="width: 100%">
        <el-table-column type="selection" width="35"></el-table-column>
        <el-table-column prop="id" label="Id" width="60px"/>
        <el-table-column prop="questionType" label="题型" :formatter="questionTypeFormatter" width="70px"/>
        <el-table-column prop="shortTitle" label="题干" show-overflow-tooltip/>
      </el-table>
      <pagination v-show="questionPage.total>0" :total="questionPage.total"
                  :page.sync="questionPage.queryParam.pageIndex" :limit.sync="questionPage.queryParam.pageSize"
                  @pagination="search"/>
      <span slot="footer" class="dialog-footer">
        <el-button @click="questionPage.showDialog = false">取 消</el-button>
        <el-button type="primary" @click="confirmQuestionSelect">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import Pagination from '@/components/Pagination'
import QuestionShow from '../question/components/Show'
import examPaperApi from '@/api/examPaper'
import questionApi from '@/api/question'
import coverPathApi from '@/api/admin/cover_path' // 🎯 导入封面路径API
import img1 from '@/assets/img1.png'
import img2 from '@/assets/img2.png'
import img3 from '@/assets/img3.png'
import img4 from '@/assets/img4.png'
export default {
  components: { Pagination, QuestionShow },
  data () {
    return {
      form: {
        id: null,
        level: null,
        subjectId: null,
        paperType: 1,
        limitDateTime: [],
        name: '',
        suggestTime: null,
        titleItems: [],
        coverPath: '' // 🎯 新增封面路径字段
      },
      subjectFilter: null,
      formLoading: false,
      // 🎯 封面选项的枚举数据
      //在此新增即可
      coverEnum: [
        { key: 1, value: img1, label: '作文' },
        { key: 2, value: img2, label: '英语' },
        { key: 3, value: img3, label: '数学' },
         { key: 4, value: img4, label: 'GESP' }
      ],
      rules: {
        level: [
          { required: true, message: '请选择阶段', trigger: 'change' }
        ],
        subjectId: [
          { required: true, message: '请选择学科', trigger: 'change' }
        ],
        paperType: [
          { required: true, message: '请选择试卷类型', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请输入试卷名称', trigger: 'blur' }
        ],
        suggestTime: [
          { required: true, message: '请输入建议时长', trigger: 'blur' }
        ],
        coverPath: [ // 🎯 封面路径验证
          { required: true, message: '请选择试卷封面', trigger: 'change' }
        ]
      },
      questionPage: {
        multipleSelection: [],
        showDialog: false,
        queryParam: {
          id: null,
          questionType: null,
          subjectId: 1,
          pageIndex: 1,
          pageSize: 5
        },
        listLoading: true,
        tableData: [],
        total: 0
      },
      currentTitleItem: null
    }
  },
  created () {
    let id = this.$route.query.id
    let _this = this
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      examPaperApi.select(id).then(re => {
        _this.form = re.response
        
        // 🎯 步骤 1：获取试卷主体信息后，尝试获取封面路径
        coverPathApi.getCoverPathById(id).then(coverRe => {
          if (coverRe.code === 1 && coverRe.response) {
            _this.form.coverPath = coverRe.response.coverPath
          }
        }).catch(() => {
          // 忽略封面路径查询失败的错误，可能该试卷还没有封面记录
        })
        _this.formLoading = false
      })
    }
  },
  methods: {
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          
          // 1. 调用试卷编辑/新增接口 (主流程)
          examPaperApi.edit(this.form).then(re => {
            if (re.code === 1) {
              const examId = re.response.id || _this.form.id // 🎯 关键：获取新增/编辑后的试卷 ID

              // 2. 调用封面路径接口 (辅助流程)
              const coverData = {
                examId: examId,
                coverPath: _this.form.coverPath
              }

              // 判断当前操作是新增 (form.id 为空) 还是编辑 (form.id 存在)
              const coverApiCall = _this.form.id ? coverPathApi.updateCoverPath : coverPathApi.addCoverPath;

              coverApiCall(coverData).then(coverRe => {
                  if (coverRe.code === 200) {
                    _this.$message.success(`${re.message}，封面路径保存成功！`);
                  } else {
                    _this.$message.warning(`${re.message}，封面路径保存失败: ${coverRe.message}`);
                  }
              }).catch(coverError => {
                  _this.$message.error(`${re.message}，但封面路径请求失败！`);
                  // 即使封面保存失败，也继续主流程跳转，用户看到警告
              }).finally(() => {
                  // 3. 页面跳转
                  _this.delCurrentView(_this).then(() => {
                      _this.$router.push('/exam/paper/list')
                  })
              })

            } else {
              _this.$message.error(re.message)
              this.formLoading = false
            }
          }).catch(e => {
            this.formLoading = false
          })
        } else {
          return false
        }
      })
    },
    addTitle () {
      this.form.titleItems.push({
        name: '',
        questionItems: []
      })
    },
    addQuestion (titleItem) {
      this.currentTitleItem = titleItem
      this.questionPage.showDialog = true
      this.search()
    },
    removeTitleItem (titleItem) {
      this.form.titleItems.remove(titleItem)
    },
    removeQuestion (titleItem, questionItem) {
      titleItem.questionItems.remove(questionItem)
    },
    queryForm () {
      this.questionPage.queryParam.pageIndex = 1
      this.search()
    },
    confirmQuestionSelect () {
      let _this = this
      this.questionPage.multipleSelection.forEach(q => {
        questionApi.select(q.id).then(re => {
          _this.currentTitleItem.questionItems.push(re.response)
        })
      })
      this.questionPage.showDialog = false
    },
    levelChange () {
      this.form.subjectId = null
      this.subjectFilter = this.subjects.filter(data => data.level === this.form.level)
    },
    search () {
      this.questionPage.queryParam.subjectId = this.form.subjectId
      this.questionPage.listLoading = true
      questionApi.pageList(this.questionPage.queryParam).then(data => {
        const re = data.response
        this.questionPage.tableData = re.list
        this.questionPage.total = re.total
        this.questionPage.queryParam.pageIndex = re.pageNum
        this.questionPage.listLoading = false
      })
    },
    handleSelectionChange (val) {
      this.questionPage.multipleSelection = val
    },
    questionTypeFormatter (row, column, cellValue, index) {
      return this.enumFormat(this.questionTypeEnum, cellValue)
    },
    subjectFormatter (row, column, cellValue, index) {
      return this.subjectEnumFormat(cellValue)
    },
    resetForm () {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        level: null,
        subjectId: null,
        paperType: 1,
        limitDateTime: [],
        name: '',
        suggestTime: null,
        titleItems: [],
        coverPath: '' // 🎯 重置时清空封面路径
      }
      this.form.id = lastId
    },
    ...mapActions('exam', { initSubject: 'initSubject' }),
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      questionTypeEnum: state => state.exam.question.typeEnum,
      paperTypeEnum: state => state.exam.examPaper.paperTypeEnum,
      levelEnum: state => state.user.levelEnum
    }),
    ...mapState('exam', { subjects: state => state.subjects })
  },
  /**
 * 将 static/img/... 路径转换为 Vue @/assets/... 路径
 * static/img/img2.9309e09c.png -> @/assets/img2.png
 */
convertStaticPathToAssets(path) {
  if (!path) return '';
  if (!path.startsWith('static/img/')) return path;

  const filenameWithHash = path.replace('static/img/', '');
  // 去掉哈希
  const filename = filenameWithHash.replace(/\.[0-9a-f]{6,}\.(\w+)$/, '.$1');
  return `@/assets/${filename}`;
}

}
</script>

<style lang="scss">
.exampaper-item-box {
  .q-title {
    margin: 0 5px;
  }
}

.cover-preview-img {
  width: 100px; 
  height: auto; 
  border: 1px solid #ccc; 
  margin-left: 10px;
  vertical-align: middle;
}
</style>