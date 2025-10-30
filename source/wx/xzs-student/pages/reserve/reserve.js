Page({
  data: {
    // ***** 注意: 将 localhost 替换为您的本地 IP 地址，因为小程序无法直接访问 localhost *****
    // 如果在微信开发者工具中测试，使用 http://localhost:8888/ 即可。
    // 如果在真机调试，请将 localhost 替换为电脑的局域网 IP (例如: http://192.168.1.xxx:8888)
    BASE_URL: 'http://localhost:8888', 
    
    // 表单数据
    formData: {
      name: '',
      phone: '',
      reserveDateIndex: null, // 存储选中的日期列表索引
      reserveDateTime: '',    // 最终显示给用户的时间字符串
      courseId: 'A001',       // 示例值
      courseName: '编程入门体验课'  // 示例值
    },
    
    // 新增：可预约的日期和时间组合列表
    reserveOptions: [], 
    
    // 可预约的时间段（固定为周六日上下午）
    TIME_SLOTS: [
      { time: '上午 10:00', value: '10:00:00' },
      { time: '下午 14:00', value: '14:00:00' },
      { time: '下午 16:00', value: '16:00:00' }
    ],
  },

  onLoad: function (options) {
    if (options.courseId && options.courseName) {
      this.setData({
        'formData.courseId': options.courseId,
        'formData.courseName': options.courseName
      });
      wx.setNavigationBarTitle({
        title: `${options.courseName} - 预约登记`
      });
    } else {
       // 如果没有从上个页面传参，使用 data 中的默认值
       wx.setNavigationBarTitle({
        title: `${this.data.formData.courseName} - 预约登记`
      });
    }
    // 页面加载时生成可预约日期列表
    this.generateReserveOptions();
  },

  /**
   * 生成未来几周内的周六和周日时间组合
   */
  generateReserveOptions() {
    let options = [];
    const now = new Date();
    
    // 假设我们生成未来 4 周的预约选项
    const WEEKS_TO_GENERATE = 4;
    
    for (let i = 0; i < WEEKS_TO_GENERATE * 7 + 7; i++) {
      const date = new Date(now);
      date.setDate(now.getDate() + i);
      const day = date.getDay(); // 当前日期是周几 (0是周日, 6是周六)

      // 只保留周六 (6) 和周日 (0)
      if (day === 0 || day === 6) {
        const dateStr = this.formatDate(date);
        const dayOfWeek = day === 6 ? '周六' : '周日';
        
        // 为每一个周六/周日添加固定的上下午时间段
        this.data.TIME_SLOTS.forEach(slot => {
          options.push({
            // 显示给用户看的完整时间字符串
            display: `${dateStr} (${dayOfWeek}) ${slot.time}`, 
            // 提交给后端或用于内部处理的完整 ISO 格式
            value: dateStr + ' ' + slot.value
          });
        });
      }
    }
    
    this.setData({
      reserveOptions: options
    });
  },

  /**
   * 格式化日期为 YYYY-MM-DD
   */
  formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  },

  // 预约时间选择器改变事件
  bindReserveChange(e) {
    const index = e.detail.value;
    const selectedOption = this.data.reserveOptions[index];
    
    this.setData({
      'formData.reserveDateIndex': index,
      'formData.reserveDateTime': selectedOption.display // 存储用于显示
    });
  },

  // 输入框数据绑定 
  handleInput(e) {
    const field = e.currentTarget.dataset.field;
    this.setData({
      [`formData.${field}`]: e.detail.value
    });
  },

  // 提交表单
  submitForm() {
    const { formData, reserveOptions, BASE_URL } = this.data;

    // 1. 基础校验
    if (!formData.name || !formData.phone || formData.reserveDateIndex === null) {
      wx.showToast({ title: '请填写完整信息', icon: 'none' });
      return;
    }
    
    // 2. 手机号校验
    if (!/^1[3-9]\d{9}$/.test(formData.phone)) {
      wx.showToast({ title: '手机号格式不正确', icon: 'none' });
      return;
    }

    // 获取最终提交的预约时间值
    const finalAppointmentValue = reserveOptions[formData.reserveDateIndex].value;

    // 3. 构造提交数据 (与后端 ReservationRequest 字段匹配)
    const dataToSubmit = {
      courseId: formData.courseId,
      courseName: formData.courseName,
      name: formData.name,
      phone: formData.phone,
      appointment: finalAppointmentValue // 提交给后端的精确时间
    };

    console.log('提交的预约数据:', dataToSubmit);
    
    // 4. 发起网络请求调用后端接口
    wx.showLoading({ title: '提交中...', mask: true });
    
    wx.request({
      url: `${BASE_URL}/api/reserve/submit`, 
      method: 'POST',
      data: dataToSubmit,
      header: {
        'content-type': 'application/json' 
      },
      success: (res) => {
        wx.hideLoading();
        
        // 假设后端返回 code 0 表示成功
        if (res.statusCode === 200 && res.data && res.data.code === 0) { 
          wx.showModal({
            title: '预约成功',
            content: `我们已收到您的预约：\n课程：《${formData.courseName}》\n时间：${reserveOptions[formData.reserveDateIndex].display}\n我们将尽快与您联系！`,
            showCancel: false,
            success: () => {
              wx.navigateBack(); 
            }
          });
        } else {
          // 处理后端返回的错误信息
          const errorMsg = res.data && res.data.message ? res.data.message : '预约失败，请重试';
          wx.showToast({
            title: errorMsg,
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        wx.hideLoading();
        console.error('预约请求失败', err);
        wx.showToast({
          title: '网络错误，请检查后端服务是否运行在 http://localhost:8888',
          icon: 'none',
          duration: 3000
        });
      }
    });
  }
});