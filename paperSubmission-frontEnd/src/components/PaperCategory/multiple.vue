<template>
  <!-- <el-form-item label="文章类别"> -->
  <el-select :value="value" multiple placeholder=" 请选择类别" @input="$emit('input', $event)">
    <el-option v-for="item in paperTypes" :key="item.id" :label="item.typeName" :value="item.id" />
  </el-select>
  <!-- </el-form-item> -->
</template>

<script>
export default {
  name: 'PaperCategoryMultiple',
  props: {
    value: [Array]
  },
  data() {
    return {
      paperTypes: []
    }
  },
  async created() {
    // console.log(this.$parent)
    this.paperTypes = (await this.$API.paperType()).data
  },
  methods: {
    selectChanged(newValue) {
      this.typeid = newValue
      // this.$emit('')
    }
  }
}
</script>
