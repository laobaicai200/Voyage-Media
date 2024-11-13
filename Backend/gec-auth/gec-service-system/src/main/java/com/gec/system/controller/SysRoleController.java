package com.gec.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysRole;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;
import com.gec.system.annocation.log;
import com.gec.system.service.SysRoleService;
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
import java.util.Map;

@Api(tags = "角色管理控制器")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询全用户记录")
    @GetMapping("/findAll")
    public Result finAll(){
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("/removeRoleById/{id}")
    @log(type = "逻辑删除角色")
    public Result removeRole(@PathVariable Long id){
        boolean isRemove = sysRoleService.removeById(id);
        return isRemove?Result.ok(isRemove):Result.fail(isRemove);
    }
    //分页查询用户记录
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("分页查询用户记录")
    @GetMapping({"/{page}/{size}"})
    @log(type = "分页查询角色")
    public Result findPage(@PathVariable Long page,
                           @PathVariable Long size,
                           SysRoleQueryVo sysRoleQueryVo)
    {
        IPage<SysRole> iPage = new Page<>(page,size);
        IPage<SysRole> pageList = sysRoleService.selectPage(iPage, sysRoleQueryVo);
        return Result.ok(pageList);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色记录")
    @PostMapping("/addRole")
    @log(type = "添加角色")
    public Result addRole(@RequestBody SysRole sysRole){
        boolean isAdd = sysRoleService.save(sysRole);
        return isAdd?Result.ok(isAdd):Result.fail(isAdd);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色记录")
    @PutMapping("/updateRole")
    @log(type = "修改角色")
    public Result updateRole(@RequestBody SysRole sysRole){
        boolean isUpdate = sysRoleService.updateById(sysRole);
        return isUpdate?Result.ok(isUpdate):Result.fail(isUpdate);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    @log(type = "批量删除角色")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean isRemove = sysRoleService.removeByIds(idList);
        return isRemove?Result.ok(isRemove):Result.fail(isRemove);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查询")
    @GetMapping("/findRoleById/{id}")
    @log(type = "根据id查询角色")
    public Result get(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    @log(type = "根据用户获取角色数据")
    public Result toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    @log(type = "根据用户分配角色")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {

        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }



    //后续扩展为，可以检验充值完的管理员给后台服务器发送请求
    @ApiOperation(value = "充值vip的方法")
    @PostMapping("/doVip")
    public Result doAssign(@RequestBody Long id) {
        if (id==null){
            id = 1l;
        }
        sysRoleService.saveVip(id);
        return Result.ok();
    }




//    @Autowired
//    private SysRoleService sysRoleService;


//    @ApiOperation("获取全部角色列表")
//    // http://localhost:8085/admin/system/sysRole/findAll
//    @GetMapping("/findAll")
//    public List<SysRole> findAll()
//    {
//        List<SysRole> list = this.sysRoleService.list();
//        return list;
//    }

//    @ApiOperation("获取全部角色列表")
//    // http://localhost:8085/admin/system/sysRole/findAll
//    @GetMapping("/findAll")
//    public Result findAll()
//    {
//
//
//        List<SysRole> list = this.sysRoleService.list();
//        return Result.ok(list);
//    }
//
//    // http://localhost:8085/admin/system/sysRole/removeRole/14
////
////    @ApiOperation("根据id去移除一个角色")
////    // 测试删除
////    @DeleteMapping("/removeRole/{id}")
////    public boolean removeRole(@PathVariable Long id)
////    {
////        boolean b = this.sysRoleService.removeById(id);
////        return b;
////    }
//
//    @PreAuthorize("hasAuthority('bnt.sysRole.remove333')") //bnt.sysRole.remove
//    @ApiOperation("根据id去移除一个角色")
//    // 测试删除
//    @DeleteMapping("/removeRole/{id}")
//    public Result removeRole(@PathVariable Long id)
//    {
//        boolean b = this.sysRoleService.removeById(id);
//        if (b)
//        {
//            return Result.ok();
//        }
//        else
//        {
//            return Result.fail();
//        }
//    }
//    // 分页和条件查询
//    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
//    @GetMapping("/{page}/{limit}")
//    public Result findRoleByPageQuery(@PathVariable Long page,
//                                      @PathVariable Long limit,
//                                      SysRoleQueryVo sysRoleQueryVo)
//    {
//        //1.创建分页对象
//        IPage<SysRole> p1 = new Page<SysRole>(page,limit);
//        //2.调用方法
//        p1 =    this.sysRoleService.selectPage(p1,sysRoleQueryVo);
//        //3.返回
//        return Result.ok(p1);
//    }
//    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
//    // 添加角色
//    @PostMapping("/addRole")
//    public Result addRole(@RequestBody SysRole sysRole)
//    {
//        boolean res = this.sysRoleService.save(sysRole);
//        if (res)
//        {
//            return Result.ok();
//        }
//        else
//        {
//            return Result.fail();
//        }
//    }
//    // 修改
//    //1.根据id 去得到一个角色
//    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
//    @GetMapping("/findRoleById/{id}")
//    public Result findRoleById(@PathVariable Long id)
//    {
//        SysRole sysRole = this.sysRoleService.getById(id);
//        return Result.ok(sysRole);
//    }
//    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
//    // 实现修改
//    @PostMapping("/updateRole")
//    public Result updateRole(@RequestBody SysRole sysRole)
//    {
//        boolean b = this.sysRoleService.updateById(sysRole);
//        if (b)
//        {
//            return Result.ok();
//        }
//        else
//        {
//            return Result.fail();
//        }
//    }
//    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
//    // 批量删除
//    @DeleteMapping("/removeRoleByIds")
//    public Result removeRoleByIds(@RequestBody List<Long> ids)
//    {
//        boolean b = this.sysRoleService.removeByIds(ids);
//        if (b)
//        {
//            return Result.ok();
//        }
//        else
//        {
//            return Result.fail();
//        }
//    }
//
//    // 根据用户获取角色信息
//    @ApiOperation("根据用户获取角色信息")
//    @GetMapping("/toAssign/{userId}")
//    public Result toAssign(@PathVariable Long userId)
//    {
//        Map<String,Object> map =  this.sysRoleService.getRolesByUserId(userId);
//        return Result.ok(map);
//    }
//    // 根据用户分配角色
//    @ApiOperation("根据用户分配角色")
//    @PostMapping("/doAssign")
//    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
//        this.sysRoleService.doAssign(assginRoleVo);
//        return Result.ok();
//    }
}
