import OSS from 'ali-oss'
const region = '你的region '
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，部署在服务端使用RAM子账号或STS，部署在客户端使用STS
const accessKeyId = '你的accessKeyId '
const accessKeySecret = '你的accessKeySecret '
const bucket = '你的bucket '

// client配置
const client = new OSS({
  region: region,
  accessKeyId: accessKeyId,
  accessKeySecret: accessKeySecret,
  bucket: bucket
})

export default (conf) => {
  return new OSS(conf)
}
// 文件上传
export const put = async(filePath, fileUrl) => {
  try {
    const result = await client.put(filePath, fileUrl)
    return result
  } catch (e) {
    console.log(e)
  }
}

// oss的地址
// export const getSystemPath = () => {
//   return ossPath
// }

// 文件的上传地址
export const getFilePath = (ObjName, orangeName) => {
  const fileName = getFileNumber() + getFileSuffix(orangeName)
  const result = `/shuyu/admin/${fileName}`
  return result
}

// oss文件名获取随机
export const getFileNumber = () => {
  const timeNumber = new Date().getTime()
  const randomNumber = Math.floor(Math.random() * 999999)
  return timeNumber + '' + randomNumber
}

// 获取文件后缀
export const getFileSuffix = (fileName) => {
  const name = fileName.lastIndexOf('.')// 取到文件名开始到最后一个点的长度
  const length = fileName.length
  const fileSuffix = fileName.substring(name, length)
  return fileSuffix
}

// 检查文件格式
export const checkFileFormat = (fileName) => {
  let isJPG = false
  const type = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()
  if (type === 'jpg' || type === 'png' || type === 'jpeg') {
    isJPG = true
  } else if (type === 'mp4' || type === '3gp' || type === 'avi') {
    isJPG = true
  }
  return isJPG
}

// 获取录音和视频的时长
export const getTimeFromVideo = (file) => {
  let time = 0
  var url = URL.createObjectURL(file)
  var audioElement = new Audio(url)
  audioElement.addEventListener('loadedmetadata', (_event) => {
    time = parseInt(audioElement.duration)
    return time
  })
  console.log(time)
}
