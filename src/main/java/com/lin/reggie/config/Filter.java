package com.lin.reggie.config;

import com.alibaba.fastjson.JSON;
import com.lin.reggie.common.BaseContext;
import com.lin.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebFault;
import java.io.FileFilter;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class Filter implements javax.servlet.Filter {
    /*
    * 路径匹配器，支持通配符
    * */
    public static final AntPathMatcher antPathMatcher= new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest Request = (HttpServletRequest) servletRequest;
        HttpServletResponse Response = (HttpServletResponse) servletResponse;
        //获取请求
        String requestURI = Request.getRequestURI();
        log.info("拦截到的url{}",requestURI);
        //需要放行的url
        String[] urls = {
        "/employee/login","/employee/logout","/backend/**","/front/**","/commmon/**","/front/**","/user/login","/user/sendMsg"
        };
        /*判断请求是否处理
        * */
        boolean check = check(urls, requestURI);
        //如果不需要处理，则直接放行
        if (check){
            log.info("本次请求不需要处理{}",requestURI);
            filterChain.doFilter(Request,Response);
            return;
        }
        //如果已登录则直接放行
        if(Request.getSession().getAttribute("user")!=null){
            log.info("用户已登录，用户id：{}",Request.getSession().getAttribute("user"));
            //获得线程id
            long id = Thread.currentThread().getId();
            log.info("线程id为{}",id);
            long empId = (Long) Request.getSession().getAttribute("user");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(Request,Response);
            return;
        }
        //如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        servletResponse.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return;


//        log.info("拦截请求: {}",servletRequest1.getRequestURI());
    }
    /**路径匹配，检查请求是否需要放行
     * @param urls
     * @param requestUrl
     * @return
     * **/
    public boolean check (String[] urls, String requestUrl){
        for (String url:urls
             ) {
            boolean match = antPathMatcher.match(url, requestUrl);
            if (match){
                return true;
            }
        }
        return false;
    }
}
