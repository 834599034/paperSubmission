// import { getOSSPolicy } from '@/api/oss'
const state = {
  reviewerStatus: [
    {
      value: 0,
      status: 0,
      text: '同意审稿',
      color: ''
    },
    {
      value: 1,
      status: 1,
      text: '拒绝审稿',
      color: 'warning'
    },
    {
      value: 2,
      status: 2,
      text: '审稿完成',
      color: 'success'
    },
    {
      value: 3,
      status: 3,
      text: '待接受',
      color: 'info'
    }
    // 0 - 同意审稿 1 - 拒绝审稿 2 - 审稿完成 3 - 待接受
  ]
}

const mutations = {
  // TOGGLE_DEVICE: (state, device) => {
  //   state.device = device
  // }
}

const actions = {
  // toggleSideBar({ commit }) {
  //   commit('TOGGLE_SIDEBAR')
  // }

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
