package com.tomaschlapek.tcbasearchitecture.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.RequestBody;
import okio.Buffer;
import timber.log.Timber;

/**
 * Useful static methods.
 */
public class MiscMethods {

  /**
   * Converts PX to DP on primary screen.
   *
   * @param px PX to convert.
   *
   * @return Resulting DP.
   */
  public static int pxToDp(Context context, int px) {
    return (int) ((px / context.getResources().getDisplayMetrics().density) + 0.5);
  }

  /**
   * Converts DP to PX on primary screen.
   *
   * @param dp DP to convert.
   *
   * @return Resulting PX.
   */
  public static int dpToPx(Context context, int dp) {
    return (int) ((dp * context.getResources().getDisplayMetrics().density) + 0.5);
  }

  /**
   * Returns country code of the current locale.
   */
  public static String getCountryCode(Context context) {
    return context.getResources().getConfiguration().locale.getCountry();
  }

  /**
   * @see Resources#getString(int)
   */
  public static String getResourceString(Context context, int id) {
    return context.getResources().getString(id);
  }

  /**
   * @see Resources#getString(int, Object...)
   */
  public static String getResourceString(Context context, int resourceId, Object... formatArgs) {
    return context.getResources().getString(resourceId, formatArgs);
  }

  /**
   * @see Resources#getBoolean(int)
   */
  public static boolean getResourceBoolean(Context context, int id) {
    return context.getResources().getBoolean(id);
  }

  /**
   * @see Resources#getDimensionPixelSize(int)
   */
  public static int getResourceDimension(Context context, int id) {
    return context.getResources().getDimensionPixelSize(id);
  }

  /**
   * Opens stream of specified asset file.
   *
   * @param assetFileName Asset file name.
   *
   * @return Opened stream of asset file.
   */
  public static InputStream getAssetFileStream(Context context, String assetFileName) {
    InputStream result = null;
    try {
      result = context.getAssets().open(assetFileName);
    } catch (IOException exception) {
      Timber.e(exception.getMessage(), exception);
    }
    return result;
  }

  public static Point getDisplaySize(WindowManager windowManager){
    try {
      if(Build.VERSION.SDK_INT > 16) {
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
      }else{
        return new Point(0, 0);
      }
    }catch (Exception e){
      e.printStackTrace();
      return new Point(0, 0);
    }
  }

  public static int dpToPx(int dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }

  /**
   * Converts network request body to String.
   * @param request Request body.
   * @return Output string.
   */
  public static String bodyToString(final RequestBody request) {
    try {
      final RequestBody copy = request;
      final Buffer buffer = new Buffer();
      copy.writeTo(buffer);
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "Do not work :/";
    }
  }

  /**
   * Simulates time-expensive operation.
   */
  public static void expensiveOperation() {
    try {
      Thread.sleep(8000);
    } catch (InterruptedException e) {
      // this is ok
    }
  }

}