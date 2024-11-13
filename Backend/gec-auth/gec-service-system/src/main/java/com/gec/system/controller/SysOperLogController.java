package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.model.system.SysLoginLog;
import com.gec.model.system.SysOperLog;
import com.gec.model.vo.SysLoginLogQueryVo;
import com.gec.model.vo.SysOperLogQueryVo;
import com.gec.system.service.SysOperLogService;
import com.gec.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("操作日志控制器")
@RestController
@RequestMapping("/admin/system/sysOperLog")
public class SysOperLogController {

    @Autowired
    private SysOperLogService sysLoginLogService;
    @ApiOperation(value = "获取分页列表日志")
    @GetMapping("{page}/{limit}")
    public Result QueryLoginLog(
            @PathVariable Long page,
            @PathVariable Long limit,
            SysOperLogQueryVo sysOperLogQueryVo) {

        IPage<SysOperLog> iPage = sysLoginLogService.selectPage(page, limit, sysOperLogQueryVo);
        return Result.ok(iPage);
    }

    @ApiOperation(value = "获取日志")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysOperLog byId = sysLoginLogService.getById(id);
        return Result.ok(byId);
    }
}
