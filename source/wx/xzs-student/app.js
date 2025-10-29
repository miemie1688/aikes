const {
  $Message
} = require('/component/iView/base/index');
const mtjwxsdk = require('./utils/mtj-wx-sdk.js');

App({
  globalData: {
    baseAPI: "http://localhost:8888",
    pageSize: 20
  },
  onLaunch: function() {
    let _this = this
    let token = wx.getStorageSync('token')
    if (null == token || token == '') {
      wx.login({
        success(wxres) {
          if (wxres.code) {
            _this.formPost('/api/wx/student/auth/checkBind', {
              "code": wxres.code
            }).then(res => {
              if (res.code == 1) {
                wx.setStorageSync('token', res.response)
                // 移除强制跳转首页，避免全局拦截
                // wx.reLaunch({
                //   url: '/pages/index/index',
                // });
              } else if (res.code == 2) {
                // 移除强制跳转登录页，避免全局拦截
                // wx.reLaunch({
                //   url: '/pages/user/bind/index',
                // });
                _this.message('请先登录', 'warning')
              } else {
                _this.message(res.message, 'error')
              }
            }).catch(e => {
              _this.message(e, 'error')
            })
          } else {
            _this.message(res.errMsg, 'error')
          }
        }
      })
    }
  },
  message: function(content, type) {
    $Message({
      content: content,
      type: type
    });
  },
  formPost: function(url, data) {
    let _this = this
    return new Promise(function(resolve, reject) {
      wx.showNavigationBarLoading();
      wx.request({
        url: _this.globalData.baseAPI + url,
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'token': wx.getStorageSync('token')
        },
        method: 'POST',
        data,
        success(res) {
          
          if (res.statusCode !== 200 || typeof res.data !== 'object') {
            reject('网络出错')
            return false;
          }

          if (res.data.code === 400) {
            let token = res.data.response
            wx.setStorageSync('token', token)
            wx.request({
              url: _this.globalData.baseAPI + url,
              header: {
                'content-type': 'application/x-www-form-urlencoded',
                'token': wx.getStorageSync('token')
              },
              method: 'POST',
              data,
              success(result) {
                resolve(result.data);
                return true;
              },
              fail(error) {
                reject('刷新token后请求失败');
                return false;
              },
              complete() {
                wx.hideNavigationBarLoading();
              }
            })
          } else if (res.data.code === 401) {
            // 移除强制跳转登录页，避免全局拦截
            // wx.reLaunch({
            //   url: '/pages/user/bind/index',
            // });
            _this.message('登录已过期，请重新登录', 'warning')
            reject('登录已过期，请重新登录');
            return false;
          } else if (res.data.code === 500) {
            reject(res.data.message)
            return false;
          } else if (res.data.code === 501) {
            reject(res.data.message)
            return false;
          } else {
            resolve(res.data);
            return true;
          }
        },
        fail(res) {
          reject(res.errMsg)
          return false;
        },
        complete(res) {
          wx.hideNavigationBarLoading();
        }
      })
    })
  }
})