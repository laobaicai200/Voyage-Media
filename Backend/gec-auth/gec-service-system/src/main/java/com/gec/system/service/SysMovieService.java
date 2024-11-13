package com.gec.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysMovie;
import com.gec.model.vo.SysMovieQueryVo;

public interface SysMovieService extends IService<SysMovie> {
    Page<SysMovie> selectPage(Page<SysMovie> page1, SysMovieQueryVo sysMovieQueryVo);
}
