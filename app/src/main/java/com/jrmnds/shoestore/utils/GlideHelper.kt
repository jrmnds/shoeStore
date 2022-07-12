package com.jrmnds.shoestore.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

object GlideHelper {

    fun converteFromBase64ToImage(base64: String, context: Context, placeholder: Int, imageView: ImageView){
        Glide.with(context).load(Base64.decode(base64, Base64.DEFAULT)).into(imageView).waitForLayout()
            .onLoadFailed(context.getDrawable(placeholder))
    }

    fun convertImageToBase64(uriData: Uri, contentResolver: ContentResolver): String{
        var base64Image = ""
        try {
            val decoder = ImageDecoder.createSource(contentResolver, uriData)
            val bitmap = ImageDecoder.decodeBitmap(decoder)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
        }catch (e: IOException){
            Log.e("Error", "Error trying to converte image to base64")
        }

        return base64Image
    }

}