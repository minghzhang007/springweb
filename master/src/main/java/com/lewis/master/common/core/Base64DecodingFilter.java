package com.lewis.master.common.core;

import com.google.common.base.Splitter;
import com.lewis.master.common.utils.ListUtil;
import com.lewis.master.common.utils.StringUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangminghua on 2017/2/20.
 */
public class Base64DecodingFilter implements Filter {

    private PathMatcher matcher = new AntPathMatcher();

    private List<String> noDecodingPathList = new ArrayList<String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        String noDecodingPaths = filterConfig.getInitParameter("noDecodingPaths");
        if (StringUtil.isNotEmpty(noDecodingPaths)) {
            noDecodingPathList = Splitter.on(",").trimResults().splitToList(noDecodingPaths);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        if (containsNoDecodingPaths(noDecodingPathList, httpServletRequest.getServletPath())) {
            chain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            Base64HttpServletRequestWrapper base64HttpServletRequestWrapper = new Base64HttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(base64HttpServletRequestWrapper,httpServletResponse);
        }
    }

    private boolean containsNoDecodingPaths(List<String> noDecodingPathList, String servletPath) {
        if (ListUtil.isNotEmpty(noDecodingPathList)) {
            if (noDecodingPathList.contains(servletPath)){
                return true;
            }
            for (String noDecodingPath : noDecodingPathList) {
                if (matcher.match(noDecodingPath, servletPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void destroy() {

    }
}
