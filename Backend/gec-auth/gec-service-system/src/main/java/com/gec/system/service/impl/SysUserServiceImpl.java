package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.RouterVo;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.mapper.SysUserMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysRoleService;
import com.gec.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sen
 * @since 2024-09-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleService SysRoleService;

    //分页查询用户列表
    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo){
        return this.baseMapper.selectPage(iPage,sysUserQueryVo);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        int byId = this.baseMapper.getStatusById(id, status);
        return byId > 0;
    }

    @Override
    public SysUser getUserInfoUserName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("username",username);
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        //1.根据用户名称查询用户信息
        SysUser sysUser = this.getUserInfoUserName(username);
        //2.根据用户id查询菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

        //3.封装数据返回
        Map<String, Object> result = new HashMap<>();
        result.put("name",sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles",  "[admin]");
        result.put("routers", routerVoList);
        result.put("buttons", permsList);
        return result;
    }

    //添加一个user时，给予一个普通用户的这类默认角色

    @Override
    public boolean save(SysUser entity) {
        //保存
        super.save(entity);
        //查询
        SysUser byId = getById(entity.getId());
        //给用户赋予角色
        AssginRoleVo assginRoleVo = new AssginRoleVo();
        //默认普通用户
        ArrayList<Long> list = new ArrayList<>();
        list.add(34l);
        assginRoleVo.setUserId(byId.getId());
        assginRoleVo.setRoleIdList(list);

        SysRoleService.doAssign(assginRoleVo);
        return true;
    }


}
