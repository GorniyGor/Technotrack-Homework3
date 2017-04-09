package com.example.gor.myhomies2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Gor on 07.04.2017.
 */



public class ImageLoader {

    private ExecutorService executor;

    public ImageLoader(/*OnImageLoadingListener listener, Handler caller*/) {
        /*mListener = listener;
        mCallerHandler = caller;*/
        start();
    }

    public void start() {
        int num = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
        executor = Executors.newFixedThreadPool(num);
    }

    public void toLoadImage(final int position/*final String url, Bundle developerPayload*/) { //Откуда требование final?

        if (/*mListener != null*/true) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    HttpRequest request = new HttpRequest(ImageCache.getInstance().getUrl(position));
                    int status = request.makeRequest("IMAGE");

                    if (status == HttpRequest.REQUEST_OK) {
                        Bitmap image = request.getBitmap();
                        ImageCache.getInstance().setImage(image);
                    }
                }
            });
            }
    }

    public void stop(){ executor.shutdown();}
}


