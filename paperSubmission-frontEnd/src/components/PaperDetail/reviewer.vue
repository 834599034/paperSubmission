<template>
  <el-row>
    <el-row>
      <el-col :span="17">
        <PaperDetail :submitid="submitid" :mode="mode" :paper="paper" />
      </el-col>
      <el-col :span="6" :offset="1">
        <el-collapse v-model="activeNames">
          <el-collapse-item name="1">
            <template slot="title">
              撰写建议<i class="el-icon-edit" />
            </template>
            <el-input v-model="suggestion" type="textarea" :rows="20" />
          </el-collapse-item>
          <el-collapse-item name="2">
            <template slot="title">
              操作
            </template>
            <el-popconfirm
              confirm-button-text="对"
              cancel-button-text="不用了"
              icon="el-icon-info"
              icon-color="red"
              title="确定退回吗？"
              @confirm="reject"
            >
              <el-button slot="reference" size="mini" type="danger">退回</el-button>
            </el-popconfirm>
            <el-divider direction="vertical" />

            <el-popconfirm
              confirm-button-text="对"
              cancel-button-text="不用了"
              icon="el-icon-info"
              icon-color="red"
              title="确定通过吗？"
              @confirm="acceptA"
            >
              <el-button slot="reference" size="mini" type="success">通过</el-button>
            </el-popconfirm>
          </el-collapse-item>
        </el-collapse>
        <el-row />
      </el-col>
    </el-row>
  </el-row>
</template>
<script>
import PaperDetail from '@/components/PaperDetail/index.vue'
export default {
  name: 'ReviewerPaperDetail',
  components: {
    PaperDetail
  },
  props: {
    submitid: {
      type: Number,
      required: true
    },
    mode: {
      type: Number,
      require: true,
      default: 0
      // 0 普通用户/审稿人/超管 查看论文
      // 1 普通用户修改论文
    },
    reviewerInviteId: {
      type: Number,
      default: -1
    }
  },
  data() {
    return {
      paper: {},
      suggestion: '',
      activeNames: ['1', '2', '3']
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
    async acceptA() {
      console.log('点击通过')
      if (this.suggestion !== '') {
        const ans = await this.$API.reviewerHandlePapers({ reviewerInviteId: this.reviewerInviteId, conclusion: 0, suggestion: this.suggestion })
        if (ans.code === 200) this.$message.success('通过成功')
        this.$emit('change-dialog')
      } else {
        this.$message.error('审稿意见不能为空')
      }
    },
    async reject() {
      if (this.suggestion !== '') {
        const ans = await this.$API.reviewerHandlePapers({ reviewerInviteId: this.reviewerInviteId, conclusion: 1, suggestion: this.suggestion })
        if (ans.code === 200) this.$message.success('退回成功')
        this.$emit('change-dialog')
      } else {
        this.$message.error('审稿意见不能为空')
      }
    }
  }
}
</script>
<style scope>
.dd {
  margin-top: 10px;
}
</style>
