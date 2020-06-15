package com.springboot_demo.common.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.springboot_demo.common.interceptor.LoginSessionInterceptor;
import com.springboot_demo.common.util.DefaultMap;
import com.springboot_demo.common.util.LogUtil;
import com.springboot_demo.common.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName LogFilter
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
public class LogFilter implements Filter {

    private Object logger = LogUtil.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            LogUtil.prinLogInfo(logger,"不是HttpServletRequest请求，不处理系统流...");
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        LogUtil.prinLogInfo(logger,"-------------------" + DateUtil.now() + "---" + httpServletRequest.getRequestURI() + "------start");
        request.setAttribute("reqStartTime", System.currentTimeMillis());

        // 仅支持[application/json,application/x-www-form-urlencoded]请求
        if (!WebUtil.isPostJsonRequest(httpServletRequest) && !WebUtil.isPostFormRequest(httpServletRequest)) {
            LogUtil.prinLogInfo(logger,"非postjson或postform请求，不处理，questMethod=" + httpServletRequest.getMethod().toUpperCase() + ",contentType=" + httpServletRequest.getContentType());
            chain.doFilter(request, response);
            return;
        }

        if((boolean) DefaultMap.get("isApiEncryptCheck")){
            String isEncryptUrl = LoginSessionInterceptor.encryptUrl.get(httpServletRequest.getRequestURI());
            if (StrUtil.isNotEmpty(isEncryptUrl)) {
                String orgBody = WebUtil.getRequestContent(httpServletRequest);
                LogUtil.prinLogInfo(logger,"请求加密数据："+orgBody);
                if(StrUtil.isEmpty(orgBody)){
                    chain.doFilter(new BodyReaderHttpServletRequestWrapper(httpServletRequest, null), response);
                    return;
                }
                //String decryptReqContent = CryptUtil.decrypt(orgBody);
                String decryptReqContent = ""; //解密数据
                if (decryptReqContent == null) {
                    chain.doFilter(new BodyReaderHttpServletRequestWrapper(httpServletRequest, "DECRYPT_ERROR"), response);
                } else {
                    chain.doFilter(new BodyReaderHttpServletRequestWrapper(httpServletRequest, decryptReqContent), response);
                }
            } else {
                chain.doFilter(new BodyReaderHttpServletRequestWrapper(httpServletRequest, null), response);
            }
        } else {
            chain.doFilter(new BodyReaderHttpServletRequestWrapper(httpServletRequest, null), response);
        }


    }

    @Override
    public void destroy() {

    }
}
