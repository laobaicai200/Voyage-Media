package com.gec.system.controller;


import com.gec.model.system.SysMenu;
import com.gec.model.vo.AssginMenuVo;
import com.gec.system.annocation.log;
import com.gec.system.service.SysMenuService;
import com.gec.util.Result;
import com.gec.util.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Api(tags = "菜单管理控制器")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {


    
    @Autowired
    private SysMenuService sysMenuService;

    // 加载树形菜单
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    @log(type = "得到菜单列表")
    public Result findNodes() {
        List<SysMenu> menuList=  this.sysMenuService.findNodes();
        return Result.ok(menuList);
    }

    @ApiOperation("根据id去查询菜单")
    // 根据id去查询菜单
    @GetMapping("/findNodeById/{id}")
    @log(type = "根据id去查询菜单")
    public Result findNodeById(@PathVariable Long id){
        SysMenu sysMenu = this.sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping("/updateMenu")
    @log(type = "修改菜单")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeMenu/{id}")
    @log(type = "删除菜单")
    public Result removeMenu(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.ok();
    }

    // 添加菜单
    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    @log(type = "添加菜单")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        this.sysMenuService.save(sysMenu);
        return Result.ok();
    }

    // 根据角色分配菜单的功能
    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    @log(type = "根据角色获取菜单")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> list =   this.sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    @log(type = "给角色分配权限")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }

}
