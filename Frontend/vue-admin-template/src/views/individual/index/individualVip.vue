<template>
    <div class="app-cont">
    <div class="vip-page">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-card shadow="hover" class="vip-card" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
            <div slot="header" class="clearfix">
              <span>普通VIP</span>
            </div>
            <div>
              <p><strong>月付: 30元</strong></p>
              <p><strong>季付: 60元</strong></p>
              <p><strong>年付: 180元</strong></p>
              <el-button type="primary" @click="handlePayment('normal')">立即购买</el-button>
            </div>
          </el-card>
        </el-col>
        <el-col :span="11" :offset="2">
          <el-card shadow="hover" class="vip-card" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
            <div slot="header" class="clearfix">
              <span>超级VIP</span>
            </div>
            <div>
              <p><strong>月付: 50元</strong></p>
              <p><strong>季付: 100元</strong></p>
              <p><strong>年付: 240元</strong></p>
              <el-button type="primary" @click="handlePayment('super')">立即购买</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
</div>
  </template>

<script>
export default {
  name: 'VipPage',
  data() {
    return {
      isHovered: false
    };
  },
  methods: {
    handlePayment(type) {
      alert(`选择了${type} VIP，请完成支付`);
      // 这里可以添加跳转到支付页面的逻辑
      // 或者显示支付二维码图片
    },
    handleMouseEnter() {
      this.isHovered = true;
    },
    handleMouseLeave() {
      this.isHovered = false;
    }
  }
}
</script>

<style scoped>
.app-cont {
  background-color: #010101; /* 设置灰色背景 */
  min-height: 100vh; /* 确保高度覆盖整个视口 */
  display: flex;
  justify-content: center;
  align-items: center;
}


.vip-page {
  background-color: black;
  color: gold;
  padding: 20px;
}

.vip-card {
  background-color: black;
  color: gold;
  width: 400px;
  border: 1px solid gold;
  transition: all 0.3s ease;
}

.vip-card:hover {
  background-color: red;
  color: white;
}

.clearfix .el-card__header {
  border-bottom: 1px solid gold;
}

.el-button--primary {
  background-color: gold !important;
  border-color: gold !important;
  color: black;
}

.el-button--primary:hover {
  background-color: red !important;
  border-color: red !important;
  color: white;
}

.vip-card .el-button--primary:hover {
  background-color: white !important;
  border-color: white !important;
  color: red;
}
</style>