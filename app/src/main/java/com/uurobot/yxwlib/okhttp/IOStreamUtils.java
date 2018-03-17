package com.uurobot.yxwlib.okhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/13.
 */

public class IOStreamUtils {
        public IOStreamUtils() {
        }

        public static String formatResponse(Response response) {
                InputStream inputStream = response.body().byteStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = null;
                String line = null;

                try {
                        line = reader.readLine();

                        for(result = line; (line = reader.readLine()) != null; result = result + line) {
                                ;
                        }
                } catch (IOException var6) {
                        var6.printStackTrace();
                }

                return result;
        }
}