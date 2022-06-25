<template>
  <div>
    <PaperDetail :submitid="submitid" :mode="mode" :paper="paper" />
    <el-collapse v-if="mode===1" :value="['1']">
      <el-collapse-item title="提交" name="1">
        <el-button type="primary" @click="submitPaper">提交修改论文</el-button>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<script>
import PaperDetail from '@/components/PaperDetail/index.vue'

export default {
  name: 'UserPaperDetail',
  components: {
    PaperDetail
  },
  props: {
    submitid: {
      type: Number,
      required: true,
      default: -1
    },
    mode: {
      type: Number,
      require: true,
      default: 0
      // 0 普通用户/审稿人/超管 查看论文
      // 1 普通用户修改论文
    }
  },
  data() {
    return {
      paper: {}

    }
  },
  watch: {
    // 如果 `question` 发生改变，这个函数就会运行
    submitid: async function(newQuestion, oldQuestion) {
      this.paper = await this.getPaperDetail()
    }
  },
  async mounted() {
    this.paper = await this.getPaperDetail()
  },
  async created() {
  },
  methods: {
    downloadPaper() {
      window.open(this.paper.paperUrl, '_blank')
    },
    async getPaperDetail() {
      return (await this.$API.paperDetail(this.submitid)).data
    },
    async submitPaper() {
      // todo 表单校验
      const res = await this.$API.paperReSubmit({ ...this.paper, submitId: this.submitid })
      if (res.code === 200) {
        this.$message.success('论文修改提交成功')
        // for (const key in this.paperForm) {
        //   this.paperForm[key] = ''
        // }
        // this.$router.push({ name: 'PaperList' })
      }
    }
  }
}
</script>
<style scope>
</style>
