package com.tomaschlapek.tcbasearchitecture.util

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.format.DateUtils
import android.text.format.Time
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.MemoryPolicy
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.domain.model.GeneralError
import com.tomaschlapek.tcbasearchitecture.domain.model.GeneralErrorResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import org.jetbrains.anko.backgroundColor
import retrofit2.Response
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

/* ************************* Extension functions *******************************/

/**
 * Sets image from path.
 * Update Picasso config in need of change.
 */
fun ImageView.setImageFromPath(path: String) {

  App.getAppComponent().provideKNetworkHelper().picasso
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
    App.getAppComponent().provideKNetworkHelper().picasso
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

fun RequestBody.bodyToString(): String {
  try {
    val buffer = Buffer()
    writeTo(buffer)
    return buffer.readUtf8()
  } catch (e: IOException) {
    return "Do not work :/"
  }
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

fun Context.dpToPx(res: Int): Int = this.resources.getDimensionPixelSize(res)

/**
 * Put String to SharedPreferences.
 */
fun SharedPreferences.storeString(res: Int, value: String?) {
  val editor = this.edit()
  editor.putString(str(res), value)
  editor.apply()
}

/**
 * Get String to SharedPreferences.
 */
fun SharedPreferences.restoreString(res: Int, defVal: String? = null): String? {
  return this.getString(str(res), defVal)
}

/**
 * Put Boolean to SharedPreferences.
 */
fun SharedPreferences.storeBoolean(res: Int, value: Boolean) {
  val editor = this.edit()
  editor.putBoolean(str(res), value)
  editor.apply()
}

/**
 * Get Boolean to SharedPreferences.
 */
fun SharedPreferences.restoreBoolean(res: Int, defVal: Boolean = false): Boolean {
  return this.getBoolean(str(res), defVal)
}

/**
 * Put Int to SharedPreferences.
 */
fun SharedPreferences.storeInt(res: Int, value: Int) {
  val editor = this.edit()
  editor.putInt(str(res), value)
  editor.apply()
}

/**
 * Get String to SharedPreferences.
 */
fun SharedPreferences.restoreInt(res: Int, defVal: Int = -1): Int {
  return this.getInt(str(res), defVal)
}


fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
  val snack = Snackbar.make(this, message, length)
  snack.view.backgroundColor = context.color(R.color.colorAccent)

  snack.apply { view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply { setMargins(leftMargin, context.dpToPx(R.dimen.margin_small), rightMargin, context.dpToPx(R.dimen.margin_small)) } }.show()

  snack.f()
  snack.show()
}

fun View.snack(message: String) {
  val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
  snack.view.backgroundColor = context.color(R.color.colorAccent)
  snack.show()
}

fun View.snack(messageResId: Int) {
  val snack = Snackbar.make(this, str(messageResId), Snackbar.LENGTH_LONG)
  snack.view.backgroundColor = context.color(R.color.colorAccent)
  snack.show()
}

fun View.snack(messageResId: Int, bottomBarHeight: Int) {
  val snack = Snackbar.make(this, str(messageResId), Snackbar.LENGTH_LONG)
  snack.view.backgroundColor = context.color(R.color.colorAccent)
  //  snack.apply { view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply { setMargins(leftMargin, bottomBarHeight, rightMargin, bottomMargin) } }.show()
  snack.show()
}

fun ResponseBody.getGeneralError(): GeneralError? {

  val converter = App.getAppComponent().provideRetrofit().responseBodyConverter<GeneralErrorResponse>(GeneralErrorResponse::class.java, arrayOfNulls<Annotation>(0))
  val error: GeneralErrorResponse

  try {
    error = converter.convert(this)
  } catch (e: IOException) {
    return null
  }
  return error.error
}

fun rx.Observable<out Response<*>>.applyTransform(onResponse: (Response<*>) -> Unit, onError: () -> Unit): Subscription {
  return this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .onErrorResumeNext {
      Timber.e(it?.message)
      Observable.just(null)
    }
    .subscribe(
      {
        it?.let {
          onResponse(it)
        } ?: onError()
      }
    ) { error ->
      Timber.e(error.message)
      onError()
    }

}

/**
 * Turns off Thread Safety for lazy initialization.
 * !! NOTE !! : It should be used wisely and only when we use object on MainThread.
 */
fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
  operation()
}

@TargetApi(Build.VERSION_CODES.N)
fun Context.safeContext(): Context {

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isDeviceProtectedStorage) {
    return ContextCompat.createDeviceProtectedStorageContext(this.applicationContext) ?: this.applicationContext
  }
  return this
}

//  takeUnless { isDeviceProtectedStorage }?.run {
//    this.applicationContext.let {
//      ContextCompat.createDeviceProtectedStorageContext(it) ?: it
//    }
//  } ?: this
