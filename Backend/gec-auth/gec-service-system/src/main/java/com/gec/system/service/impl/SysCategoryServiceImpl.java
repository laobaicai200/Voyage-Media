package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysCategory;

import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.system.mapper.SysCategoryMapper;

import com.gec.system.service.SysCategoryService;
import org.springframework.stereotype.Service;


@Service
public class SysCategoryServiceImpl extends ServiceImpl<SysCategoryMapper, SysCategory> implements SysCategoryService {
    @Override
    public IPage<SysCategory> selectPage(IPage iPage, SysCategoryQueryVo sysCategoryQueryVo) {
        return baseMapper.selectPage(iPage,sysCategoryQueryVo);
    }
}
