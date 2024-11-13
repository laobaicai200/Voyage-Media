<template>
  <div class="home-container">
    <header class="header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="启航电影院 Logo" />
        <h1 class="title">启航电影院</h1>
      </div>
      <div class="line"></div>
      <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
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

    
    <main class="main">
      <section class="hero">
        <el-carousel height="400px" type="card">
          <el-carousel-item v-for="(slide, index) in slides" :key="index">
            <img :src="slide.image" alt="Slide Image" style="width: 100%; height: 100%;">
          </el-carousel-item>
        </el-carousel>
      </section>

      <section class="featured-movies">
        <h2 class="section-title">热门电影</h2>
        <div class="movie-list">
          <div class="movie" v-for="(movie, index) in movies" :key="index">
            <img :src="movie.poster" :alt="movie.title" />
            <h3>{{ movie.title }}</h3>
            <p>{{ movie.description }}</p>
          </div>
        </div>
      </section>
    </main>

    <footer class="footer">
      <p>&copy; 2023 启航电影院</p>
    </footer>
  </div>
</template>

<script>

export default {
  data() {
    return {
      slides: [
        { image: require('@/assets/slide1.jpeg') },
        { image: require('@/assets/slide2.jpeg') },
        { image: require('@/assets/slide3.jpg') }
      ],
      movies: [
        {
          title: '电影 A',
          description: '这是电影 A 的简介',
          poster: '@/assets/165958.jpg',
        },
        {
          title: '电影 B',
          description: '这是电影 B 的简介',
          poster: '@/assets/165958.jpg'
        },
        {
          title: '电影 C',
          description: '这是电影 C 的简介',
          poster: '@/assets/165958.jpg'
        }
      ]
    };
  },
  methods: {
    showMovies() {
      this.$router.push('/movies');
    }
  }
};
</script>

<style lang="scss" scoped>
.home-container {
  font-family: 'Arial', sans-serif;
  color: #fff;
  background-color: #1a1a1a;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 3px;
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

    .navigation {
      a.nav-link {
        margin-left: 20px;
        text-decoration: none;
        color: #fff;
        font-weight: bold;
        transition: color 0.3s ease;

        &:hover {
          color: #aaa;
        }
      }
    }
  }

  .main {
    padding: 20px;

    .hero {
      text-align: center;
      padding: 40px;

      .hero-title {
        font-size: 32px;
        font-weight: bold;
        color: #fff;
      }

      .hero-description {
        font-size: 18px;
        margin-top: 10px;
        color: #aaa;
      }

      .hero-button {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
        margin-top: 20px;
        transition: background-color 0.3s ease;

        &:hover {
          background-color: #0056b3;
        }
      }
    }

    .featured-movies {
      margin-top: 40px;

      .section-title {
        font-size: 24px;
        font-weight: bold;
        color: #fff;
        margin-bottom: 20px;
      }

      .movie-list {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;

        .movie {
          flex: 1 1 200px;
          background-color: #333;
          padding: 20px;
          border-radius: 5px;

          img {
            width: 100%;
            height: auto;
            margin-bottom: 10px;
          }

          h3 {
            font-size: 18px;
            margin-bottom: 10px;
            color: #fff;
          }

          p {
            font-size: 14px;
            color: #aaa;
          }
        }
      }
    }
  }

  .footer {
    text-align: center;
    padding: 20px;
    background-color: #222;
    color: #aaa;
  }
}
</style>