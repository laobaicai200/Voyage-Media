import request from '@/utils/request'
const BASE_URL = '/admin/system/sysUser';
export default {
  // 查询分页列表
  getPageList(page, size, searchObj) {
        return request({
            url: `${BASE_URL}/${page}/${size}`,
            method: 'get',
            params: searchObj
        })
  },
  // 删除的方法
  removeId(id){
    return request({
        url: `${BASE_URL}/removeUserById/${id}`,
        method: 'delete', // 提交方式
    })
  },
  // 添加用户
  saveUser(User){
    return request({
        url: `${BASE_URL}/addUser`,
        method: 'post', // 提交方式为 post
        data: User // 传递json 数据
    })
  },
  // 修改用户
  updateUser(User)
    {
       return request({
        url: `${BASE_URL}/updateUser`,
        method: 'put', // 提交方式为 put
        data: User 
       })
    },
    // 根据id查询
  getUserById(id) {
    return request({
        url: `${BASE_URL}/findUserById/${id}`,
        method: 'get'
    })
  },
  //批量 删除
  bactchremoveId(ids) {
    return request({
      url: `${BASE_URL}/batchRemove`,
      method: 'delete', // 提交方式
      data: ids //传递的json格式
    })
  },
  //修改用户状态
  updateStatusById(id,status) {
    return request({
      url: `${BASE_URL}/updateStatusById/${id}/${status}`,
      method: 'get'
    })
  },

  


}