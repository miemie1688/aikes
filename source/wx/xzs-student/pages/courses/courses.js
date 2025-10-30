Page({
  data: {
    // 新增状态：记录当前展开的课程ID
    activeId: 1, 
    // 通用试听信息，用于所有课程的宣传
    trialInfo: {
      singlePrice: '9.9元',
      groupAction: '两人同行，免费试听！',
      mainAction: '立即抢购试听席位'
    },
    courseList: [
      {
        id: 1,
        name: "Scratch 图形化游戏编程启蒙课",
        slogan: "拖拽积木，创造你的第一个游戏世界！",
        age: "5-10 岁",
        // 价格信息弱化
        priceInfo: { price: "¥ 3,199", originalPrice: "¥ 3,800" }, 
        size: "形象气泡班，限 15 人",
        content: "【核心技能】通过趣味动画、游戏闯关项目，掌握编程三大核心逻辑：顺序、循环、判断。告别被动游戏，成为主动的创造者。\n【价值提升】在项目分解中，系统培养逻辑推理、系统化解构复杂问题的能力，为高阶编程打下坚实基础。",
        action: "立即预约试听，解锁游戏创作天赋！"
      },
      {
        id: 2,
        name: "Python 人工智能编程",
        slogan: "开启孩子的未来创造力，直通AI世界！",
        age: "10-18 岁",
        priceInfo: { price: "¥ 4,499", originalPrice: "¥ 5,200" },
        size: "精英小班，4-8 人（限 15 人）",
        content: "【项目实战】使用Python语言，亲手打造聊天机器人、图像识别应用和数据可视化项目。从零基础到掌握面向对象编程。\n【未来科技】深入探索AI背后的运行原理，培养数据思维和创新能力，为未来进入高科技领域做好准备。",
        action: "立即预约试听，领取 AI 体验课！"
      },
      {
        id: 3,
        name: "创意积木架构师启蒙课",
        slogan: "手脑并用，在游戏中建构空间与逻辑！",
        age: "5-7 岁",
        priceInfo: { price: "¥ 2,999", originalPrice: "¥ 3,600" },
        size: "小班名额，仅 12 席",
        content: "【能力培养】在系统化拼搭任务中，精准锻炼手部精细动作与手眼协调能力。激发孩子的空间想象、几何图形识别能力。\n【思维启发】通过合作项目，学会任务分解、团队沟通，培养创造性解决问题的建筑师思维。",
        action: "立即预约试听，免费体验一节课！"
      },
      {
        id: 4,
        name: "高级游戏软硬件一体化开发项目课",
        slogan: "从代码到实体交互，定义下一个科技创新！",
        age: "12+ 岁（建议有基础）", // 增加适用建议
        priceInfo: { price: "¥ 6,599", originalPrice: "¥ 7,800" },
        size: "硬核项目班，仅 10 席！",
        content: "【全链实践】项目贯通游戏软件开发、硬件电路设计、嵌入式编程三大领域。以 自感应动小车 等高级综合项目为目标。\n【价值体现】导师领衔（资深工程师/竞赛金牌教练），学员将产出高含金量的 升学项目作品 ，直就名校敲门砖。",
        action: "立即预约试听，进行项目能力评估！"
      },
      {
        id: 5,
        name: "无人机技术与编程飞行营",
        slogan: "代码为翼，掌握未来空中科技！",
        age: "10-18 岁", 
        priceInfo: { price: "¥ 5,588", originalPrice: "¥ 6,500" },
        size: "飞行安全小班，限 10 人！",
        content: "【核心技术】学习飞行器 空气动力学、传感器融合 技术。掌握 Python编程控制 无人机，实现自主巡航、避障等智能任务。\n【实战体验】从模拟器到 真机实操 ，训练高难度动手飞行技巧，培养严谨的 航空安全意识 。",
        action: "立即预约试听，锁定飞行席位！"
      },
      {
        id: 6,
        name: "三维创意设计与 3D 打印工坊",
        slogan: "将孩子的想象力，高效打印成现实！",
        age: "9-16 岁",
        priceInfo: { price: "¥ 4,199", originalPrice: "¥ 4,900" },
        size: "创客实操小班，仅 10 席！",
        content: "【设计技能】学习专业三维设计软件，掌握 几何建模、结构设计 核心思想。将复杂创意转化为可执行的数字模型。\n【实践落地】亲手操作 3D 打印机 ，从切片到成品，见证数字设计变实物的奇迹，培养 跨学科的创新制造能力 。",
        action: "立即预约试听，开启创造之旅！"
      },
      {
        id: 7,
        name: "机器人技术创客营",
        slogan: "动手搭建，为机器人赋予智能“大脑”！",
        age: "8-15 岁",
        priceInfo: { price: "¥ 4,799", originalPrice: "¥ 5,600" },
        size: "竞赛预备小班，仅 12 席！",
        content: "【硬件搭建】从零开始，掌握 机械结构、齿轮传动、传感器与执行器原理 。实现从图纸到实物的机器人搭建。\n【软件控制】使用 图形化编程 或简易代码，赋予机器人运动逻辑和任务判断能力。课程项目 直接对接 WRO/FLL  等国际竞赛标准。",
        action: "立即预约试听，抢占创客席位！"
      },
      {
        id: 8,
        name: "C++ 信息学奥赛特训营",
        slogan: "叩开名校大门的金钥匙，竞赛直通车！",
        age: "10-16 岁",
        priceInfo: { price: "¥ 3,999", originalPrice: "¥ 4,800" },
        size: "精英小班，4-8 人（限前 20 名）",
        content: "【竞赛目标】课程直指  NOIP/CSP-S/S  等权威信息学奥赛，系统学习 数据结构、基础算法、动态规划 等竞赛核心知识。\n【学习模式】 2小时/节课，高强度讲练结合 。专注于算法思维和代码实现效率，高效助力学员 升学加分 。",
        action: "立即预约试听，赢在起跑线！"
      }
    ]
  },
// ... (toggleContent 和 onReserve 函数保持不变)
  // 新增函数：点击课程列表项时触发
  toggleContent(e) {
    const clickId = e.currentTarget.dataset.id;
    
    // 如果点击的是当前已展开的，则收起（设置为 null 或 0，此处设置为 null）
    // 否则，设置为点击的ID
    this.setData({
      activeId: this.data.activeId === clickId ? null : clickId
    });
  },

  // 假设您有一个点击预约的函数 (保持不变)
  onReserve(e) {
    const courseId = e.currentTarget.dataset.id;
    const courseName = e.currentTarget.dataset.name;
    wx.navigateTo({
      url: `/pages/reserve/reserve?courseId=${courseId}&courseName=${courseName}`
    });
    console.log(`用户预约了课程ID: ${courseId}，课程名: ${courseName}`);
    wx.showToast({
      title: '名额剩余，前往预约',
      icon: 'success',
      duration: 2000
    });
  }
})