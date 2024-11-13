package com.gec.system.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysMovie;
import com.gec.model.system.SysRole;
import com.gec.model.vo.SysMovieQueryVo;
import com.gec.system.mapper.SysMovieMapper;
import com.gec.system.mapper.SysRoleMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.service.SysMovieService;
import com.gec.system.service.SysRoleService;
import org.springframework.stereotype.Service;


@Service
public class SysMovieServiceImpl extends ServiceImpl<SysMovieMapper, SysMovie> implements SysMovieService {

    @Override
    public Page<SysMovie> selectPage(Page page1, SysMovieQueryVo sysMovieQueryVo) {
        return baseMapper.selectPage(page1, sysMovieQueryVo);
    }
}
