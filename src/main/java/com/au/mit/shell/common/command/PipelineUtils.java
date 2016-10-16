package com.au.mit.shell.common.command;

import java.io.*;

/**
 * Class utils for communication between commands and common functions connected with string formats
 */
public class PipelineUtils {
    private PipelineUtils() {}

    /**
     * Write output from provided InputStream to OutputStream <p>
     * Output should be compatible with UTF-8
     */
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

    /**
     * Returns endline characters for current OS
     */
    public static String getEndLine() {
        return System.getProperty("line.separator");
    }

    /**
     * Returns default charset for the Shell
     */
    public static String defaultCharset() {
        return "UTF-8";
    }
}
