package com.springboot_demo.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @ClassName EncodeUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
public class EncodeUtil {

    private static Object logger = LogUtil.getLogger(EncodeUtil.class);

    /**
     * URL解码, Encode默认为UTF-8.
     *
     * @param s
     *            String to be translated.
     * @return
     */
    public static String urlDecode(String s) {
        return urlDecode(s, CharsetUtil.UTF_8);
    }

    /**
     * URL解码, Encode默认为UTF-8{@link URLDecoder#decode}
     *
     * @param s
     *            String to be translated.
     * @param enc
     *            The name of a supported character encoding.
     * @return
     */
    public static String urlDecode(String s, String enc) {
        try {
            return URLDecoder.decode(s, enc);
        } catch (UnsupportedEncodingException e) {
            LogUtil.prinLogError(logger, e);
        }
        return null;
    }
}
