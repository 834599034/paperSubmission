import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import service from './modules/service'
import paper from './modules/paper'
import reviewer from './modules/reviewer'
import permission from './modules/permission'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    service,
    paper,
    reviewer,
    permission
  },
  getters
})

export default store
