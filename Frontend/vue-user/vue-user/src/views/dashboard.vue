<template>

  <div class="home">
    <main class="main">
      <section class="hero">
        <el-carousel height="400px" type="card">
          <el-carousel-item v-for="(slide, index) in slides" :key="index">
            <img :src="slide.image" alt="Slide Image" style="width: 100%; height: 100%;">
          </el-carousel-item>
        </el-carousel>
      </section>

      <section class="featured-movies">
        <h2 class="section-title">正在热播</h2>
        <div class="movie-list" >
          <div class="movie" v-for="(movie, index) in movies" :key="index">
            <img
            :src="movie.image"
            :alt="movie.title"
            class="movie-image"
            :class="{ highlighted: selectedMovie === movie.id }"
            @click="handleClick(movie)"
        >
            <h3>{{ movie.name }}</h3>
            <p>{{ movie.keyword }}</p>
          </div>
        </div>
      </section>
    </main>

    <footer class="footer">
      <p>&copy; 2024 启航电影院</p>
    </footer>
  </div>
</template>

<script>
import api from "@/api/movie/movie.js";
export default{data() {
    return {
      dialogVisible: false,
      total: 0,
      page: 1,
      pages: 2,
      limit: 6,
      limits: 10,
      slides: {},
      // 多选
      movies: []

    }
  }

  ,methods: {
    showMovies() {
      this.$router.push('/movies');
    },
    dispatch(){
      api.getMoviePageInfo(this.pages, this.limits, this.movies).then((response) => {
          console.log(response);
          this.movies = response.data.records;
          console.log(this.slides);
        });
    },
    fetchPageList(page = 3) {
      this.page = page;
      api.getMoviePageInfo(this.page, this.limit, this.slides).then((response) => {
          console.log(response);
          this.slides = response.data.records;
          console.log(this.slides);
        });
    },
    handleClick(movie) {
      this.$message({
        message: '点击了' + movie.name,
        type: 'success'
      });
      console.log(movie.id);
      this.$router.push({ name: 'assignVideo', query: { id: movie.id } });
},
    created() {
      this.fetchPageList();
    },
  },
  created() {
    //this.listLoading=true;
    this.dispatch();
    this.fetchPageList();
  },
}


</script>

<style scoped lang="less">

.home{
  background-color: #222;
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
            height: 130px;
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
  

  .footer {
    text-align: center;
    padding: 20px;
    background-color: #222;
    color: #aaa;
  }
</style>
