<template>
  <div class="app-cont">
    <header class="header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="启航电影院 Logo" />
        <h1 class="title">启航电影院</h1>
      </div>
      <div class="line"></div>
      <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
        style="width: auto;"
        background-color="#1a1a1a" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="1">
          <router-link to="/">
            首页
          </router-link>
        </el-menu-item>
        <el-submenu index="2">
          <template slot="title">我的</template>
          <router-link to="/individual/index">
            <el-menu-item index="2-1">     
              个人信息
            </el-menu-item>
          </router-link>
          <router-link to="/individual/vip">
            <el-menu-item index="2-2">vip充值</el-menu-item>
          </router-link>
          <el-menu-item index="2-3">选项3</el-menu-item>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项1</el-menu-item>
            <el-menu-item index="2-4-2">选项2</el-menu-item>
            <el-menu-item index="2-4-3">选项3</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="3" disabled>消息中心</el-menu-item>
        <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
      </el-menu>
    </header>

    <div class="app-container">
      <el-form ref="form" :model="sysUser" label-width="120px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="sysUser.password" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="sysUser.phone" />
        </el-form-item> 
        <el-form-item label="头像" prop="url">
          <el-upload action="" list-type="picture-card"
            :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        
        <div class="upload-buttons-container">
          <el-button type="primary" @click="updateUser">保存</el-button>
          <el-button @click="onCancel">取消</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import api from '@/api/user/user'
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      sysUser: {},
      dialogVisible: false,
      dialogImageUrl: ''
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      api.getUserById(1).then(response => {
        this.sysUser = response.data;
      })
    },
    updateUser() {
      api.updateUser(this.sysUser)
        .then(response => {
          // 给出提示框
          this.$message({
            type: "success",
            message: "保存成功!",
          });
          this.onCancel();
        })
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    onCancel() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.app-cont {
  background-color: #504545; /* 设置灰色背景 */
  min-height: 100vh; /* 确保高度覆盖整个视口 */
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 3px;
  background-color: #222;
  width: 100%;
}

.logo {
  display: flex;
  align-items: center;
  margin-left: 20px;

  img {
    height: 50px;
    margin-right: 10px;
  }

  .title {
    font-size: 24px;
    font-weight: bold;
    color: #fff;
  }
}

.line {
  flex: 1;
  height: 1px;
  background-color: #444;
  margin: 0 20px;
}

.el-menu-demo {
  display: flex;
  justify-content: center;
  width: 100%;
}

.el-menu-demo > .el-menu-item,
.el-menu-demo > .el-submenu__title {
  padding: 0 20px;
}

.app-container {
  max-width: 600px;
  width: 100%;
  margin: 20px auto;
  padding: 20px;
  background: #121212; /* 黑色背景 */
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  color: #ffffff; /* 文字颜色 */
}

.el-form {
  margin-bottom: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input {
  width: calc(100% - 120px); /* 调整宽度 */
  background: #222222; /* 输入框背景 */
  color: #ffffff; /* 输入框文字颜色 */
}

.upload-buttons-container {
  display: flex;
  margin-left: 21%;
  margin-top: 20px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #444444;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  margin-bottom: 20px;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  background: #222222; /* 图标背景 */
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.el-button {
  margin-right: 10px;
  background: #409EFF; /* 按钮背景 */
  color: #ffffff; /* 按钮文字颜色 */
}

.el-button:hover {
  background: #4a90e2; /* 按钮悬停背景 */
}

.el-button--primary {
  background: #409EFF;
  color: #ffffff;
}

.el-button--primary:hover {
  background: #4a90e2;
}

.el-input__inner {
  background: #222222;
  color: #ffffff;
}

.el-form-item__label {
  color: #ffffff;
}

.el-form-item__content {
  color: #ffffff;
}

.el-input__prefix i,
.el-input__suffix i {
  color: #ffffff;
}

.el-input__inner:focus {
  border-color: #409EFF;
}

.el-message-box {
  background: #121212;
  color: #ffffff;
}

.el-message-box__header {
  color: #ffffff;
}

.el-message-box__title {
  color: #ffffff;
}

.el-message-box__btns .el-button {
  background: #409EFF;
  color: #ffffff;
}

.el-message-box__btns .el-button:hover {
  background: #4a90e2;
}

.el-dialog__body {
  color: #ffffff;
}

.el-dialog__footer {
  color: #ffffff;
}

.el-dialog__header {
  color: #ffffff;
}

.el-dialog__title {
  color: #ffffff;
}
</style>