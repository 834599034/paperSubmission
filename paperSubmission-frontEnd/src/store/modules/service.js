import { getOSSPolicy } from '@/api/oss'
const state = {
  fileUploadPolicy: {
    'accessid': 'LTAI5tGM7GY1D2thLacrA1RF',
    'policy': 'eyJleHBpcmF0aW9uIjoiMjAyMi0wNi0wNFQxMDo0MDo1OC42NDFaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCIyMDIyLTA2LTA0LyJdXX0=',
    'signature': 'wtVP2SphnXeD9hdgWIyaoQD9/vI=',
    'dir': '2022-06-04/',
    'host': 'https://paper-submission.oss-cn-shanghai.aliyuncs.com',
    'expire': '1654339258'
  }
}

const mutations = {
  // TOGGLE_DEVICE: (state, device) => {
  //   state.device = device
  // }
  SET_POLICY: (s, policy) => {
    s.fileUploadPolicy = policy
  }
}

const actions = {
  // toggleSideBar({ commit }) {
  //   commit('TOGGLE_SIDEBAR')
  // }
  async OSSPolicy({ commit }) {
    const res = await getOSSPolicy()
    commit('SET_POLICY', res.data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
