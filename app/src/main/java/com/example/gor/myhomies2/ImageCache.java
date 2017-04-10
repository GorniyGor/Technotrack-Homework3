package com.example.gor.myhomies2;

import android.graphics.Bitmap;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by Gor on 06.04.2017.
 */

/**
 * Area where are stored data: URL and Image
 *  Using in Downloader-class
 */
public class ImageCache {

    private static volatile ImageCache sSelf;
    private ArrayList<String> mUrls = new ArrayList<String>();
    private SparseArray<Bitmap> mImages = new SparseArray<Bitmap>();

    public static ImageCache getInstance() {
        if (sSelf == null) {
            synchronized (ImageCache.class) {
                if (sSelf == null) {
                    sSelf = new ImageCache();
                }
            }
        }
        return sSelf;
    }


    public Bitmap getImage(int position) {
        return mImages.get(position);
    }

    public String getUrl (int position){ return mUrls.get(position); }

    public void setUrl(String url){
        mUrls.add(url);
    }

    public void setImage(Bitmap image){
        mImages.put(mUrls.size()-1,image);
    }

    /*public int getLength (){
        return mUrls.size();
    }*/
}
