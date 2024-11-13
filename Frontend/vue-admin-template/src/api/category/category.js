import request from '@/utils/request'

// 定义公共的api 
const BASE_URL = '/admin/system/sysCategory';

export default{

  findAll(){
    return request({
      url:`${BASE_URL}/findAll`,
      method: 'get'
    })
  },
  getCategoryPageInfo(page,limit,searchObj){
    return request({
      url:`${BASE_URL}/${page}/${limit}`,
      method: 'get',
      params:searchObj
    })
  },

  removeCategoryById(id){
    return request({
      url:`${BASE_URL}/removeCategory/${id}`,
      method: 'delete'
    })
  },
  addCategory(category){
    return request({
      url:`${BASE_URL}/addCategory`,
      method: 'post',
      data:category
    })
  },
  updateCategory(category){
    return request({
      url:`${BASE_URL}/updateCategory`,
      method: 'post',
      data:category
    })
  },
  findCategoryById(id){
    return request({
      url:`${BASE_URL}/findCategoryById/${id}`,
      method: 'get'
    })
  }
  
}