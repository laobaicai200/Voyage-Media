import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
import Layout from '@/layout'
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' },
    },
  ]
  },

  
  {
    path: '/individual',
    component: Layout,
    hidden: true,
    children: [
          {
            path: 'index',
            name: 'index',
            // 动态导入组件
            component: () => import('@/views/individual/index/index.vue'),
            meta: { title: '个人信息', icon: 'dashboard' }, // 元数据配置
            hidden: true,
          },
          {
            // 子路径配置
            path: 'vip',
            name: 'vip',
            // 动态导入组件
            component: () => import('@/views/individual/index/individualVip.vue'),
            meta: { title: 'vip信息', icon: 'dashboard' }, // 元数据配置
            hidden: true,
          },
        ],
   },

//  {
//     path: '/system',  // 父路径
//     component: Layout,
//     redirect: '/system/sysUser', // 直接输入system 会重定向到 
//     name: 'system',
//     meta: { title: '系统管理', icon: 'el-icon-s-help' },
//     children: [
//       {
//         path: 'sysRole', // 子路径  注意这里不要加斜线 
//         name: 'sysRole',
//         component: () => import('@/views/system/sysRole/list'),
//         meta: { title: '角色管理', icon: 'el-icon-s-custom' }
//       },
//       {
//         path: 'sysUser',  // 子路径
//         name: 'Tree',
//         component: () => import('@/views/system/sysUser/list'),
//         meta: { title: '用户管理', icon: 'el-icon-user-solid' }
//       },
//       {
//       name: 'sysMenu',
//       path: 'sysMenu',
//       component: () => import('@/views/system/sysMenu/list'),
//       meta: {
//         title: '菜单管理',
//         icon: 'el-icon-s-unfold'
//       },
//     },
//     {
//       path: 'assignAuth',
//       component: () => import('@/views/system/sysRole/assignAuth'),
//       meta: {
//         activeMenu: '/system/sysRole',
//         title: '角色授权'
//       },
//       hidden: true,
//     }
//     ]
//   },
//   { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}
export default router

