<template>
  <div>
    <PaperTable>
      <!-- 仅能增加不需要scope的列 -->
      <template v-slot:column>
        <el-table-column align="center" prop="firstAuthor" label="第一作者" width="100" />
      </template>
      <template v-slot:option="slotProps">
        <el-button
          size="mini"
          type="warning"
          @click.stop="handleShowSuggestion(slotProps.scope.$index, slotProps.scope.row)"
        >查看意见
        </el-button>
      </template>
    </PaperTable>
    <el-dialog title="反馈意见" :visible.sync="dialogSuggestionVisible" width="30%" center append-to-body>
      <div>{{ succ }}</div>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button> -->
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PaperTable from '@/components/PaperTable/index.vue'
export default {
  name: 'AdminFinished',
  components: {
    PaperTable
  },
  data() {
    return {
      succ: '',
      dialogSuggestionVisible: false
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
    async getData({ curPage = 1, limit = 10, status = 12 }) {
      // console.log(curPage, limit)
      return await this.$API.adminPaperList({ curPage, limit, status })
    },
    async handleShowSuggestion(index, row) {
      const ans = await this.$API.getPaperSuggestion({ submitId: row.submitId })
      // console.log(ans)
      if (ans.code === 200) {
        this.dialogSuggestionVisible = true
        this.succ = ans.data.suggestion
      } else {
        this.$message.error('获取意见失败')
      }
    }
  }
}
</script>
