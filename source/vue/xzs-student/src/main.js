import Vue from 'vue'
import App from './App.vue'
import { router } from './router'
import store from './store'
import 'normalize.css/normalize.css'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css
import './icons' // icon
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style

Vue.use(Element, {
  size: 'medium' // set element-ui default size
})

Vue.config.productionTip = false

NProgress.configure({ showSpinner: false }) // NProgress Configuration

// 允许首页和登录、注册页面无需登录，其他页面可按需加权限校验
router.beforeEach(async (to, from, next) => {
  // start progress bar
  
  NProgress.start()
  if (to.meta.title !== undefined) {
    document.title = to.meta.title
  } else {
    document.title = '\u200E'
  }

  if (to.meta.bodyBackground !== undefined) {
    document.querySelector('body').setAttribute('style', 'background: ' + to.meta.bodyBackground)
  } else {
    document.querySelector('body').removeAttribute('style')
  }

  if (to.path) {
    // 百度统计（如有）
    if (typeof _hmt !== 'undefined') {
      _hmt.push(['_trackPageview', '/#' + to.fullPath])
    }
  }

  // 直接放行所有页面
  next()
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})

Vue.prototype.$$router = router

new Vue({
  router: router,
  store: store,
  render: h => h(App)
}).$mount('#app')