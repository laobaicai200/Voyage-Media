package com.gec.system.mapper;

import com.gec.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuLogMapper extends BaseMapper<SysMenu> {

}