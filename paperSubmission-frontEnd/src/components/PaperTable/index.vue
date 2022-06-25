<template>
  <div>
    <el-table
      ref="filterTable"
      :data="tableData"
      stripe
      highlight-current-row
      @row-click="rowClick"
    >
      <el-table-column
        align="center"
        prop="type"
        label="类别"
        width="140"
      />
      <el-table-column
        align="center"
        prop="status"
        label="状态"
        width="100"
        :filters="paperStatus"
        :filter-method="filterPaperStatus"
        filter-placement="bottom-end"
      >
        <template slot-scope="scope">
          <el-tag
            :type="paperStatus[scope.row.status].color"
          >{{ paperStatus[scope.row.status].text }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        label="提交日期"
        sortable
        width="180"
        column-key="date"
        :formatter="formatterPaperDate"
      />
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="title"
        label="标题"
        width="280"
      />
      <slot name="column" />
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <slot name="option" :scope="scope">
            slot
            <!-- <el-button
              v-if="[1].some(v=>v===scope.row.status)"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
            >修改</el-button>
            <el-button
              v-if="[0,4].some(v=>v===scope.row.status)"
              size="mini"
              type="danger"
              @click="handleEdit(scope.$index, scope.row)"
            >撤稿</el-button>
            <el-button
              v-if="[1,2,5].some(v=>v===scope.row.status)"
              size="mini"
              type="warning"
              @click="handleEdit(scope.$index, scope.row)"
            >查看意见</el-button> -->
          </slot>
        </template>
      </el-table-column>
    </el-table>
    <el-col
      style=" text-align: center; margin-top:20px"
    >
      <el-pagination
        background
        layout="prev, pager, next,-> , sizes,total"
        :total="pagination.totalCount"
        :page-size="pagination.pageSize"
        :current-page="pagination.currPage"
        @current-change="handlePageChange"
        @size-change="handlePageSizeChange"
      />
    </el-col>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'PaperList',
  data() {
    return {
      tableData: [],
      pagination: {
        'totalCount': 1,
        'pageSize': 10,
        'totalPage': 1,
        'curPage': 1
      }
    }
  },
  computed: {
    ...mapGetters([
      'paperStatus'
      // ...
    ])
  },
  async created() {
    // console.log(this.$parent)
    await this.getData({ curPage: this.pagination.curPage, limit: this.pagination.pageSize })
  },

  methods: {
    async getData({ curPage = 1, limit = 10, status }) {
      // console.log(curPage, limit)
      const ans = (await this.$parent.getData({ curPage, limit, status })).data
      // console.log(ans)
      this.tableData = ans.list
      for (const key in this.pagination) {
        this.pagination[key] = ans[key]
      }
    },
    formatterPaperDate(row, column, cellValue, index) {
      // console.log(row, column, cellValue, index)
      const d = new Date(cellValue)
      return `${d.getFullYear()}年${d.getMonth() + 1}月${d.getDate()}日`
    },
    filterPaperStatus(value, row) {
      return row.status === value
    },
    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    async handlePageChange(curPage) {
      // console.log({ curPage, limit: this.pagination.pageSize })
      await this.getData({ curPage, limit: this.pagination.pageSize })
    },
    async handlePageSizeChange(pageSize) {
      await this.getData({ curPage: this.pagination.curPage, limit: pageSize })
    },
    rowClick(row, column, event) {
      // console.log(row, column, event)
      const reviewerInviteId = row.reviewerInviteId ? row.reviewerInviteId : -1
      this.$emit('open-paper-detail', row.submitId, reviewerInviteId)
    }
  }
}
</script>
