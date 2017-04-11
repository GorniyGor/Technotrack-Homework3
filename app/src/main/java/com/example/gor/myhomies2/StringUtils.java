package com.example.gor.myhomies2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Gor on 28.03.2017.
 */

class StringUtils {
   public static String readInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[16384];

        while ((read = is.read(data, 0, data.length)) != -1) {
            outputStream.write(data, 0, read);
        }

        outputStream.flush();
        return outputStream.toString("utf-8");
    }
}

