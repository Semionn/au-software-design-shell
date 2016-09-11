package com.au.mit.shell.common.command;

import java.io.*;

/**
 * Created by semionn on 11.09.16.
 */
public class PipelineUtils {
    private PipelineUtils() {}

    public static void ConnectStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        final int BUFFER_SIZE = 1024;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            char[] buffer = new char[BUFFER_SIZE];
            int len;
            while ((len = br.read(buffer)) > 0) {
                dataOutputStream.writeChars(new String(buffer).substring(0, len));
                dataOutputStream.flush();
            }
        } finally {
            inputStream.close();
        }
    }
}
