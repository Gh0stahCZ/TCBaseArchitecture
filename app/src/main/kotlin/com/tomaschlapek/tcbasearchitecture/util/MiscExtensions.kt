package com.tomaschlapek.tcbasearchitecture.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.text.format.DateUtils
import android.text.format.Time
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.MemoryPolicy
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

/* ************************* Extension functions *******************************/

/**
 * Sets image from path.
 * Update Picasso config in need of change.
 */
fun ImageView.setImageFromPath(path: String) {

  App.getAppComponent().provideNetworkHelper().picasso
    .load(File(path))
    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
    .config(Bitmap.Config.RGB_565)
    .centerCrop()
    .onlyScaleDown()
    .error(R.drawable.ic_empty)
    .into(this)
}

/**
 * Loads URL to ImageView.
 * Update Picasso config in need of change.
 */
fun ImageView.loadUrl(url: String, isProfileImage: Boolean = false, placeHolderRes: Int? = null) {

  val requestMaker =
    App.getAppComponent().provideNetworkHelper().picasso
      .load(url)
      .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
      .config(Bitmap.Config.RGB_565)

  if (isProfileImage) {
    requestMaker.fit()
    requestMaker.placeholder(R.drawable.ic_no_content)
  }

  placeHolderRes?.let {
    requestMaker.placeholder(it)
  }

  requestMaker.into(this)
}

/**
 * Converts image defined by path to Base64.
 */
fun String.imagePathToBase64(): String {
  val bm = BitmapFactory.decodeFile(this)
  val baos = ByteArrayOutputStream()
  bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
  val b = baos.toByteArray()
  return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
}


/**
 * Converts double to 2 decimal places.
 */
fun Double.roundTo2DecimalPlaces() =
  BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

/**
 * Converts millis to human readable form.
 */
fun Long.convertToTime(dateFormat: String? = "dd/MM/yyyy"): String {
  // Create a DateFormatter object for displaying date in specified format.
  val formatter = SimpleDateFormat(dateFormat)

  // Create a calendar object that will convert the date and time value in milliseconds to date.
  val calendar = Calendar.getInstance()
  calendar.timeInMillis = this
  return formatter.format(calendar.time)
}


/**
 * Prints relative time of inserted millis.
 */
fun Long.covertToRelativeTime(): String {
  return DateUtils.getRelativeTimeSpanString(this, System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS).toString()
}


/**
 * Parse String in 3339 time format to millis in long.
 */
fun String.parse3339(): Long {
  val time = Time()
  time.parse3339(this)
  return time.normalize(false)
}

/**
 * Parse String in 3339 time format to human readable form.
 */
fun String.parse3339ToTime(): String {
  val time = Time()
  time.parse3339(this)
  return time.normalize(false).convertToTime()
}

/**
 * Parse String in 3339 time format to human readable form.
 */
fun String.parse3339ToRelative(): String {
  val time = Time()
  time.parse3339(this)
  return time.normalize(false).covertToRelativeTime()
}

/**
 * Parse inserted Integer price amount to price format.
 */
fun Int.parsePrice(): String = (String.format("%,d", this)).replace(',', ' ')

/**
 * Parse inserted Double price amount to price format.
 */
fun Double.parsePrice(): String = (String.format("%,d", this)).replace(',', ' ')

/**
 * Parse inserted String price amount to price format.
 */
fun String.parsePrice(): String = (String.format("%,d", this)).replace(',', ' ')

/**
 * Shows inserted text or hide TextView.
 */
fun TextView.textOrHide(input: String?) {
  if (input.isNullOrBlank()) {
    this.visibility = View.GONE
  } else {
    this.visibility = View.VISIBLE
    this.text = input
  }
}

/**
 * Change button text color after button selection.
 * Use in cases when un-selected button change background color.
 */
fun Button.notifySelectionChanged() {
  // use new text color
  if (isSelected) {
    setTextColor(ContextCompat.getColor(context, R.color.white))
  } else {
    setTextColor(ContextCompat.getColor(context, R.color.gray_70))
  }
}

/**
 * Makes view visible.
 */
fun View.visible() {
  this.visibility = View.VISIBLE
}

/**
 * Makes view invisible.
 */
fun View.invisible() {
  this.visibility = View.INVISIBLE
}

/**
 * Makes view gone.
 */
fun View.gone() {
  this.visibility = View.GONE
}

/**
 * Gets string from resources.
 */
fun Any.str(res: Int): String {
  return App.getResString(res)
}

/**
 * Gets color from resources.
 */
fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)
