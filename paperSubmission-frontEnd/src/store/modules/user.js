import { login, getInfo, register } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    userRole: '',
    roles: [{
      value: 0,
      role: 0,
      text: '普通用户',
      eng: 'user'
    }, {
      value: 1,
      role: 1,
      text: '审稿人',
      eng: 'reviewer'
    }, {
      value: 2,
      role: 2,
      text: '超管',
      eng: 'admin'
    }],
    token: getToken(),
    userInfo: {
      // id: 14,
      // userName: 'obama',
      // password: '38d1b1adb16cb2fe3d89467d5b4dcdef',
      // salt: 'Z6#glphf_A9TO2og',
      // icon: null,
      // role: 0,
      // email: '834599034@qq.com',
      // address: null,
      // telphone: null
    }
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USERINFO: (state, data) => {
    // for (const [key, value] in data) {
    //   state.userInfo[key] = value
    // }
    state.userInfo = data
    state.userRole = state.roles.find(ele => ele.role === data.role).eng
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, role } = userInfo
    // console.log(userInfo)
    return new Promise((resolve, reject) => {
      login({ userName: username, password: password, role: role })
        .then(response => {
          // console.log(response)
          const { data } = response
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
    })
  },

  register({ commit }, userInfo) {
    // const { userName, password, email,realName } = userInfo
    return new Promise((resolve, reject) => {
      register(userInfo)
        .then(response => {
          console.log('register:' + response)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          return reject('getInfo 出错')
        }
        commit('SET_USERINFO', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      // logout(state.token).then(() => {
      //   removeToken() // must remove  token  first
      //   resetRouter()
      //   commit('RESET_STATE')
      //   resolve()
      // }).catch(error => {
      //   reject(error)
      // })
      removeToken()
      resetRouter()
      commit('RESET_STATE')
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
