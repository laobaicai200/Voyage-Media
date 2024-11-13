import request from '@/utils/request'

// 定义公共的api 
const BASE_URL = '/admin/system/sysMovie';


export default{
  // 当前 页码  每页条数   搜索条件
  getMoviePageInfo(page,limit,searchObj){
    return request({
      url:`${BASE_URL}/${page}/${limit}`,
      method: 'get',
      params:searchObj
    })
  },
  // 根据id移除一个movie
  removeMovieById(id){
    return request({
      url:`${BASE_URL}/removeMovie/${id}`,
      method: 'delete'
    })
  },
  // 添加影视
  addMovie(movie){
    console.log(movie);
    return request({
      url:`${BASE_URL}/addMovie`,
      method: 'post',
      data:movie
    })
  },
  // 更新影视
  updateMovie(movie){
    return request({
      url:`${BASE_URL}/updateMovie`,
      method: 'post',
      data:movie
    })
  },
  // 根据id获取一个movie
  getMovieById(id){
    return request({
      url:`${BASE_URL}/findMovieById/${id}`,
      method: 'get'
    })
  },
  // 根据id获取palyAuth播放凭证
  getPlayAuthById(id){
    return request({
      url:`${BASE_URL}/getPlayAuth/${id}`,
      method: 'get'
    })
  }

}