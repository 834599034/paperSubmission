#!/usr/bin/env shx

#当发生错误中止脚本
set -e
# cd 到构建输出的目录下
cd dist
git init
git add -A
git commit -m 'deploy'
# 这里的access_token是travis设置的环境变量
git push -f https://${gtoken}@github.com/jieye-ericx/conference-submission-system master:deploy
cd -  # 返回上一层

# ssh root@47.103.212.239 'Hx608136 && yes && cd /paperSubmission/frontend_travis/ && mkdir aaa'