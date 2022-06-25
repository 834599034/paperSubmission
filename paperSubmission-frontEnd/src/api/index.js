import * as paper from '@/api/paper'
import * as user from '@/api/user'
import * as table from '@/api/table'
import * as oss from '@/api/oss'
import * as admin from '@/api/admin'
import * as reviewer from '@/api/reviewer'

export default {
  ...paper,
  ...user,
  ...table,
  ...oss,
  ...admin,
  ...reviewer
}
