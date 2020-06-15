package com.springboot_demo.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @ClassName StringUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
public class StringUtil {

    private static Object logger = LogUtil.getLogger(StringUtil.class);


    /**
     * 解析如下格式字符串，格式：key1=value1&key2=value12&key3=value3
     *
     * @param str
     *            输入字符串
     * @return 返回键值对map集合，同样的key进行合并
     */
    public static HashMap<String, String[]> parseString(String str) {
        return parseString(str, "&");
    }

    /**
     * 解析如下格式字符串，格式：key1=value1&key2=value12&key3=value3;'&'由参数delim指定
     *
     * @param str
     *            输入字符串
     * @param delim
     *            分割符，不支持空串，多数情况为'&'则可以直接使用{@link StringUtil#parseString(str)}
     * @return 返回键值对map集合，同样的key进行合并
     */
    public static HashMap<String, String[]> parseString(String str, String delim) {
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        if (StrUtil.isEmpty(str) || StrUtil.isEmpty(delim)) {
            LogUtil.prinLogError(logger,"输入参数异常，str=" + str + ",delim=" + delim);
            return map;
        }
        String valArray[] = null;
        StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            if (pair == null) {
                continue;
            }
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = pair.substring(0, pos);
            String val = pair.substring(pos + 1, pair.length());
            if (map.containsKey(key)) {
                String oldVals[] = (String[]) map.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = EncodeUtil.urlDecode(val);
            } else {
                valArray = new String[1];
                valArray[0] = EncodeUtil.urlDecode(val);
            }
            map.put(key, valArray);
        }
        return map;
    }

}
