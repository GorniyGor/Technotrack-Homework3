package com.example.gor.myhomies2.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Gor on 15.04.2017.
 */

public class NewImageCache {
    private static final String TAG = "myLogs" ;
    private static volatile NewImageCache sSelf;
    private ArrayList<String> mUrls = new ArrayList<String>();
    /*private SparseArray<Bitmap> mImages = new SparseArray<Bitmap>();*/
    private LruCache<String, Bitmap> mCache;

    //LRUCache's variables-----------------------
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;
    private Context context;

    //Почему то выдаёт ошибку, если не в отдельном методе.------!!!!!!!!!---------
    public void instanceLruCache() {
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }
    //Нужно для работы с файловой системой
    public void instanceContext(Context context) {
        this.context = context;
    }

    public static NewImageCache getInstance() {
        if (sSelf == null) {
            synchronized (NewImageCache.class) {
                if (sSelf == null) {
                    sSelf = new NewImageCache();
                }
            }
        }
        return sSelf;
    }
    //--------------------------------------------------

    //Work methods------------------------------
    public Bitmap getImage(int position) {
        if(mCache.get(mUrls.get(position)) != null){
                Log.d(TAG, "Successful getting image from cache");
            return mCache.get(mUrls.get(position));
        }
        else if(getImageFromStorage(position) != null) return getImageFromStorage(position);
        //else Log.d(TAG, "GET: Изображение есть NULL");
        return null;
    }


    public String getUrl (int position){ return mUrls.get(position); }

    public void setUrl(String url){ mUrls.add(url); }

    public void setImage(Bitmap image, int position){
        if(image != null) {
            setImageIntoStorage(image, position);
            mCache.put(mUrls.get(position), image);

                Log.d(TAG, "New image " + mCache.get(mUrls.get(position)));
        }
        else    Log.d(TAG, "SET: Изображение есть NULL");
    }

    // Additional methods------------------------------------

    //Main methods
    private Bitmap getImageFromStorage(int position) {
        String state = Environment.getExternalStorageState();
        if(state == Environment.MEDIA_MOUNTED) return getImageFromExternalStorage(position);
        else return getImageFromInternalStorage(position);
    }

    private void setImageIntoStorage(Bitmap image, Integer position){
        String state = Environment.getExternalStorageState();
        if(state == Environment.MEDIA_MOUNTED) setImageIntoExternalStorage(image, position);
        else setImageIntoInternalStorage(image, position);
    }

    //Internal storage---------------------------------
    private Bitmap getImageFromInternalStorage (Integer position){
        String filename = position.toString();
        File file = new File(context.getFilesDir(), filename);
        if(file.exists()){
            try {
                InputStream is = new FileInputStream(file);
                return BitmapFactory.decodeStream(is, null, new BitmapFactory.Options());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void setImageIntoInternalStorage (Bitmap image, Integer position){
        String filename = position.toString();
        FileOutputStream outputStream;
        File file = new File(context.getFilesDir(), filename);
        try {
            outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //External storage ------------------------------------------

    public Bitmap getImageFromExternalStorage(Integer position) {
        String filename = position.toString();
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            if(file.exists()){
                try {
                    InputStream is = new FileInputStream(file);
                    return BitmapFactory.decodeStream(is, null, new BitmapFactory.Options());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }


    public void setImageIntoExternalStorage (Bitmap image, Integer position){
        String filename = position.toString();
            FileOutputStream outputStream;
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            try {
                outputStream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


}

