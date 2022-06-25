<template>
  <div>
    <PaperTable @open-paper-detail="openPaperDetail">
      <!-- 仅能增加不需要scope的列 -->
      <template v-slot:column>
        <el-table-column align="center" prop="firstAuthor" label="第一作者" width="100" />
      </template>
      <template v-slot:option="slotProps">
        <el-button size="mini" plain type="primary" @click.stop="openPaperDetail(slotProps.scope.row.submitId)">审阅
        </el-button>
      </template>
    </PaperTable>
    <el-dialog title="论文详情" :visible.sync="dialogPaperDetailVisible" width="80%">
      <AdminPaperDetail :submitid="submitid" :mode="5" />
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
import AdminPaperDetail from '@/components/PaperDetail/admin.vue'
export default {
  name: 'AdminReviewing',
  components: {
    PaperTable, AdminPaperDetail
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
    async getData({ curPage = 1, limit = 10, status = 11 }) {
      // console.log(curPage, limit)
      return await this.$API.adminPaperList({ curPage, limit, status })
    },
    handleEdit() {

    },
    openPaperDetail(submitid) {
      // console.log(submitid)
      this.submitid = submitid
      this.dialogPaperDetailVisible = true
    }
  }
}
</script>
