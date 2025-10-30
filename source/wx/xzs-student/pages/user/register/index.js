const app = getApp()
Page({
  data: {
    levelIndex: 0
  },
  bindLevelChange: function (e) {
    this.setData({
      levelIndex: e.detail.value
    })
  },
  formSubmit: function(e) {
    let _this = this;
    let form = e.detail.value
    if (form.userName == null || form.userName == '') {
      app.message('用户名不能为空', 'error');
      return;
    }
    if (form.password == null || form.password == '') {
      app.message('密码不能为空', 'error');
      return;
    }
    if (form.userLevel == null || form.userLevel == '') {
      app.message('年级不能为空', 'error');
      return;
    }
    _this.setData({
      spinShow: true
    });
    app.formPost('/api/wx/student/user/register', form)
      .then(res => {
        _this.setData({
          spinShow: false
        });
        if (res.code == 1) {
          const userId = res.response; 
          const baseUrl = 'https://aikes.ltd'; 
          
          wx.request({
            // 【已修正】使用反引号 (`) 确保变量被解析
            url: `${baseUrl}/api/student/profiles/default-by-user/${userId}`,
            
            // 【关键】必须指定请求方法为 POST
            method: 'POST', 
            
            // ⚠️ 补充：根据上文推测，这个 POST 请求没有请求体 (data)，因此省略 data 字段
            
            // 【建议】添加 HTTP Header
            header: {
              'content-type': 'application/json',
              // 'Authorization': 'Bearer YOUR_TOKEN' // 如果需要认证
            },
            
            // 【重要】处理异步结果
            success: (result) => {
              if (result.statusCode === 200) {
                wx.showToast({ title: '默认档案已设置', icon: 'success' });
              } else {
                // 处理后端返回的业务错误
                wx.showModal({ title: '操作失败', content: result.data.message || '服务器错误', showCancel: false });
              }
            },
            fail: (error) => {
              wx.showToast({ title: '网络连接失败', icon: 'none' });
            }
          });
        
          wx.reLaunch({
            url: '/pages/user/bind/index',
          });
        } else {
          app.message(res.message, 'error')
        }
      }).catch(e => {
        _this.setData({
          spinShow: false
        });
        app.message(e, 'error')
      })
  }
})