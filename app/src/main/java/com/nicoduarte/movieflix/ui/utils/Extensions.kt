package com.nicoduarte.movieflix.ui.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt


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

fun getDateFormatted(strDate: String?, formatOrigin: String?): String {
    return try{
        val sdfOrigin = SimpleDateFormat(formatOrigin, Locale.US)
        val sdfDestiny = SimpleDateFormat("yyyy", Locale.US)
        sdfDestiny.format(sdfOrigin.parse(strDate))
    } catch (e: Exception) {
        ""
    }
}

fun ImageView.colorPalette(color: Int): Int {
    val a: Int = 225
    val r = Color.red(color)
    val g = Color.green(color)
    val b = Color.blue(color)
    return Color.argb(
        a,
        min(r, 255),
        min(g, 255),
        min(b, 255)
    )
}