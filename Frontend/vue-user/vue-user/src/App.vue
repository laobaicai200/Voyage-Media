<template>
  <div id="app">
    <nav>
      <div class="home-container">

        <!-- 顶部导航栏组件 -->
        <header class="header">
          <div class="logo">
            <img src="@/assets/logo.png" alt="启航电影院 Logo" />
            <h1 class="title">启航电影院</h1>
          </div>
          <div style="margin-left: 40px;"></div>
          <div class="search-container">
            <el-input placeholder="请输入电影名" v-model="movie.name" style="margin-left: 100px;"/>
          </div>
          <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
            background-color="#222" text-color="#fff" active-text-color="#87CEEB" :data="activeImages">
            <div></div>
            <el-menu-item index="1">
              <router-link to="/" class="no-underline">
                首页
              </router-link>
            </el-menu-item>
            
            <el-menu-item index="2" style="color: #ffd04b !important;">
              <router-link to="/vip" class="no-underline">
              Vip权益中心
            </router-link>
            </el-menu-item>
            <el-menu-item index="3">
                吃点东西
            </el-menu-item>
            <el-submenu index="4">
              <template slot="title">
                <div>
                  <el-avatar :src="activeImages.head_url"></el-avatar>
                </div>
              </template>
              <el-menu-item index="2-1" @click="showLogin()">{{logintitle}}</el-menu-item>
              <el-menu-item index="2-2" style="color: #ffd04b !important;">vip权益中心</el-menu-item>
              <el-menu-item index="2-3">个人中心</el-menu-item>
              <el-menu-item index="2-4" @click="logout()">
                退出
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </header>
        <!-- 侧导航栏组件 -->
      </div>
    </nav>
    <div class="container">
      <el-row class="tac ">
        <el-col :span="12">
          <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
            background-color="#545c64" text-color="#fff" active-text-color="#87CEEB">

            <router-link to="/film" class="no-underline" style="color: aliceblue;">
            <el-menu-item index="1">
                  电影
            </el-menu-item>
          </router-link>
            
              <router-link to="/anime" class="no-underline" style="color: aliceblue;">
                <el-menu-item index="2">
                  动漫
                </el-menu-item>
            </router-link>
            

            <router-link to="/TVplay" class="no-underline" style="color: aliceblue;">
                <el-menu-item index="3">
                  电视剧
                </el-menu-item>
            </router-link>
            
              <router-link to="/variety" class="no-underline" style="color: aliceblue;">
                <el-menu-item index="4">
                  综艺
                </el-menu-item>
            </router-link>

            <router-link to="/drama" class="no-underline" style="color: aliceblue;">
                <el-menu-item index="5">
                  网剧
                </el-menu-item>
            </router-link>

            <router-link to="/documentary" class="no-underline" style="color: aliceblue;">
                <el-menu-item index="6">
                  纪录片
                </el-menu-item>
            </router-link>

            <router-link to="/SoapOpera" class="no-underline" style="color: aliceblue;">
                <el-menu-item index="6">
                  肥皂剧
                </el-menu-item>
            </router-link>

            <router-link to="/vip" class="no-underline" style="color: #ffd04b;" >
            <el-menu-item index="9">
              <span slot="title" style="color: #ffd04b;">vip权益中心</span>
            </el-menu-item>
          </router-link>

          </el-menu>
        </el-col>
      </el-row>
      <div id="main-content" class="main-content">
      <div class="content">
        <!-- 主要内容显示 -->
        <router-view />
      </div>

      <!-- 登录的表格 -->
      <el-dialog :title="登录" :visible.sync="dialogVisible" width="30%">
      <el-form ref="dataForm" v-model="user" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="user.password" />
        </el-form-item>
      </el-form>
      <!-- 注册的按钮 -->   
      <span slot="footer" class="dialog-footer" >
        <el-button @click="showRegist()" type="text" size="small">没有账号?点击立即注册</el-button>
        <el-button @click="cancel()" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 注册的表格 -->
    <el-dialog title="注册" :visible.sync="regist"  width="30%">
      <el-form ref="dataForm" v-model="user" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="user.password" />
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="user.password" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="user.phone" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" > 
        <el-button @click="cancel()" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="Regist()" size="small">注 册</el-button>
      </span>
    </el-dialog>


    </div>
    </div>
  
    
     

  </div>
</template>

<script>
import api from '@/api/user.js'
import { Form } from 'element-ui';
export default {

  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1',
      limit: 3,
      activeImages: { head_url: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" },
      movie: {},
      user:{},
      logintitle: '登录',
      token:{},
      dialogVisible: false,
      regist: false
    };
  },
  methods: {

    Regist(){

       api.regist(this.user).then((response) => {
        this.dialogVisible = false;
        this.$message({
          message: '注册成功,请重新登录',
          type: 'success'
        })
        cancel()
      }).catch((error) => {

      });
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },

    cancel(){
      this.regist = false;
      this.dialogVisible = false
    },

    showRegist(){
      this.dialogVisible = false
      this.regist = true;
    },
    logout() {
      this.token = {};
      this.logintitle='登录';
      this.$message({
          message: '已经成功退出',
          type: 'success'
        });
    },
    
    showLogin() {
      this.dialogVisible = true;
    },
    saveOrUpdate(){
      console.log(this.user);
      api.login(this.user).then((response) => {
        console.log(response);
        this.token = response.data.token;
        console.log(this.token);
        this.$message({
          message: '登录成功',
          type: 'success'
        });
      }).catch((error) => {
        console.log(error);
      }).finally(() => {
        this.dialogVisible = false;
        console.log(this.token)
      })

    }
    },
    watch: {
      token(){
        console.log("监听器已经执行");
        console.log(this.token);
        console.log(this.logintitle);
        deep: true;
        if(this.token!=null){
          this.logintitle='切换账号';
        }else{
          this.logintitle='登录';
        }
      },
      deep: true  
}
  }


</script>

<style lang="less">
body{
  background-color: #1c1919;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #222;

  .search-container {
    width: 30%;
    background-color: #222;
    /* 灰色背景 */
    padding: 10px;
    border-radius: 4px;
    display: flex;
    align-items: center;
  }

  .search-input {
    opacity: 0.8;
    /* 调整透明度 */
    background-color: transparent;
    /* 背景透明 */
    border: 1px solid #222;
    /* 边框颜色 */
    border-radius: 4px;
    padding: 8px 12px;
    transition: opacity 0.3s ease-in-out;
  }

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: -1px;
    background-color: #222;

    .logo {
      display: flex;
      align-items: center;

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
  }


  .no-underline {
    text-decoration: none !important;
  }
}

/* 容器 */
.container {
    display: flex;
    
    height: 100vh;
}

.container .tac {
    background-color: #1c1919 ;
}

.container .el-menu-vertical-demo {
    width: 150px;
    float: left;
}

.container .main-content {
  flex-grow: 1;
    padding: 20px;
    background-color: #1c1919;
}

/*清除浮动*/
.container:after {
  content: "";
  height: 0;
  line-height: 0;
  display: block;
  visibility: hidden;
  clear: both;
}
</style>
