package com.gec.system.controller;

import com.gec.model.system.SysRole;
import com.gec.model.system.SysUser;
import com.gec.model.vo.LoginVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.service.SysUserService;
import com.gec.util.JwtHelper;
import com.gec.util.MD5Helper;
import com.gec.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "登录管理控制器")
@RequestMapping(value = "/admin/system/index")
public class LoginController {


    @Autowired
    private SysUserService sysUserService;

    /**
     *  登录接口
     * @return
     */


    @ApiOperation("登录方法")
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginVo loginVo)
    {
        //1.根据username 查询数据
        SysUser sysUser =  this.sysUserService.getUserInfoUserName(loginVo.getUsername());
        //2.判断非空
        if(null == sysUser)
        {
            throw new MyCustomerException(2001,"用户名不存在");
        }
        //3.判断密码是否正确
        if(!sysUser.getPassword().equals(MD5Helper.encrypt(loginVo.getPassword())))
        {
            throw new MyCustomerException(2002,"密码不正确");
        }
        //4.判断状态是否可用
        if(sysUser.getStatus() == 0)
        {
            throw new MyCustomerException(2003,"用户被停用");
        }
        //5.生成token
        String token = JwtHelper.createToken(sysUser.getId()+"", sysUser.getUsername());

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("token",token);

        return Result.ok(map);
    }
    /**
     *  获取用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public Result info(HttpServletRequest request)
    {
        //1.获取请求头的token字符串
        String token = request.getHeader("token");
        //2.从token字符串获取用户id和用户名
        String username = JwtHelper.getUsername(token);
        //3.根据用户名称获取用户信息（a.基本信息 b.菜单权限信息 c.按钮权限信息）
        Map<String,Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }

    @ApiOperation("注册的方法")
    @PostMapping(value = "/Register")
    public Result Register(@RequestBody SysUser user)
    {
        //重写sysUserService,让他默认分配用户角色
        sysUserService.save(user);
        //1.根据username 查询数据
        SysUser sysUser =  this.sysUserService.getUserInfoUserName(user.getUsername());
        //2.判断非空
        if(null == sysUser) {
            throw new MyCustomerException(2001, "用户名已经被使用");
        }
        //3.生成token
        String token = JwtHelper.createToken(sysUser.getId()+"", sysUser.getUsername());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("token",token);

        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }




}
