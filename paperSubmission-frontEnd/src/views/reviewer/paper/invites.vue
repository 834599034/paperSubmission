<template>
  <div>
    <!-- <el-col :offset="10" :span="2" /> -->
    <PaperTable @open-paper-detail="openPaperDetail">
      <!-- 仅能增加不需要scope的列 -->
      <template v-slot:column />
      <template v-slot:option="slotProps">
        <el-button size="mini" @click.stop="handleAccept(slotProps.scope.$index, slotProps.scope.row)">同意</el-button>
        <el-button size="mini" type="danger" @click.stop="handleRefuse(slotProps.scope.$index, slotProps.scope.row)">拒绝
        </el-button>
      </template>
    </PaperTable>
    <!-- </el-col> -->
    <el-dialog title="论文详情" :visible.sync="dialogPaperDetailVisible" width="80%">
      <ReviewerPaperDetail ref="ReviewerPaperDetail" :submitid="submitid" :mode="3" />
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span> -->
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PaperTable from '@/components/PaperTable/index.vue'
import ReviewerPaperDetail from '@/components/PaperDetail/reviewer.vue'

export default {
  name: 'ReviewerInvites',
  components: {
    PaperTable, ReviewerPaperDetail
  },
  data() {
    return {
      dialogPaperDetailVisible: false,
      submitid: 0
    }
  },
  computed: {
    ...mapGetters([
      // 'paperStatus'
      // ...
    ])
  },
  async created() {
  },
  methods: {
    async getData({ curPage = 1, limit = 10 }) {
      // console.log(curPage, limit)
      return await this.$API.reviewerGetInvites({ curPage, limit })
    },
    openPaperDetail(submitid) {
      // console.log(submitid)
      this.submitid = submitid
      this.dialogPaperDetailVisible = true
    },
    async handleAccept(index, row) {
      // console.log(row)
      const ans = await this.$API.reviewerHandleInvites({ reviewerInviteId: row.reviewerInviteId, result: 0 })
      if (ans.code === 200) this.$message.success('同意成功')
      await this.$children[0].getData({ curPage: this.$children[0].pagination.curPage, limit: this.$children[0].pagination.pageSize })
    },
    async handleRefuse(index, row) {
      const ans = await this.$API.reviewerHandleInvites({ reviewerInviteId: row.reviewerInviteId, result: 1 })
      if (ans.code === 200) this.$message.success('拒绝成功')
      console.log(this.$children)
      await this.$children[0].getData({ curPage: this.$children[0].pagination.curPage, limit: this.$children[0].pagination.pageSize })
    }
  }
}
</script>
