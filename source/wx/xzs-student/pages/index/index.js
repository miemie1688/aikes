//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    spinShow: false,
    fixedPaper: [],
    pushPaper: [],
    timeLimitPaper: [],
    taskList: []
  },
  onLoad: function() {
    this.setData({
      spinShow: true
    });
    this.indexLoad()
  },
  onPullDownRefresh() {
    this.setData({
      spinShow: true
    });
    if (!this.loading) {
      this.indexLoad()
    }
  },
  indexLoad: function() {
    let _this = this
    // 重置数据，确保初始状态为空数组
    _this.setData({
      fixedPaper: [],
      timeLimitPaper: [],
      pushPaper: [],
      taskList: []
    });
    
    app.formPost('/api/wx/student/dashboard/index', null).then(res => {
      _this.setData({
        spinShow: false
      });
      wx.stopPullDownRefresh()
      if (res.code === 1) {
        // 确保数据存在，不存在则设置为空数组
        _this.setData({
          fixedPaper: res.response.fixedPaper || [],
          timeLimitPaper: res.response.timeLimitPaper || [],
          pushPaper: res.response.pushPaper || []
        });
      } else {
        // 处理业务错误，确保显示占位符
        _this.setData({
          fixedPaper: [],
          timeLimitPaper: []
        });
        // 可以根据具体错误码显示不同提示
        if (res.code === 401) {
          app.message('请登录后查看内容', 'info');
        }
      }
    }).catch(e => {
      _this.setData({
        spinShow: false,
        // 确保失败时数据为空数组，显示占位符
        fixedPaper: [],
        timeLimitPaper: []
      });
      // 判断是否是登录错误
      if (e && (e.includes('登录') || e.includes('401'))) {
        app.message('请登录后查看内容', 'info');
      } else {
        app.message(e || '加载失败，请重试', 'error');
      }
    })

    app.formPost('/api/wx/student/dashboard/task', null).then(res => {
      _this.setData({
        spinShow: false
      });
      wx.stopPullDownRefresh()
      if (res.code === 1) {
        _this.setData({
          taskList: res.response || [],
        });
      } else {
        _this.setData({
          taskList: []
        });
      }
    }).catch(e => {
      _this.setData({
        spinShow: false,
        taskList: []
      });
      // 任务列表的错误可以不显示，因为主要关注试卷模块
    })
  }
})