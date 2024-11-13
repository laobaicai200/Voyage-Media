package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysRole;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.annocation.log;
import com.gec.system.service.SysRoleService;
import com.gec.system.service.SysUserService;
import com.gec.util.MD5Helper;
import com.gec.util.Result;
import com.gec.util.ResultCodeEnum;
import io.github.classgraph.utils.WorkQueue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sen
 * @since 2024-09-21
 */
@Api(tags = "用户管理控制器")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;


    //分页和条件
    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation("用户分页查询")
    @GetMapping("/{page}/{size}")
    @log(type = "分页查询用户")
    public Result selectUserPageByVo(@PathVariable Long page, @PathVariable Long size, SysUserQueryVo sysUserQueryVo){
        IPage page1 = new Page(page, size);
        IPage iPage = sysUserService.selectPage(page1, sysUserQueryVo);
        return Result.ok(iPage);
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("/removeUserById/{id}")
    @log(type = "删除用户")
    public Result removeRole(@PathVariable Long id){
        boolean isRemove = sysUserService.removeById(id);
        return isRemove?Result.ok(isRemove):Result.fail(isRemove);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation("添加用户记录")
    @PostMapping("/addUser")
    @log(type = "添加用户")
    public Result addRole(@RequestBody SysUser sysUser){
        //加密
        String encrypt = MD5Helper.encrypt(sysUser.getPassword());
        //返回
        sysUser.setPassword(encrypt);
        //保存
        boolean isAdd = sysUserService.save(sysUser);
        return isAdd?Result.ok(isAdd):Result.fail(isAdd);
    }


    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("修改用户记录")
    @PutMapping("/updateUser")
    @log(type = "修改用户")
    public Result updateRole(@RequestBody SysUser sysUser){
        //加密
        String encrypt = MD5Helper.encrypt(sysUser.getPassword());
        //返回
        sysUser.setPassword(encrypt);
        //保存
        boolean isUpdate = sysUserService.updateById(sysUser);
        return isUpdate?Result.ok(isUpdate):Result.fail(isUpdate);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    @log(type = "批量删除用户")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean isRemove = sysUserService.removeByIds(idList);
        return isRemove?Result.ok(isRemove):Result.fail(isRemove);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation("根据id查询")
    @GetMapping("/findUserById/{id}")
    @log(type = "根据id查询用户")
    public Result get(@PathVariable Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("根据id修改状态码")
    @GetMapping("/updateStatusById/{id}/{status}")
    @log(type = "根据id修改状态码")
    public Result getStatusById(@PathVariable Long id,@PathVariable Integer status){
        System.out.println("id:"+id+"status:"+status);
        boolean isOk = sysUserService.updateStatus(id, status);
        return isOk? Result.ok():Result.fail();
    }


}

