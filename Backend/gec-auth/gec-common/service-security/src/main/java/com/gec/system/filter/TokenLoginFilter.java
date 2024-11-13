package com.gec.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.model.vo.LoginVo;
import com.gec.system.custom.CustomUser;
import com.gec.system.service.SysLoginLogService;
import com.gec.util.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * 用户认证（Authentication）
 * SecurityContext：上下文对象，用来获取`Authentication`
 * SecurityContextHolder：上下文管理对象，用来在程序任何地方获取`SecurityContext`
 * </p>
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private SysLoginLogService sysLoginLogService;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate,SysLoginLogService sysLoginLogService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
        this.sysLoginLogService = sysLoginLogService;
    }

    /**
     * 登录认证
     * @return
     * @throws AuthenticationException
     * 完整流程
     * 接收请求：
     * 用户发送一个包含登录信息的 HTTP 请求到服务器。
     * 请求体包含 JSON 格式的登录数据。
     * 读取请求体：
     * 使用 ObjectMapper 读取请求体中的 JSON 数据，并将其转换为 LoginVo 对象。
     * 创建认证令牌：
     * 从 LoginVo 对象中提取用户名和密码，创建 UsernamePasswordAuthenticationToken 对象。
     * 执行认证：
     * 调用 AuthenticationManager 的 authenticate 方法进行认证。
     * AuthenticationManager 会调用相应的 AuthenticationProvider 来完成实际的认证逻辑。
     * 返回认证结果：
     * 如果认证成功，返回一个表示认证结果的 Authentication 对象。
     * 如果认证失败，抛出 AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            //读取请求体，从请求体（req.getInputStream）中读取json数据，并将其转换为LoginVo对象
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);
            //封装用户名和密码，调用authenticationManager的authenticate方法进行认证,放回了Authentication对象，用来封装用户信息
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            //调用 AuthenticationManager 的 authenticate 方法进行实际的认证操作。
            //其过程是:
            //getAuthenticationManager() 获取当前的 AuthenticationManager 实例。
            //authenticate 方法会调用相应的 AuthenticationProvider 来完成实际的认证逻辑。
            //认证成功后返回一个 Authentication 对象，表示认证结果。
            return this.getAuthenticationManager().authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录成功
     * @throws IOException
     * @throws ServletException
     * 成功认证处理逻辑的一部分，主要用于在认证成功后生成JWT Token并将用户的权限信息保存到Redis中
     *认证成功：
     * 当用户通过认证后，Spring Security会调用 successfulAuthentication 方法。
     * 获取用户信息：
     * 从 Authentication 对象中获取认证成功的用户信息，这里假设用户信息存储在 CustomUser 类中。
     * 生成JWT Token：
     * 使用 JwtHelper 类生成JWT Token，传递用户的ID和用户名作为参数。
     * 保存权限数据到Redis：
     * 将用户的权限信息序列化为JSON字符串，并保存到Redis中。
     * 构建响应数据：
     * 创建一个 Map 对象，将生成的JWT Token放入其中。
     * 输出响应数据：
     * 将包含JWT Token的响应数据输出到HTTP响应中
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        String token = JwtHelper.createToken(customUser.getSysUser().getId()+"", customUser.getSysUser().getUsername());
        //保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        sysLoginLogService.recordLoginLog(customUser.getUsername(),1, IpUtil.getIpAddress(request),"登录成功");
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        
        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }
}