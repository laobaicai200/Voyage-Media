package com.gec.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysCategory;
import com.gec.model.vo.SysCategoryQueryVo;
import com.gec.system.service.SysCategoryService;
import com.gec.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags="影视分类管理器")
@RequestMapping("/admin/system/sysCategory")
public class SysCategoryController {

    @Autowired
    private SysCategoryService sysCategoryService;


    //查询所有
    @ApiOperation("查询所有影视分类")
    @RequestMapping("/findAll")
    public Result findNodes(){
        List<SysCategory> list = sysCategoryService.list();
        return Result.ok(list);
    }

    //分页查询
    @ApiOperation("分页查询影视分类")
    @RequestMapping("/{page}/{size}")
    public Result findPage(@PathVariable Long page, @PathVariable Long size, SysCategoryQueryVo sysCategoryQueryVo ){
        IPage iPage = new Page(page, size);
        IPage<SysCategory> pageList = sysCategoryService.selectPage(iPage, sysCategoryQueryVo);
        return Result.ok(pageList);
    }

    //添加
    @ApiOperation("添加影视分类")
    @PostMapping("/addCategory")
    public Result addCategory(SysCategory sysCategory){
        sysCategoryService.save(sysCategory);
        return Result.ok();
    }
    @ApiOperation("根据id查询影视分类")
    @GetMapping("/findCategoryById/{id}")
    public Result findCategoryById(@PathVariable Long id){
        SysCategory sysCategory = sysCategoryService.getById(id);
        return Result.ok(sysCategory);
    }
    @ApiOperation("修改影视分类")
    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody SysCategory sysCategory){
        boolean isOk = sysCategoryService.updateById(sysCategory);
        return isOk?Result.ok():Result.fail();
    }

    @ApiOperation("删除影视分类")
    @DeleteMapping("/removeCategory/{id}")
    public Result removeCategory(@PathVariable Long id){
        boolean isOk = sysCategoryService.removeById(id);
        return isOk?Result.ok():Result.fail();
    }

    @ApiOperation("批量删除影视分类")
    @DeleteMapping("/batchRemoveCategory")
    public Result batchRemoveCategory(@RequestBody List<Long> ids){
        boolean isOk = sysCategoryService.removeByIds(ids);
        return isOk?Result.ok():Result.fail();
    }


}
