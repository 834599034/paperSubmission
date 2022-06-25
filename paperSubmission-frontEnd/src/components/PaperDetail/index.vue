<template>
  <el-collapse v-model="activeNames" @change="handleChange">
    <el-collapse-item title="基本信息" name="1">
      <el-input v-model="paper.title" class="dd" :disabled="uneditable">
        <template slot="prepend">论文标题</template>
      </el-input>
      <el-input v-if="mode!==3" v-model="paper.firstAuthorName" class="dd" :disabled="uneditable">
        <template slot="prepend">第一作者</template>
      </el-input>
      <el-input v-if="mode!==3" v-model="paper.otherAuthor" class="dd" :disabled="uneditable">
        <template slot="prepend">其他作者</template>
      </el-input>
      <el-input v-model="paper.primaryKey" class="dd" :disabled="uneditable">
        <template slot="prepend">关键字</template>
      </el-input>
    </el-collapse-item>
    <el-collapse-item title="摘要" name="2">
      <el-input v-model="paper.abstractContext" :disabled="uneditable" type="textarea" :autosize="{ minRows: 15 }" />
    </el-collapse-item>
    <el-collapse-item title="文件下载" name="3">
      <el-button type="primary" @click="downloadPaper">下载论文<i class="el-icon-download el-icon--right" /></el-button>
      <el-upload
        v-if="mode===1"
        class="upload-demo"
        drag
        :data="paperFileData"
        :file-list="paperFileList"
        :before-upload="handleBeforePaperUpload"
        :on-success="handlePaperUploadSuccess"
        action="https://paper-submission.oss-cn-shanghai.aliyuncs.com"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip">只能上传pdf/docx文件，大小限制100MB</div>
      </el-upload>
    </el-collapse-item>
  </el-collapse>
</template>
<script>
import { v4 as uuidv4 } from 'uuid' // ⇨ '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d'

export default {
  name: 'PaperDetail',
  props: {
    paper: {
      type: Object,
      required: true
    },
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
      // 3 隐去个人信息
    }
  },
  data() {
    return {
      activeNames: ['1', '2', '3'],
      // paper: {}
      paperFileList: [],
      paperFileData: {},
      ossPolicy: {
        // 'accessid': 'LTAI5tGM7GY1D2thLacrA1RF',
        // 'policy': 'eyJleHBpcmF0aW9uIjoiMjAyMi0wNi0wNFQxMjo0NzoxMS4zMzZaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCIyMDIyLTA2LTA0LyJdXX0=',
        // 'signature': 'BkTsZzMyX7ze2LUX2FGbdrjJP9Q=',
        // 'dir': '2022-06-04/',
        // 'host': 'https://paper-submission.oss-cn-shanghai.aliyuncs.com',
        // 'expire': '1654346831'
      }
    }
  },
  computed: {
    uneditable: function() {
      return this.mode !== 1
    }
  },
  methods: {
    downloadPaper() {
      window.open(this.paper.paperUrl, '_blank')
    },
    handleChange(val) {
      // console.log(val)
    },
    async handleBeforePaperUpload(file) {
      console.log(file)
      this.ossPolicy = (await this.$API.getOSSPolicy()).data
      this.paperFileData = {
        'key': this.ossPolicy.dir + uuidv4() + file.name,
        'policy': this.ossPolicy.policy,
        'OSSAccessKeyId': this.ossPolicy.accessid,
        // 设置服务端返回状态码为200，不设置则默认返回状态码204。
        'success_action_status': '200',
        'signature': this.ossPolicy.signature
      }
    },
    handlePaperUploadSuccess(res, file) {
      this.$message.success('文件上传成功')
      this.$parent.paper.paperUrl = this.ossPolicy.host + '/' + this.paperFileData.key
      console.log(res, file)
    }
  }
}
</script>
<style scope>
.dd{
  margin: 5px;
}
</style>
