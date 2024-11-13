<template>
    <div class="app-cont">
        <div class="vip-info">
      
    </div>
    <div class="vip-page" style="margin-top: -280px;">
        <h3 style="color: darkgoldenrod;">充值vip立刻赢取超多权益</h3>
      <ul style="color: darkgoldenrod;">
        <li>会员专享电影、剧集、动漫等资源</li>
        <li>积分日：在积分日活动期间，可获得专属积分福利</li>
        <li>专属VIP服务：达到指定等级，可享受专属VIP服务</li>
      </ul>
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

    <el-dialog title="购买" :visible.sync="dispath" width="40%">
      <img src="../../assets/cd7.jpg"style="width: 50%; height: auto;" />
    <el-button @click="cancel()" size="small" icon="el-icon-refresh-right">取 消</el-button>
    <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">已支付成功</el-button>
      
    </el-dialog>
    
</div>
  </template>

<script>
import api from '@/api/user'
export default {
    
  name: 'VipPage',
  data() {
    return {
      isHovered: false,
      dispath: false
    };
  },
  methods: {
    saveOrUpdate(){
        this.dispath= false
    },
    cancel(){
        this.dispath= false
    },
    handlePayment(type) {
      alert(`选择了${type} VIP，请完成支付`);
      // 这里可以添加跳转到支付页面的逻辑
      // 或者显示支付二维码图片
      this.dispath= true;
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