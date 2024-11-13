<template>
  
    <section class="featured-movies">
      <h2 class="section-title">电视剧</h2>
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
  


</template>

<script>
import api from "@/api/movie/movie.js";
export default{data() {
  return {
    dialogVisible: false,
    total: 0,
    page: 1,
    categoryId: 1,
    // 多选
    movies: {}

  }
}

,methods: {
  showMovies() {
    this.$router.push('/movies');
  },
  dispatch(){
    api.getfindAll(this.categoryId).then((response) => {
        console.log(response);
        this.movies = response.data;
        console.log(this.slides);
      });
  },
  
  handleClick(movie) {
    this.$message({
      message: '点击了' + movie.name,
      type: 'success'
    });
    this.$router.push({ name: 'assignVideo', query: { id: movie.id } });
  },
  
  created() {
    this.dispatch();
  },
},
created() {
  //this.listLoading=true;
  this.dispatch();
  
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
