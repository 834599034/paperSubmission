import request from '@/utils/request'

export function getAdminPapers() {
  return request({
    url: '/oss/policy',
    method: 'get'
  })
}

// {
//   "submitId": 25,
//   "reviewerId": [
//       26, 27, 28
//     ]
// }
export function adminAssignPaper(data) {
  return request({
    url: 'reviewerInvite/assignPaper',
    method: 'post',
    data
  })
}
export function adminDeletePaper(data) {
  return request({
    url: 'reviewerInvite/deleteAssignedReviewers',
    method: 'post',
    data
  })
}
export function adminGetPaperStatus(data) {
  return request({
    url: 'submitPaper/getSubmitDetail',
    method: 'get',
    params: {
      ...data
    }
  })
}
export function adminHandleSubmit(data) {
  return request({
    url: 'submitPaper/handleSubmit',
    method: 'post',
    data
  })
}
