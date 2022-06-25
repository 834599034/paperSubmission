// import { getOSSPolicy } from '@/api/oss'
const state = {
  paperStatus: [
    {
      value: 0,
      status: 0,
      text: '投稿成功',
      color: ''
    },
    {
      value: 1,
      status: 1,
      text: '建议修改',
      color: 'warning'
    },
    {
      value: 2,
      status: 2,
      text: '拒收',
      color: 'danger'
    },
    {
      value: 3,
      status: 3,
      text: '待审稿',
      color: 'info'
    },
    {
      value: 4,
      status: 4,
      text: '待分配',
      color: 'info'
    },
    {
      value: 5,
      status: 5,
      text: '审稿通过',
      color: 'success'
    }
    // 0 - 投稿成功，1 - 退回修改，2 - 拒收，3 - 待审稿，4 - 待分配, 5 - 审稿通过
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
