package com.au.mit.shell.common.command;

import java.io.*;

/**
 * Created by semionn on 11.09.16.
 */
public class PipelineUtils {
    private PipelineUtils() {}

    public static void ConnectStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        final int BUFFER_SIZE = 1024;
        final String CHARSET = "UTF-8";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
             BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream)) {
            char[] buffer = new char[BUFFER_SIZE];
            int len;
            while ((len = br.read(buffer)) > 0) {
                dataOutputStream.write(new String(buffer, 0, len).getBytes("UTF-8"));
                dataOutputStream.flush();
            }
        } finally {
            inputStream.close();
        }
    }

    public static String getEndLine() {
        return System.getProperty("line.separator");
    }

    public static String defaultCharset() {
        return "UTF-8";
    }
}
