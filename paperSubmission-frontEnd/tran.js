// var path = require('path')
var fs = require('fs')
var pathName = '/Users/likuihao/PycharmProjects/SMARTS-baseline/baselines/marl_benchmark/mydata/dqn_episodes'

const readd = async(path) => {
  let cnt = 0
  const ans = await fs.readdirSync(path)
  // ans = ans.slice(0, 2)
  const gen_dict = (num) => {
    return {
      'min': Array(num).fill(Infinity),
      'max': Array(num).fill(-Infinity)
    }
  }
  const ori = {
    'distance_to_center': gen_dict(1),
    'goal_relative_pos': gen_dict(2),
    'heading_errors': gen_dict(20),
    'neighbor': gen_dict(40),
    'speed': gen_dict(1),
    'steering': gen_dict(1)
  }
  for (const fileName of ans) {
    const tmp = await require(pathName + '/' + fileName)
    // console.log(tmp)
    cnt += 1
    for (const key in tmp) {
      // console.log(tmp[key]['min'].length)
      for (let i = 0; i < tmp[key]['min'].length; i++) {
        if (tmp[key]['min'][i] < ori[key]['min'][i]) ori[key]['min'][i] = tmp[key]['min'][i]
        if (tmp[key]['max'][i] > ori[key]['max'][i]) ori[key]['max'][i] = tmp[key]['max'][i]
      }
    }
    // console.log('---------------------')
    // console.log(tmp)
    // console.log(ori)
  }
  console.log('经过' + cnt + '个文件的迭代')
  console.log(ori)
}

readd(pathName)
