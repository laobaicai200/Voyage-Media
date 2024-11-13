package com.gec.system.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysLoginLog;
import com.gec.model.system.SysMovie;
import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.system.annocation.log;
import com.gec.system.service.SysLoginLogService;
import com.gec.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("登录日志控制器")
@RestController
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "获取分页列表日志")
    @GetMapping("{page}/{limit}")
    @log(type = "获取分页日志")
    public Result QueryLoginLog(
            @PathVariable Long page,
            @PathVariable Long limit,
                    SysLoginLogQueryVo sysLoginLogQueryVo) {

        IPage<SysLoginLog> iPage = sysLoginLogService.selectPage(page, limit, sysLoginLogQueryVo);
        return Result.ok(iPage);
    }

    @ApiOperation(value = "获取日志")
    @GetMapping("get/{id}")
    @log(type = "获取日志")
    public Result get(@PathVariable Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return Result.ok(sysLoginLog);
    }

    @ApiOperation(value = "删除日志")
    @DeleteMapping("/remove/{id}")
    @log(type = "删除日志")
    public Result removeMenu(@PathVariable Long id) {
        sysLoginLogService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "批量删除日志")
    @DeleteMapping("/batchRemove")
    @log(type = "批量删除日志")
    public Result batchRemove(@RequestBody List<Long> ids) {
        sysLoginLogService.removeByIds(ids);
        return Result.ok();
    }
}
