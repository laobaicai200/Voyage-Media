package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author sen
 * @since 2024-09-21
 */
public interface SysUserService extends IService<SysUser> {
    //分页
    IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo);

    boolean updateStatus(Long id, Integer status);

    SysUser getUserInfoUserName(String username);

    Map<String, Object> getUserInfo(String username);
}
