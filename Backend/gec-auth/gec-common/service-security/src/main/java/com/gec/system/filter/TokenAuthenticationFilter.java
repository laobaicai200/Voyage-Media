package com.gec.system.filter;

import com.alibaba.fastjson.JSON;

import com.gec.model.system.SysMenu;
import com.gec.util.JwtHelper;
import com.gec.util.ResponseUtil;
import com.gec.util.Result;
import com.gec.util.ResultCodeEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 认证解析token过滤器
 * </p>
 */
/**
 * <p>
 * 认证解析token过滤器
 * </p>
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }





    // 过滤器 方法
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());

        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI())||"/admin/system/upload/uploadImage".equals(request.getRequestURI())||"/admin/system/upload/uploadVideo".equals(request.getRequestURI()) ||"/admin/system/index/Register".equals(request.getRequestURI()) ) {
            chain.doFilter(request, response);
            return;
        }



        // 调用了下面的方法

        // 获取认证 对象
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 1.从请求中 header 中 获取token
        String token = request.getHeader("token");
        logger.info("token:"+token);


        // 2.判断token是否为null
        if (!StringUtils.isEmpty(token)) {

            //3.当token不为null，就从token 中解析获取 到username

            String useruame = JwtHelper.getUsername(token);
            logger.info("useruame:"+useruame);

            //4.如果 username 不为null

            if (!StringUtils.isEmpty(useruame)) {

                // 5.就认证成功

                // 从redid 中 根据 username 这个key 来获取到刚才存储的权限信息
                String authoritiesString = (String) redisTemplate.opsForValue().get(useruame);

                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                for (Map map : mapList) {
                    authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
                }

                return new UsernamePasswordAuthenticationToken(useruame, null, authorities);


                // return new UsernamePasswordAuthenticationToken(useruame, null, Collections.emptyList());
            }
        }
        return null;
    }
}