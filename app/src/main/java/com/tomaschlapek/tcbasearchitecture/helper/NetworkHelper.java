package com.tomaschlapek.tcbasearchitecture.helper;

import android.content.Context;

import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

/**
 * Created by tomaschlapek on 31/8/16.
 */
public class NetworkHelper {

  /**
   * Application context.
   */
  private Context mContext;

  /**
   * Retrofit instance.
   */
  Retrofit mRetrofit;

  /**
   * Picasso instance
   */
  Picasso mPicasso;


  /* Constructor **********************************************************************************/

  public NetworkHelper(Context context, Retrofit retrofit, Picasso picasso) {
    mContext = context;
    mRetrofit = retrofit;
    mPicasso = picasso;
    init();
  }

  private void init() {
    // TODO
  }

  /**
   * Dummy method.
   *
   * @return Instance of retrofit.
   */
  public Retrofit getRetrofit() {
    return mRetrofit;
  }

  /**
   * Dummy method
   *
   * @return Instance of retrofit
   */
  public Picasso getPicasso() {
    return mPicasso;
  }
}
