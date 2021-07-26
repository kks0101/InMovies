package com.inmovies.inmovies;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class LoadingDialog {

    Activity activity;
    Dialog dialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void showDialog(){

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // never hide
        dialog.setCancelable(true);

        // inflate custom_loading_layout.xml
        dialog.setContentView(R.layout.custom_loading_layout);

        ImageView loadingImage = dialog.findViewById(R.id.custom_loading_imageView);

        Glide.with(activity)
                .load(R.drawable.ic_loading)
                .placeholder(R.drawable.ic_loading)
                .centerCrop()
                .into(new DrawableImageViewTarget(loadingImage));

        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }

}
