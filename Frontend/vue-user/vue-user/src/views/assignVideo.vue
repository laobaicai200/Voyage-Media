<template>
  <div>
    <!-- 定义播放器dom -->
    
    <div id="J_prismPlayer" class="prism-player" />
  </div>

  
</template>


<script>
import api from "@/api/movie/movie.js";
import aliplayer from "aliplayer";
export default {
  data() {
    return {
      playInfo: {
        image:'',
        playId: "",
        playAuth: "",
        movie: {},
        danmukuList:{},
      },
      
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      const id = this.$route.query.id;
      // alert(id);
      api.getPlayAuthById(id).then((response) => {
        console.log(response);
        this.playInfo.image = response.data.image;
        this.playInfo.playId = response.data.playId;
        this.playInfo.playAuth = response.data.playAuth;

        api.getMovieById(id).then((response) => {
        console.log(response);
        this.movie=response.data;})
        

        let player = new Aliplayer({
                        id: 'J_prismPlayer',
                        width: '100%',
                        height: '500px',
                        autoplay: false,
                        controlBarVisibility: 'hover',
                        isLive: false,
                        cover: this.playInfo.image,//图片
                        vid: this.playInfo.playId,//视频id
                        playauth: this.playInfo.playAuth,//视频播放秘钥
                        encryptType: 1, //是否加密播放
                        components: [{
      name: 'AliplayerDanmuComponent',
      type: AliPlayerComponent.AliplayerDanmuComponent,
      args: [danmukuList]
    }],
                    }, function (player) {
                        console.log('播放器创建好了。')
                    });

      });
    },
  },
  mounted() {
    
    console.log(this.playInfo.playId);

    console.log(this.playInfo.playAuth);

    /* eslint-disable no-undef */ //忽略 no-undef 检测
  },
};
</script>
