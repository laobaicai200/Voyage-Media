package com.gec.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author sen
 * @since 2024-09-21
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    //分页
    IPage<SysUser> selectPage(IPage<SysUser> page, @Param("vo") SysUserQueryVo userQueryVo);

    int getStatusById(Long id, Integer status);
}
