import request from '@/utils/request'
const BASE_URL = '/admin/system/sysRole';
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
        url: `${BASE_URL}/removeRoleById/${id}`,
        method: 'delete', // 提交方式
    })
  },
  saveRole(role){
    return request({
        url: `${BASE_URL}/addRole`,
        method: 'post', // 提交方式为 post
        data: role // 传递json 数据
    })
  },
  updateRole(role)
    {
       return request({
        url: `${BASE_URL}/updateRole`,
        method: 'put', // 提交方式为 put
        data: role 
       })
    },
  getRoleById(id) {
    return request({
        url: `${BASE_URL}/findRoleById/${id}`,
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
  //根据用户id查询用户已分配的角色
  getRolesByUserId(userId) {
    return request({
      url: `${BASE_URL}/toAssign/${userId}`,
      method: 'get'
    })
  },
  
  //分配角色
  assignRoles(assginRoleVo) {
    return request({
      url: `${BASE_URL}/doAssign`,
      method: 'post',
      data: assginRoleVo
    })
  }
}