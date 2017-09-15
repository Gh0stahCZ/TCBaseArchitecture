package com.tomaschlapek.tcbasearchitecture.core.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.widget.NetworkInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.tomaschlapek.tcbasearchitecture.util.Constants.CACHE_SIZE;
import static com.tomaschlapek.tcbasearchitecture.util.Constants.JSON_DATE_FORMAT;
import static com.tomaschlapek.tcbasearchitecture.util.MiscMethods.bodyToString;

@Module
public class NetModule {

  @Provides
  @Singleton
  NetworkInterceptor provideNetworkInterceptor() {
    return new NetworkInterceptor();
  }

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient(Cache cache, NetworkInterceptor networkInterceptor) {
    return createApiClient(cache, networkInterceptor);
  }

  @Provides
  @Singleton
  public Retrofit provideRetrofit(Context context, OkHttpClient client,
    GsonConverterFactory gsonConverterFactory) {
    return new Retrofit.Builder()
      .baseUrl(context.getResources().getString(R.string.base_url))
      .client(client)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(gsonConverterFactory)
      .build();
  }

  @Provides
  @Singleton
  public GsonConverterFactory provideGson() {
    Gson gson = new GsonBuilder()
      .setDateFormat(JSON_DATE_FORMAT)
      .create();
    return GsonConverterFactory.create(gson);
  }

  @Provides
  @Singleton
  public Picasso providePicasso(Context context) {

    return new Picasso.Builder(context)
      .downloader(new OkHttpDownloader(context))
      .listener((picasso, uri, exception) -> Timber.d("Exception " + exception.getStackTrace()))
      .build();
  }

  @Provides
  @Singleton
  Cache provideOkHttpCache(Context context) {
    return new Cache(context.getCacheDir(), CACHE_SIZE);
  }


  /**
   * Creates OkHttp client with 10MiB cache.
   *
   * @return Instance of OkHttp client.
   */
  private OkHttpClient createApiClient(Cache cache, NetworkInterceptor interceptor) {

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.addInterceptor(interceptor);
    builder.addInterceptor(chain -> {
      Request request =
        chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
      Timber.d("Request: [%s] %s", request.method(), request.url());
      Timber.d("Request: %s", request.headers().toString());
      if (request.body() != null) {
        Timber.d("Request: %s", bodyToString(request.body()));
      }

      // TODO Uncomment this call for simulation of slow internet connection.
      // MiscMethods.expensiveOperation();

      return chain.proceed(request);
    });
    builder.cache(cache);
    return builder.build();
  }
}