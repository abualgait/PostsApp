package com.abualgait.msayed.thiqah.shared.util.io


import android.text.TextUtils
import android.widget.ImageView
import com.abualgait.msayed.thiqah.R
import com.squareup.picasso.Picasso

object ImageSetter {


    fun loadImage(mUrl: String, mImageView: ImageView?) {
        if (!TextUtils.isEmpty(mUrl)) {
            Picasso.get()
                    .load(mUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fit().centerInside()
                    .into(mImageView)
        } else {
            mImageView?.setImageResource(R.drawable.placeholder)
        }
    }

}