//全局请求处理
import axios from 'axios';
import { MessageBox, Message } from 'element-ui';


// 创建 Axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // 基础 URL
  timeout: 5000 // 请求超时时间
});




// 响应拦截器
service.interceptors.response.use(
  response => {
    console.log('response', response);
    const res = response.data;
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      });

      

      return Promise.reject(new Error(res.message || 'Error'));
    } else {
      return res;
    }
  },
  error => {
    console.log('err' + error); // 错误调试
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;