package com.springboot_demo.common.util;

import javax.servlet.ServletInputStream;
import java.io.*;

/**
 * @ClassName StreamUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/6/12
 * @Version V1.0
 **/
public class StreamUtil {

    private static Object logger = LogUtil.getLogger(StreamUtil.class);

    /**
     * 关闭流
     *
     * @param c
     */
    public static final void close(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c = null;
    }

    /**
     * 克隆InputStream
     *
     * @param inputStream
     * @return
     */
    public static InputStream cloneInputStream(ServletInputStream inputStream) {
        if (inputStream == null) {
            LogUtil.prinLogError(logger,"参数异常，inputStream=" + inputStream);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            LogUtil.prinLogError(logger,""+ e);
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }

    /**
     * 读取流里面的byte数据,抛出IO异常
     *
     * @param is
     *            输入流
     * @return byte数组
     * @throws IOException
     */
    public static byte[] getStreamBytesThrowsIOException(InputStream is) throws IOException {
        if (is == null) {
            LogUtil.prinLogError(logger,"参数异常，is=" + is);
            throw new IOException("输入流为空");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        try {
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            LogUtil.prinLogError(logger,"Exception:" + e);
            throw new IOException(e);
        } finally {
            close(baos);
        }
    }

    /**
     * 读取流里面的文本内容
     *
     * @param is
     *            输入流
     * @param size
     *            限制读取的字节大小，小于等于0为不限制
     * @return 文本内容
     */
    public static String getInputStreamContent(InputStream is, int size) {
        if (is == null) {
            LogUtil.prinLogError(logger,"参数异常，inputStream=" + is);
            return null;
        }
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            if (size > 0) {
                br = new BufferedReader(new InputStreamReader(is, "utf-8"), size);
            } else {
                br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            }
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            LogUtil.prinLogError(logger,"Exception:" + e);
        } finally {
            close(br);
        }
        return null;
    }

    /**
     * 读取流里面的文本内容
     *
     * @param is
     *            输入流
     * @return 文本内容
     */
    public static String getInputStreamContent(InputStream is) {
        return getInputStreamContent(is, -1);
    }


}
