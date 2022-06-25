<template>
  <el-row>
    <el-collapse :value="['1','2','3']">
      <el-collapse-item title="分配审稿人" name="1">
        <el-card :body-style="{ padding: '8px' ,textAlign:'center'}" style="margin:0 5px  5px 0">
          <div>
            剩余可分配审稿人：
            <el-tag :type="paper.inviteNum > 0 ? 'success' : 'danger'">{{ assignedReviewers.inviteNum }}</el-tag>
          </div>
        </el-card>
        <el-select v-model="reviewerId" multiple placeholder="请选择" style="width:40%">
          <el-option v-for="item in reviewers" :key="item.id" :label="item.realName" :value="item.id" />
        </el-select>
        <el-button style="margin-left:40px" @click="assignPaper">确定分配</el-button>
        <!-- <el-transfer
            ref="transfer"
            v-model="reviewerId"
            style="text-align: left; display: inline-block"
            :props="{
              key: 'id',
              label: 'realName'
            }"
            :titles="['审稿人列表', '已分配']"
            :button-texts="['移去', '新增']"
            filter-placeholder="请选择审稿人"
            :data="reviewers"
            @change="transferChange"
            @left-check-change="transferLeftChange"
          >
            <div slot-scope="{ option }">
              {{ option.realName }}
              <span style="font-size:12px;">
                <span v-if="showAccept(option)===3">
                  待接受
                </span>
                <span v-if="showAccept(option)===0">
                  已接受
                </span>
                <span v-if="showAccept(option)===2">
                  审稿完成
                </span>
              </span>
            </div>
          </el-transfer> -->

        <!-- <el-button type="primary" @click="assignPaper">分配审稿人</el-button> -->
      </el-collapse-item>
    </el-collapse>
    <PaperDetail v-if="mode!==5" :submitid="submitid" :mode="mode" :paper="paper" />
    <div v-else>
      <el-collapse :value="adminUnEditable?['1']:['1','2','3']">
        <el-collapse-item title="审稿人状态" name="1">
          <el-row v-for="(item) in reviewDetail.reviewDetailVos" :key="item.reviewerId" style="margin:5px 0;">
            <el-col :span="22" :offset="1">
              <el-card :body-style="{ padding: '0px' }">
                <div style="padding: 5px 10px;">
                  <el-row>
                    <el-col :span="3">
                      <el-avatar :src="item.icon" />
                    </el-col>
                    <el-col :span="5">
                      <el-input v-model="item.reviewerRealName" :placeholder="item.reviewerRealName" disabled />
                    </el-col>
                    <el-col :span="4" :offset="1">
                      <el-tag style="height:40px;line-height:40px" :type="reviewerStatus[item.status].color">
                        {{ reviewerStatus[item.status].text }}
                      </el-tag>
                    </el-col>
                    <el-col v-if="item.status===2" :span="10" :offset="0">
                      <el-tag style="height:40px;line-height:40px" :type="item.conclusion===0?'success':'warning'">
                        {{ item.conclusion === 0 ? '通过':'退回' }}
                      </el-tag>
                      <el-button
                        size="mini"
                        style="height:40px;margin-left: 5px;"
                        type="primary"
                        plain
                        @click="openSuggestion(item)"
                      >
                        查看意见
                      </el-button>
                    </el-col>

                  </el-row>
                </div>
              </el-card>
            </el-col>

          </el-row>
          <el-dialog title="反馈意见" :visible.sync="dialogSuggestionVisible" width="30%" center append-to-body>
            <div>{{ publicSuggestion }}</div>
            <span slot="footer" class="dialog-footer">
              <!-- <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button> -->
            </span>
          </el-dialog>
        </el-collapse-item>
        <el-collapse-item name="2">
          <template slot="title">
            撰写建议<i class="el-icon-edit" />
            <span style="color:gray;padding-left: 10px;">{{ adminUnEditable?'请等待审稿人审稿完成后再操作':'' }}</span>
          </template>
          <el-input v-model="adminSuggestion" :disabled="adminUnEditable" type="textarea" :rows="20" />
        </el-collapse-item>
        <el-collapse-item name="3">
          <template slot="title">
            操作
            <span style="color:gray;padding-left: 10px;">{{ adminUnEditable?'请等待审稿人审稿完成后再操作':'' }}</span>
          </template>
          <el-popconfirm
            confirm-button-text="对"
            cancel-button-text="不用了"
            icon="el-icon-info"
            icon-color="red"
            title="确定退回吗？"
            @confirm="adminHandleSubmit(2)"
          >
            <el-button slot="reference" :disabled="adminUnEditable" size="mini" type="danger">退回</el-button>
          </el-popconfirm>
          <el-divider direction="vertical" />
          <el-popconfirm
            confirm-button-text="对"
            cancel-button-text="不用了"
            icon="el-icon-info"
            icon-color="red"
            title="确定退回修改吗？"
            @confirm="adminHandleSubmit(1)"
          >
            <el-button slot="reference" :disabled="adminUnEditable" size="mini" type="warning">退回修改</el-button>
          </el-popconfirm>
          <el-divider direction="vertical" />

          <el-popconfirm
            confirm-button-text="对"
            cancel-button-text="不用了"
            icon="el-icon-info"
            icon-color="red"
            title="确定通过吗？"
            @confirm="adminHandleSubmit(5)"
          >
            <el-button slot="reference" :disabled="adminUnEditable" size="mini" type="success">通过</el-button>
          </el-popconfirm>
        </el-collapse-item>
      </el-collapse>
    </div>
  </el-row>
</template>
<script>
import PaperDetail from '@/components/PaperDetail/index.vue'
import { mapGetters } from 'vuex'
export default {
  name: 'AdminPaperDetail',
  components: {
    PaperDetail
  },
  props: {
    submitid: {
      type: Number,
      required: true,
      default: 25
    },
    mode: {
      type: Number,
      require: true,
      default: 0
      // 0 普通用户/审稿人/超管 查看论文
      // 1 普通用户修改论文
      // 5 超管查看审稿中
    }
  },
  data() {
    return {
      publicSuggestion: '',
      paper: {},
      reviewers: [],
      reviewerId: [],
      assignedReviewers: {
      },
      option: {

      },
      reviewDetail: {
        reviewDetailVos: []
      },
      dialogSuggestionVisible: false,
      adminSuggestion: ''
    }
  },
  computed: {
    ...mapGetters([
      'reviewerStatus'
      // ...
    ]),
    adminUnEditable: function() {
      return this.reviewDetail.reviewDetailVos.findIndex((ele) =>
        ele.status !== 2
      ) !== -1
    }
  },
  watch: {
    // 如果 `question` 发生改变，这个函数就会运行
    submitid: async function(newQuestion, oldQuestion) {
      if (this.mode !== 5) { this.paper = await this.getPaperDetail() } else {
        this.reviewDetail = (await this.$API.adminGetPaperStatus({ submitId: this.submitid })).data
      }
    }
  },
  async mounted() {
    if (this.mode !== 5) { this.paper = await this.getPaperDetail() } else {
      this.reviewDetail = (await this.$API.adminGetPaperStatus({ submitId: this.submitid })).data
    }

    this.reviewers = (await this.$API.adminGetAllReviewers()).data
    this.assignedReviewers = (await this.$API.paperReviewers(this.submitid)).data
    this.assignedReviewers.reviewerVos.forEach(element => {
      this.reviewerId.push(element.id)
    })
    this.assignedReviewers.reviewerVos.forEach(ele => {
      const ans = this.reviewers.find(e => e.id === ele.id)
      // console.log(ans)
      ans['disabled'] = ele.inviteStatus !== 3
    })
  },
  async created() {
  },
  methods: {
    openSuggestion(data) {
      this.dialogSuggestionVisible = true
      this.publicSuggestion = data.suggestion
    },
    downloadPaper() {
      window.open(this.paper.paperUrl, '_blank')
    },
    async getPaperDetail() {
      return (await this.$API.paperDetail(this.submitid)).data
    },
    async assignPaper() {
      const res = await this.$API.adminAssignPaper({ submitId: this.submitid, reviewerId: this.reviewerId })
      // console.log(res)
      if (res.code === 200) this.$message.success('分配成功')
    },
    async transferChange(remainReviewers, dirction, changedReviewers) {
      // console.log(dirction)
      // if (dirction === 'left') {
      //   const res = await this.$API.adminDeletePaper({ submitId: this.submitid, reviewerId: changedReviewers })
      //   if (res.code === 200) this.$message.success('取消分配成功')
      //   this.$refs.transfer.sourceData.forEach(ele => {
      //     ele.disabled = false
      //   })
      //   // await this.$API.adminAssignPaper
      // } else {
      //   if (changedReviewers.length > this.assignedReviewers.inviteNum) {
      //     this.$message.error('分配人数超过限制')
      //     return
      //   }
      const res = await this.$API.adminAssignPaper({ submitId: this.submitid, reviewerId: changedReviewers })
      if (res.code === 200) this.$message.success('分配成功')
      changedReviewers.forEach(ele => {
        this.reviewers.forEach(e => {
          if (e.id === ele) {
            e.disabled = false
          }
        })
      })
      // }
      this.assignedReviewers = (await this.$API.paperReviewers(this.submitid)).data
    },
    transferLeftChange(a, b) {
      console.log(a, b)
      if (a.length >= this.assignedReviewers.inviteNum) {
        // this.reviewers.forEach(ele => {
        //   ele.disabled = true
        // })
        console.log(this.$refs.transfer)
        this.$refs.transfer.sourceData.forEach(ele => {
          ele.disabled = true
        })
      }
    },
    showAccept(option) {
      if (this.assignedReviewers.reviewerVos) {
        const ans = this.assignedReviewers
          .reviewerVos
          .find(
            ele => ele.id === option.id
          )
        if (ans === undefined) return -1
        if (ans.inviteStatus === 3) return 3
        if (ans.inviteStatus === 0) return 0
        if (ans.inviteStatus === 2) return 2
        return 10
      }
    },
    async adminHandleSubmit(status) {
      const ans = await this.$API.adminHandleSubmit({
        'submitId': this.submitid,
        'status': status,
        'suggestion': this.adminSuggestion
      })
      if (ans.code === 200) {
        this.$message.success('结果上传成功')
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
