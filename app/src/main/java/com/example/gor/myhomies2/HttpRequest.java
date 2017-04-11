package com.example.gor.myhomies2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Gor on 28.03.2017.
 */

class HttpRequest {

    public static final int REQUEST_OK = 0;
    public static final int REQUEST_ERROR = 2;

    private final String mURL;
    private String mContent;
    private Bitmap bitmap;

        public HttpRequest(String url) {
            mURL = url;
        }

        public int makeRequest(String jsonORimage) {
            try {
                URL url = new URL(mURL);
                HttpURLConnection connection;
                try {
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setInstanceFollowRedirects(true);
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK ) {
                        Map<String, List<String>> headers = connection.getHeaderFields();
                        InputStream is = new BufferedInputStream(connection.getInputStream());
                        if(jsonORimage.compareTo("JSON") == 0) {
                            mContent = StringUtils.readInputStream(is);
                        }
                        else if (jsonORimage.compareTo("IMAGE") == 0) {
                            bitmap = BitmapFactory.decodeStream(is);
                        }
                        is.close();
                        return REQUEST_OK;
                    }
                }
                catch (SocketTimeoutException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            return REQUEST_ERROR;
        }

        public String getContent() {
            return mContent;
        }

    public Bitmap getBitmap() {
        return bitmap;
    }
    }

