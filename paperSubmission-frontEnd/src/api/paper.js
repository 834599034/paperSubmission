import request from '@/utils/request'

/**
 * {
  "title": "ESA:一种新型的隐私保护框架",
    "firstAuthor": 15,
  "otherAuthor": "衡家宝",
  "primaryKey": "隐私保护;ESA 框架;本地化差分隐私;中心化差分隐私;混洗差分隐私;数据收集",
  "abstractContext": "随着大数据驱动下智能技术的快速发展,大规模数据收集场景成为数据治理和隐私保护的主战场,本地化差分隐私技术作为该场景下的主流技术,被谷歌、苹果、微软等企业广泛使用．然而,该技术在用户本地对数据进行扰动,引入较多噪声,数据可用性较差．为实现可用性与隐私性兼顾的隐私保护方法,ESA(encodeＧshuffleＧanalyze)框架被提出,它在混洗器(shuffler)的作用下尽可能对数据进行较小扰动,同时保护用户隐私,使得任一用户的隐私信息都不能被数据分析者从收集数据中唯一识别．鉴于差分隐私在数学上优雅且严格的隐私定义,该框架目前主要基于差分隐私技术进行实现,该种实现称为混洗差分隐私(shuffledifferentialprivacy,SDP)．在保证相同隐私损失ε的情况下,混洗差分隐私比本地化差分隐私的可用性高O(n)倍,接近中心化差分隐私而不依赖于可信第三方．为对该新型的隐私保护框架进行综述,首先对该框架进行分析;之后基于主流的混洗差分隐私技术,对相关理论基础与技术基础进行总结,对不同统计问题下的隐私保护机制进行理论与实验对比;最终提出 ESA 框架的挑战问题,并对该框架下非差分隐私方法的实现进行展望．",
  "paperUrl": "https://paper-submission.oss-cn-shanghai.aliyuncs.com/ESA%20%E4%B8%80%E7%A7%8D%E6%96%B0%E5%9E%8B%E7%9A%84%E9%9A%90%E7%A7%81%E4%BF%9D%E6%8A%A4%E6%A1%86%E6%9E%B6%20%281%29.pdf",
  "typeId": 3
}
 * @param {*} data
 * @returns
 */
export function paperSubmit(data) {
  return request({
    url: '/submitPaper/contribute',
    method: 'post',
    data
  })
}
export function paperReSubmit(data) {
  return request({
    url: '/submitPaper/modify',
    method: 'post',
    data
  })
}

export function paperType() {
  return request({
    url: '/paperType/allinfo',
    method: 'get'
  })
}

export function paperList({ curPage, limit }) {
  // console.log(curPage, limit)
  return request({
    url: '/submitPaper/userAllSubmit',
    method: 'get',
    params: {
      curPage,
      limit
    }
  })
}
export function adminPaperList({ curPage, limit, status }) {
  // console.log(curPage, limit)
  return request({
    url: '/submitPaper/listSubmit',
    method: 'get',
    params: {
      curPage,
      limit,
      status
    }
  })
}

export function paperDetail(submitId) {
  // console.log(curPage, limit)
  return request({
    url: '/submitPaper/paperDeail',
    method: 'get',
    params: {
      submitId
    }
  })
}
export function paperReviewers(submitId) {
  // console.log(curPage, limit)
  return request({
    url: '/reviewerInvite/asignedReviewers',
    method: 'get',
    params: {
      submitId
    }
  })
}
