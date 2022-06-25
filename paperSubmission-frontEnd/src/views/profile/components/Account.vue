<template>
  <div>

    <el-form>
      <el-form-item label="用户名">
        <el-input v-model.trim="user.userName" disabled />
      </el-form-item>
      <el-form-item label="真名">
        <el-input v-model.trim="user.realName" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model.trim="user.email" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model.trim="user.telephone" />
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model.trim="user.address" />
      </el-form-item>

      <el-form-item label="兴趣">
        <!-- <PaperCategorySingle v-model="user.id" /> -->
        <PaperCategoryMultiple v-model="user.majorIds" style="width:100%" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateUserInfo">更新信息</el-button>
      </el-form-item>

      <el-divider />

      <el-form-item label="旧密码">
        <el-input v-model.trim="pass.oldPassword" show-password />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model.trim="pass.newPassword" show-password />
      </el-form-item>
      <el-form-item label="重复新密码">
        <el-input v-model.trim="pass.newPassword" show-password />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateUserPassword">更新密码</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// import PaperCategorySingle from '@/components/PaperCategory/single.vue'
import PaperCategoryMultiple from '@/components/PaperCategory/multiple.vue'
export default {
  components: {
    // PaperCategorySingle,
    PaperCategoryMultiple
  },
  props: {
    userInfo: {
      type: Object,
      default: () => {
        return {
          'id': 23,
          'userName': 'rad',
          'password': 'e7ce2d612703fcf58f5d0ea59db120a8',
          'salt': 'YYIZL)r9yd4P#LoL',
          'icon': 'https://rusyasoft.github.io/assets/2018-05-20-ddd-main/onionModel.png',
          'role': 0,
          'email': '111@qq.com',
          'address': null,
          'telphone': null,
          'realName': '马斯克'
        }
      }
    }
  },
  data() {
    return {
      // test: [],
      user: Object.assign({}, this.userInfo),
      pass: {
        oldPassword: '',
        newPassword: ''
      }
    }
  },
  methods: {
    async updateUserInfo() {
      const ans = await this.$API.updateUser(this.user)
      if (ans.code === 200) {
        await this.$store.dispatch('user/getInfo')
        this.$message.success('更新成功')
      } else { this.$message.error(ans.message) }
    },
    async updateUserPassword() {
      const ans = await this.$API.updateUserPassword(this.pass)
      if (ans.code === 200) {
        await this.$store.dispatch('user/getInfo')
        this.$message.success('更新成功')
      } else { this.$message.error(ans.message) }
    }
  }
}
</script>
