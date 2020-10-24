package com.nicoduarte.movieflix.ui.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)!!

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return format.format(this)
}

fun View.visible() { visibility = View.VISIBLE }

fun View.gone() { visibility = View.GONE }

fun ImageView.loadImage(url: String?, placeHolder: Int, circleCrop: Boolean = false) {
    var requestOptions = RequestOptions().placeholder(placeHolder)
    requestOptions = if (circleCrop) requestOptions.circleCrop() else requestOptions
    Glide.with(context).load(url).apply(requestOptions).into(this)
}

fun Context.showMessage(container: View?, message: String) {
    container?.let {
        Snackbar.make(
            container,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}