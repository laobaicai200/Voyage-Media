import request from '@/utils/request'

/*
菜单管理相关的API请求函数
*/
const BASE_URL = '/admin/system/sysMenu'

export default {

  /*
  获取权限(菜单/功能)列表
  */
  findNodes() {
    return request({
      url: `${BASE_URL}/findNodes`,
      method: 'get'
    })
  },

  /*
  删除
  */
  removeById(id) {
    return request({
      url: `${BASE_URL}/removeMenu/${id}`,
      method: "delete"
    })
  },

  /*
  保存
  */
  save(sysMenu) {
    return request({
      url: `${BASE_URL}/addMenu`,
      method: "post",
      data: sysMenu
    })
  },

  /*
  更新
  */
  updateById(sysMenu) {
    return request({
      url: `${BASE_URL}/updateMenu`,
      method: "post",
      data: sysMenu
    })
  },

  //角色分配权限相关

  //查询
  toAssign(roleId){
    return request({
      url: `${BASE_URL}/toAssign/${roleId}`,
      method: 'get'
    })
  },

  //分配
  doAssign(assignMenuVo){
    return request({
      url: `${BASE_URL}/doAssign`,
      method: 'post',
      data: assignMenuVo
    })
  }
}