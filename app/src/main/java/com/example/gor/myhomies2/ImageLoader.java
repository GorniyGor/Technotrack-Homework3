package com.example.gor.myhomies2;

import android.graphics.Bitmap;
import android.media.Image;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Gor on 07.04.2017.
 */



public class ImageLoader {

    private ExecutorService executor;

    private final ImageLoadedListener loadedListener;

    public ImageLoader(/*Handler caller*/ ImageLoadedListener imageLoadedListener) {
        /*mCallerHandler = caller;*/
        loadedListener = imageLoadedListener;
        start();
    }

    public void start() {
        int num = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
        executor = Executors.newFixedThreadPool(num);
    }

    public void  toLoadImage(final int position) { //Откуда требование final?

        if (loadedListener != null) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    HttpRequest request = new HttpRequest(ImageCache.getInstance().getUrl(position));
                    int status = request.makeRequest("IMAGE");

                    if (status == HttpRequest.REQUEST_OK) {
                        Bitmap image = request.getBitmap();
                        loadedListener.onImageLoaded(image, position);
                    }
                }
            });
            }
    }

    public void stop(){ executor.shutdown();}

    //Для каких-то действий после скачивания
    public interface ImageLoadedListener {
        void onImageLoaded(Bitmap bitmap, int position);
    }

}


