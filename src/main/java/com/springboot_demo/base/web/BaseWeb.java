package com.springboot_demo.base.web;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springboot_demo.common.util.Constants;
import com.springboot_demo.common.util.DefaultMap;
import com.springboot_demo.common.util.LogUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @ClassName BaseWeb
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/11
 * @Version V1.0
 **/
public class BaseWeb {

    private Object logger = LogUtil.getLogger(BaseWeb.class);
    /**
     * 获取http ip地址
     * @param request
     * @return
     */
    public String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("PRoxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 读取post数据
     *
     * @param request
     */
    public String getPostContent(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("");
        InputStream in = null;
        String line = null;
        try {
            in = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * @param array 传入对象数组
     * @param param 传入对象名数组
     * @param issuc true成功，false失败
     * @param error 失败时候传入错误信息,成功是无需传入
     * @return
     */
    public String toJson(HttpSession session, String[] param, Object[] array, boolean issuc, String error) {
        return toJson(session, param, array, issuc, error, 200, 300);
    }

    public String toJson(HttpSession session, JSONObject jsonObject, boolean issuc, String error) {
        if (issuc == false) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("rst", 300);
            map.put("msg", error);
            jsonObject = JSONUtil.parseObj(map,false);
        }

        String resData = jsonObject.toString();
        LogUtil.printLogDebug(logger,"返回原始数据:" + resData);
        String uri = (String) session.getAttribute("uri");
        if (((boolean) DefaultMap.get("isApiEncryptCheck")) && StrUtil.isNotEmpty(uri)
                && StrUtil.isNotEmpty(uri)) {
            DES des = SecureUtil.des(Constants.CRYPT_KEY.getBytes());
            resData = des.encryptBase64(jsonObject.toString());
            LogUtil.printLogDebug(logger,"返回加密后数据:" + resData);
        }
        return resData;
    }

    /**
     * @param array    传入对象数组
     * @param param    传入对象名数组
     * @param issuc    true成功，false失败
     * @param error    失败时候传入错误信息,成功是无需传入
     * @param succode  成功返回代码
     * @param errorcod 失败时候返回代码
     * @return
     */
    public String toJson(HttpSession session, String[] param, Object[] array, boolean issuc, String error,
                         Integer succode, Integer errorcod) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (issuc) {
            map.put("rst", succode);
            if (param != null && param.length != 0) {
                for (int i = 0; i < param.length; i++) {
                    if (param[i] != null && array[i] != null) {
                        map.put(param[i], array[i]);
                    }
                }
            }

        } else {
            map.put("rst", errorcod);
            map.put("msg", error);
        }

        JSONObject jsonObject = JSONUtil.parseObj(map);

        String resData = jsonObject.toString();
        LogUtil.printLogDebug(logger,"返回原始数据:" + resData);
        String uri = (String) session.getAttribute("uri");
        if (((boolean) DefaultMap.get("isApiEncryptCheck")) && StrUtil.isNotEmpty(uri)
                && StrUtil.isNotEmpty(uri)) {
            DES des = SecureUtil.des(Constants.CRYPT_KEY.getBytes());
            resData = des.encryptBase64(jsonObject.toString());
            LogUtil.printLogDebug(logger,"返回加密后数据:" + resData);
        }
        return resData;
    }

    /**
     * 返回json格式的字符串使用  (cy:2019-09-19)
     * @param session
     * @param param
     * @param array
     * @param issuc
     * @param error
     * @return
     */
    public String tofastJson(HttpSession session, String[] param, Object[] array, boolean issuc, String error) {
        return tofastJson(session, param, array, issuc, error, 200, 300);
    }

    /**
     * 返回json格式的字符串使用  (cy:2019-09-19)
     * @param session
     * @param param
     * @param array
     * @param issuc
     * @param error
     * @return
     */
    public String tofastJson(HttpSession session, String[] param, Object[] array, boolean issuc, String error,
                             Integer succode, Integer errorcod) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (issuc) {
            map.put("rst", succode);
            if (param != null && param.length != 0) {
                for (int i = 0; i < param.length; i++) {
                    if (param[i] != null && array[i] != null) {
                        map.put(param[i], array[i]);
                    }
                }
            }
        } else {
            map.put("rst", errorcod);
            map.put("msg", error);
        }
        String resData = JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
        return resData;
    }

}
