package com.example.gor.myhomies2;

/**
 * Created by Gor on 24.03.2017.
 */
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


public class SimpleViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;

    public SimpleViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageId);
    }

    public void setImage(Bitmap map) {
        mImageView.setImageBitmap(map);
    }

    public void setImage(int image_res) {
        mImageView.setImageResource(image_res);
    }
}
