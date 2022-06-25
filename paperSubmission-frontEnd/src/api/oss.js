import request from '@/utils/request'

export function getOSSPolicy() {
  return request({
    url: '/oss/policy',
    method: 'get'
  })
}
