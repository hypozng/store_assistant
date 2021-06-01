package com.dauivs.storeassistant.utils;

import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
    public static final int BUF_SIZE = 1024;

    /**
     * 从输入流读取数据，并将读取到的数据写入到输出流
     * @param in
     * @param out
     */
    public static long copy(InputStream in, OutputStream out) {
        if (in == null || out == null) {
            return 0;
        }
        long length = 0;
        int n;
        byte[] buf = new byte[BUF_SIZE];
        try {
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
                length += n;
            }
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return length;
    }
}
