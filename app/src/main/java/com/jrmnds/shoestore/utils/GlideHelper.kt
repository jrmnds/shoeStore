package com.jrmnds.shoestore.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideHelper {

    fun converteFromBase64ToImage(base64: String, context: Context, placeholder: Int, imageView: ImageView){
        Glide.with(context).asBitmap().load(base64).placeholder(placeholder).into(imageView)
    }

}