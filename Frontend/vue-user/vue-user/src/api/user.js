import request from '@/utils/request'


const BASE_URL = '/api/admin/system/index';


export default{
  login(data) {
  return request({
    url: `${BASE_URL}/login`,
    method: 'post',
    data
  })
},

 logining(username, password) {
  return request({
    url: `${BASE_URL}/login`,
    method: 'post',
    
  })
},

 getInfo(token) {
  return request({
    url: '/api/admin/system/index/info',
    method: 'get',
    params: { token }
  })
  
},

 regist(data) {
    return request({
      url: '/api/admin/system/index/Register',
      method: 'post',
    data
    })
  },

 logout() {
  return request({
    url: '/api/vue-admin-template/user/logout',
    method: 'post'
  })
}
}