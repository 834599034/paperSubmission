import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

import * as API from '@/api'
// console.log(API)
// import message from '@/utils/message'

import GoEasy from 'goeasy'
Vue.prototype.$goeasy = GoEasy.getInstance({
  host: 'hangzhou.goeasy.io', // 若是新加坡区域：singapore.goeasy.io
  appkey: 'BC-3f8da063d00d4983a29d0189ec2cdbe4',
  modules: ['pubsub']// 根据需要，传入‘pubsub’或'im’，或数组方式同时传入
})

// Vue.prototype.$message = message
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  beforeCreate() {
    Vue.prototype.$bus = this
    Vue.prototype.$API = API.default
  },
  router,
  store,
  render: h => h(App)
})
