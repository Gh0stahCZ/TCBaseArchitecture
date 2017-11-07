package com.tomaschlapek.tcbasearchitecture.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

/**
 * Adapters for Google data binding.
 */

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: String?) {
  if (imageUri == null) {
    view.setImageURI(null)
  } else {
    view.setImageURI(Uri.parse(imageUri))
  }
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: Uri) {
  view.setImageURI(imageUri)
}

@BindingAdapter("android:src")
fun setImageDrawable(view: ImageView, drawable: Drawable) {
  view.setImageDrawable(drawable)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
  imageView.setImageResource(resource)
}