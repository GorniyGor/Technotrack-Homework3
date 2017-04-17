package com.example.gor.myhomies2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.gor.myhomies2.Services.LoadService;
import com.example.gor.myhomies2.Services.NewImageCache;

import java.lang.ref.WeakReference;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * Created by Gor on 24.03.2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    private final WeakReference<LayoutInflater> localInflater;
    private NewImageCache imageCache;

    //For Service---------------------
    private LoadService service;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            LoadService.MyBinder b = (LoadService.MyBinder) binder;
            service = b.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    };
    private LoadService.NotifyListener mNotifyListener = new LoadService.NotifyListener(){
        @Override
        public void onNotify(int position){
            notifyItemChanged(position);
        }
    };
    //------------------------------------


    public RecyclerAdapter(LayoutInflater layoutInflater) {
        localInflater = new WeakReference<LayoutInflater>(layoutInflater);
        imageCache = NewImageCache.getInstance();
        imageCache.instanceLruCache();
        imageCache.instanceContext(localInflater.get().getContext());

        Intent i = new Intent(localInflater.get().getContext(), LoadService.class);
        localInflater.get().getContext().bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = localInflater.get();
        if (inflater != null) {

            // Вставил именно сюда, чтобы успели присоединиться к сервису в конструкторе
            if (service != null) {
                service.setNotifyListener(mNotifyListener);
            }
            return new SimpleViewHolder(inflater.inflate(R.layout.activity_main, parent, false));
        }
        else {
            throw new  RuntimeException("Oooops, looks like activity is dead");
        }
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        Bitmap bm = imageCache.getImage(position);
        if (bm != null) {
            holder.setImage(bm);
        }
        else {
            if (service != null) {
                service.loadImage(position);
            }
            holder.setImage(android.R.drawable.sym_def_app_icon);
        }

    }

    @Override
    public int getItemCount() {
        return DownloadJSONAsync.countOfImages;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        service.unbindService(mServiceConnection);//Сервис  - это контекст, поэтому можем вызвать данный метод
    }
}
