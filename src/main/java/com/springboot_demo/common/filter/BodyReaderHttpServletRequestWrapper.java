package com.springboot_demo.common.filter;

import com.springboot_demo.common.util.StreamUtil;
import com.springboot_demo.common.util.WebUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * @ClassName BodyReaderHttpServletRequestWrapper
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /** 报文 */
    private final byte[] body;
    /** 请求参数集合 */
    private Map<String, String[]> paramsMap;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request, String newBody) throws IOException {
        super(request);
        if(newBody!=null){
            body = newBody.getBytes(request.getCharacterEncoding());
        } else {
            body = StreamUtil.getStreamBytesThrowsIOException(request.getInputStream());
        }
        // 首先从POST中获取数据
        if ("POST".equals(request.getMethod().toUpperCase())) {
            if(newBody != null){
                paramsMap  = WebUtil.getParamMapFromPost(this, newBody);
            } else {
                paramsMap = WebUtil.getParamMapFromPost(this);
            }
        } else {
            paramsMap = WebUtil.getParamMapFromGet(this);
        }

    }

    @Override
    public Map getParameterMap() {
        return paramsMap;
    }

    /**
     * 重写getParameter，代表参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = paramsMap.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {// 同上
        return paramsMap.get(name);
    }

    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(paramsMap.keySet());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener arg0) {

            }
        };
    }


}
