import request from '@/utils/request'

export function adminGetAllReviewers() {
  return request({
    url: 'client/getAllReviewers',
    method: 'get'
  })
}
export function reviewerGetInvites() {
  return request({
    url: 'reviewerInvite/getInviteInfo',
    method: 'get'
  })
}
export function reviewerHandleInvites(data) {
  return request({
    url: 'reviewerInvite/handleInvite',
    method: 'post',
    data
  })
}
export function reviewerGetPapers(data) {
  return request({
    url: 'reviewerInvite/listReviewedPaper',
    method: 'get',
    params: {
      ...data
    }
  })
}
export function reviewerHandlePapers(data) {
  return request({
    url: 'reviewerInvite/reviewPaper',
    method: 'post',
    data
  })
}
