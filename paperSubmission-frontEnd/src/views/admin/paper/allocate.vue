<template>
  <div>
    <PaperTable @open-paper-detail="openPaperDetail">
      <!-- 仅能增加不需要scope的列 -->
      <template v-slot:column>
        <el-table-column align="center" prop="firstAuthor" label="第一作者" width="100" />
        <el-table-column show-overflow-tooltip align="center" prop="inviteNum" label="可分配审稿人" />
      </template>
      <template v-slot:option="slotProps">
        <!-- <template v-slot> -->
        <!-- <el-button type="primary" size="mini" @click="handleEdit(slotProps.scope.$index, slotProps.scope.row)">分配审稿人
        </el-button> -->
        <el-button size="mini" type="primary" plain>
          分配
        </el-button>
        <!-- <el-button size="mini" type="danger" plain @click.stop="handleReject(slotProps.scope.$index, slotProps.scope.row)">
          拒收
        </el-button> -->
      </template>
    </PaperTable>
    <el-dialog title="论文详情" :visible.sync="dialogPaperDetailVisible" width="80%">
      <AdminPaperDetail :submitid="submitid" />
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
  name: 'AdminAllocate',
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
    async getData({ curPage = 1, limit = 10, status = 10 }) {
      // console.log(curPage, limit)
      return await this.$API.adminPaperList({ curPage, limit, status })
    },
    openPaperDetail(submitid) {
      // console.log(submitid)
      this.submitid = submitid
      this.dialogPaperDetailVisible = true
    },
    handleReject(inex, row) {
      console.log('tuihui')
    }
  }
}
</script>
