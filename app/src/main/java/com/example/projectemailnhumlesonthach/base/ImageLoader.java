package com.example.projectemailnhumlesonthach.base;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.example.projectemailnhumlesonthach.R;

@GlideModule
public class ImageLoader extends AppGlideModule {

    public static ImageLoader mInstance;
    public ImageLoader(){
    }

    public static ImageLoader getInstance(){
        if(mInstance == null){
            mInstance = new ImageLoader();
        }
        return mInstance;
    }

    public void loadImage(Context context, String url, ImageView imageView, RequestOptions requestOptions){
        try{
            GlideApp.with(context).setDefaultRequestOptions(requestOptions)
                    .load(url)
                    .centerCrop().into(imageView);

        }catch (Exception e){
        }
    }
    private void loadImageNoOne(Context activity, ImageView imageView) {
        GlideApp.with(activity)
                .load(R.drawable.ic_laucher_transparent)
                .into(imageView);
    }

    public void loadImageUser(final Context activity, final String url, final ImageView ivImage) {
        if (url == null || url.isEmpty()) {
            loadImageNoOne(activity, ivImage);
            return;
        }
        GlideApp.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_laucher_transparent)
                .error(R.drawable.ic_laucher_transparent)
                .override(200, 200)
                .centerCrop()
                .into(ivImage)
                .waitForLayout();
    }
}


