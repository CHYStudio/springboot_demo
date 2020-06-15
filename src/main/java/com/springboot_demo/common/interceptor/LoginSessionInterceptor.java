package com.springboot_demo.common.interceptor;

import cn.hutool.core.date.DateUtil;
import com.springboot_demo.common.util.LogUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName LoginSessionInterceptor
 * @Description: TODO 拦截器
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
@Component
public class LoginSessionInterceptor extends HandlerInterceptorAdapter {

    private static Object logger = LogUtil.getLogger(LoginSessionInterceptor.class);

    // 需要校验的地址
    private static Map<String, String> checkUrl = new HashMap<String, String>();
    // Smallking:需要加密的URL
    public static Map<String, String> encryptUrl = new HashMap<String, String>();

    static {
        loadProperties("checkUrl.properties", checkUrl);
        loadProperties("encryptUrl.properties", encryptUrl);
    }

    /**
     * 加载验证地址配置
     *
     * @param ppath
     * @param map
     */
    private static void loadProperties(String ppath, Map<String, String> map) {
        Properties props = new Properties();
        try {
            /*
            String path = LoginSessionInterceptor.class.getClassLoader().getResource(ppath).getPath().replace("%20",
                    " ");
            FileInputStream fis = new FileInputStream(path);
            InputStream is = new BufferedInputStream(fis);
            */
            InputStream is = LoginSessionInterceptor.class.getClassLoader()
                    .getResourceAsStream(ppath);
            props.load(is);
            Enumeration<?> subenum = props.propertyNames();
            while (subenum.hasMoreElements()) {
                String key = (String) subenum.nextElement();
                String value = props.getProperty(key);
                map.put(key, value);
            }
        } catch (IOException e) {
            LogUtil.prinLogError(logger,e);
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 设置响应类型和编码
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            return true;
        }catch (Exception e){
            LogUtil.prinLogError(logger,e);
        }
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LogUtil.prinLogInfo(logger,"-------------------" + DateUtil.now() + "---"
                + request.getRequestURI() + "------end【"
                + (System.currentTimeMillis() - (long) request.getAttribute("reqStartTime")) + "ms】");
    }
}
