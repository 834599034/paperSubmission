<template>
  <div>
    <!-- <el-col :offset="10" :span="2" /> -->
    <PaperTable @open-paper-detail="openPaperDetail">
      <!-- 仅能增加不需要scope的列 -->
      <template v-slot:column />
      <template v-slot:option="slotProps">
        <el-button size="mini" type="danger" @click="handleReview(slotProps.scope.$index, slotProps.scope.row)">
          审阅
        </el-button>
      </template>
    </PaperTable>
    <!-- </el-col> -->
    <el-dialog title="论文详情" :visible.sync="dialogPaperDetailVisible" width="80%">
      <ReviewerPaperDetail
        :submitid="submitid"
        :mode="3"
        :reviewer-invite-id="reviewerInviteId"
        @change-dialog="changeDialog"
      />
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
  name: 'ReviewerReview',
  components: {
    PaperTable, ReviewerPaperDetail
  },
  data() {
    return {
      dialogPaperDetailVisible: false,
      submitid: -1,
      reviewerInviteId: -1
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
    async getData({ curPage = 1, limit = 10, status = 0 }) {
      // console.log(curPage, limit)
      return await this.$API.reviewerGetPapers({ curPage, limit, status })
    },
    openPaperDetail(submitid, reviewerInviteId) {
      // console.log(submitid)
      this.submitid = submitid
      this.reviewerInviteId = reviewerInviteId
      this.dialogPaperDetailVisible = true
    },
    async changeDialog() {
      console.log('changeDialog')
      this.dialogPaperDetailVisible = this.dialogPaperDetailVisible !== true
      await this.$children[0].getData({ curPage: this.$children[0].pagination.curPage, limit: this.$children[0].pagination.pageSize, status: 0 })
    },
    handleReview(index, row) {
      console.log(row)
      this.openPaperDetail(row.submitId)
    }
  }
}
</script>
