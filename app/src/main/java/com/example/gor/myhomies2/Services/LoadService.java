package com.example.gor.myhomies2.Services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.example.gor.myhomies2.ImageCache;
import com.example.gor.myhomies2.ImageLoader;

public class LoadService extends Service {

    private final IBinder mBinder = new MyBinder();
    private NotifyListener mNotifyListener;

    private ImageLoader mDataSource;
    private ImageLoader.ImageLoadedListener mLoadedListener =
            new ImageLoader.ImageLoadedListener() {
                @Override
                public void onImageLoaded(Bitmap image, int position) {
                    ImageCache.getInstance().setImage(image, position);

                    //notifyItemChanged(position);
                    mNotifyListener.onNotify(position);
                }
            };

    @Override
    public IBinder onBind(Intent intent) {
        mDataSource = new ImageLoader(mLoadedListener);
        return mBinder;
    }

    //Work method
    public void loadImage(int position) {
        mDataSource.toLoadImage(position);
    }

    // КРИВОЕ МЕСТО, НУЖНО СДЕЛАТЬ ПО-ДРУГОМУ
    public void setNotifyListener(NotifyListener mNotifyListener) {
        this.mNotifyListener = mNotifyListener;
    }

    //Using in ServiceConnection
    public class MyBinder extends Binder {
        public LoadService getService() {
            return LoadService.this;
        }
    }

    //Для информирования адаптера о загрузки изображений, чтобы обновлялись изображения
    public interface NotifyListener {
        void onNotify(int position);
    }


}
