package com.niuyaohui.filter;

import com.niuyaohui.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/admin/index.html","/admin/views/*","/express/*"})
public class AccessControlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String userName = UserUtil.getUserName(request.getSession());
        if(userName != null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            response.sendError(404,"很遗憾，权限不足");
        }
    }

    @Override
    public void destroy() {
    }
}
