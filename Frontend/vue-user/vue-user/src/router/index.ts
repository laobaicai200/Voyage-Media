import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import dashboard from '../views/dashboard.vue'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'home',
    component: dashboard
  },

  {
    path: '/assignVideo',
    name: 'assignVideo',
    component: () => import('../views/assignVideo.vue'),
  },
  

  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
    props: true
  },

  //动漫
  {
    path: '/anime',
    name: 'anim',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/Anime.vue')
  },
  //电影
  {
    path: '/film',
    name: 'film',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/film.vue')
  },
  //纪录片
  {
    path: '/documentary',
    name: 'documentary',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/documentary.vue')
  },
  //综艺
  {
    path: '/variety',
    name: 'variety',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/variety.vue')
  },
  //电视剧
  {
    path: '/TVplay',
    name: 'TVplay',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/TVplay.vue')
  },
  //网剧
  {
    path: '/drama',
    name: 'drama',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/drama.vue')
  },
  //肥皂剧
  {
    path: '/SoapOpera',
    name: 'SoapOpera',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/SoapOpera.vue')
  },

  //vip界面
  {
    path: '/vip',
    name: 'vip',
    component: () => import(/* webpackChunkName: "about" */ '../views/Vip/vip.vue')
  }



 
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
