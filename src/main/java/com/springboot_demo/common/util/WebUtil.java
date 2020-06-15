package com.springboot_demo.common.util;

import cn.hutool.core.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WebUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
public class WebUtil {

    private static Object logger = LogUtil.getLogger(WebUtil.class);

    /**
     * @description 获取请求上报数据
     * @param request
     * @return
     */
    public static String getRequestContent(HttpServletRequest request) {
        String content = "";
        try {
            content = StreamUtil.getInputStreamContent(request.getInputStream());
        } catch (IOException e) {
            LogUtil.prinLogError(logger,e);
        }
        return content;
    }

    /**
     * 向客户端响应内容
     *
     * @param response
     * @param content
     *            输出内容
     */
    public static void print(HttpServletResponse response, String content) {
        if (response == null || StrUtil.isEmpty(content)) {
            LogUtil.prinLogError(logger,"参数异常，response=" + response + ",content=" + content);
            return;
        }
        LogUtil.prinLogError(logger,"respData:" + content);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(content);
        } catch (IOException e) {
            LogUtil.prinLogError(logger, e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 判断是否为POST请求，json格式
     *
     * @param request
     * @return
     */
    public static boolean isPostJsonRequest(HttpServletRequest request) {
        try {
            if ("POST".equals(request.getMethod().toUpperCase())
                    && request.getContentType().indexOf("application/json") != -1) {
                return true;
            }
        } catch (Exception e) {
            LogUtil.prinLogError(logger, e);
        }
        return false;
    }

    /**
     * 判断是否是POST请求，且是application/x-www-form-urlencoded
     */
    public static boolean isPostFormRequest(HttpServletRequest request) {
        try {
            if ("POST".equals(request.getMethod().toUpperCase())
                    && request.getContentType().indexOf("application/x-www-form-urlencoded") != -1) {
                return true;
            }
        } catch (Exception e) {
            LogUtil.prinLogError(logger, e);
        }
        return false;
    }

    /**
     * 读取HttpServletRequest中的get请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String[]> getParamMapFromGet(HttpServletRequest request) {
        if (request == null) {
            LogUtil.prinLogError(logger,"参数异常，request=" + request);
            return new HashMap<String, String[]>();
        }
        return StringUtil.parseString(request.getQueryString());
    }

    /**
     * 读取HttpServletRequest里面的post请求参数
     *
     * @param request
     *            HttpServletRequest对象
     * @return 返回参数的map集合
     */
    public static HashMap<String, String[]> getParamMapFromPost(HttpServletRequest request) {
        String body = "";
        try {
            body = StreamUtil.getInputStreamContent(request.getInputStream());
        } catch (IOException e) {
            LogUtil.prinLogError(logger, e);
        }
        if (StrUtil.isEmpty(body)) {
            return new HashMap<String, String[]>();
        }
        return StringUtil.parseString(body);
    }

    public static HashMap<String, String[]> getParamMapFromPost(HttpServletRequest request, String body) {
        if (StrUtil.isEmpty(body)) {
            return new HashMap<String, String[]>();
        }
        return StringUtil.parseString(body);
    }

}
