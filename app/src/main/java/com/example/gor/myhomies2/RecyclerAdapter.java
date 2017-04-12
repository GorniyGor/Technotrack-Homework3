package com.example.gor.myhomies2;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;


/**
 * Created by Gor on 24.03.2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    private final WeakReference<LayoutInflater> localInflater;
    private ImageCache imageCache;
    private ImageLoader mDataSource;
    private ImageLoader.ImageLoadedListener mLoadedListener =
            new ImageLoader.ImageLoadedListener() {
                @Override
                public void onImageLoaded(Bitmap image, int position) {
                    ImageCache.getInstance().setImage(image, position);

                    notifyItemChanged(position);
                }
            };


    public RecyclerAdapter(LayoutInflater layoutInflater) {
        localInflater = new WeakReference<LayoutInflater>(layoutInflater);
        imageCache = ImageCache.getInstance();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = localInflater.get();
        if (inflater != null) {
            mDataSource = new ImageLoader(mLoadedListener);
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
            mDataSource.toLoadImage(position);
            holder.setImage(android.R.drawable.sym_def_app_icon);
        }

    }

    @Override
    public int getItemCount() {
        return DownloadJSONAsync.countOfImages;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mDataSource.stop();
    }
}
