import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/client/login',
    method: 'post',
    data
  })
}

export function register(data) {
  return request({
    url: '/client/register',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: 'client/clientInfo',
    method: 'get'
    // params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export function searchUser(key) {
  return request({
    url: '/client/searchAuthor',
    method: 'get',
    params: {
      key
    }
  })
}

export function getPaperSuggestion(data) {
  return request({
    url: 'submitPaper/getSuggestion',
    method: 'get',
    params: {
      ...data
    }
  })
}

export function updateUser(data) {
  return request({
    url: '/client/updateClientInfo',
    method: 'post',
    data
  })
}
export function updateUserPassword(data) {
  return request({
    url: '/client/updatePassword',
    method: 'post',
    data
  })
}
