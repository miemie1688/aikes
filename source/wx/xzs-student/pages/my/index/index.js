const app = getApp()
Page({
  data: {
    spinShow: false,
    info: {},
    isLoggedIn: false
  },

  onLoad: function(options) {
    this.loadUserInfo()
  },
  loadUserInfo() {
    let _this = this
    _this.setData({
      spinShow: true
    });
    
    // 检查本地是否有token来判断登录状态
    const token = wx.getStorageSync('token');
    if (!token || token === '') {
      _this.setData({
        isLoggedIn: false,
        spinShow: false
      });
      return;
    }
    
    app.formPost('/api/wx/student/user/current', null).then(res => {
      if (res.code == 1) {
        _this.setData({
          info: res.response,
          isLoggedIn: true
        });
      } else {
        _this.setData({
          isLoggedIn: false
        });
      }
      _this.setData({
        spinShow: false
      });
    }).catch(e => {
      _this.setData({
        spinShow: false,
        isLoggedIn: false
      });
    })
  },
  
  login() {
    wx.navigateTo({
      url: '/pages/user/bind/index',
    });
  },
  logOut() {
    let _this = this
    _this.setData({
      spinShow: true
    });
    app.formPost('/api/wx/student/auth/unBind', null).then(res => {
      if (res.code == 1) {
        wx.setStorageSync('token', '')
        _this.setData({
          isLoggedIn: false,
          info: {}
        });
        app.message('退出成功', 'success')
      }
      _this.setData({
        spinShow: false
      });
    }).catch(e => {
      _this.setData({
        spinShow: false
      });
      app.message(e, 'error')
    })
  }
})