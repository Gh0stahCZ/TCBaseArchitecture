package com.tomaschlapek.tcbasearchitecture.core.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tomaschlapek.tcbasearchitecture.R;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class NetModule {

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient(Context context) {
    return createApiClient(context);
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
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
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

  /**
   * Creates OkHttp client with 10MiB cache.
   *
   * @return Instance of OkHttp client.
   */
  private OkHttpClient createApiClient(Context context) {
    int cacheSize = 10 * 1024 * 1024; // 10 MiB
    Cache cache = new Cache(context.getCacheDir(), cacheSize);

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.addInterceptor(chain -> {
      Request request =
        chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
      Timber.d("Request: [%s] %s", request.method(), request.url());
      Timber.d("Request: %s", request.headers().toString());
      if (request.body() != null) {
        Timber.d("Request: %s", bodyToString(request.body()));
      }
      return chain.proceed(request);
    });
    builder.cache(cache);
    return builder.build();
  }

  private static String bodyToString(final RequestBody request) {
    try {
      final RequestBody copy = request;
      final Buffer buffer = new Buffer();
      copy.writeTo(buffer);
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "Do not work :/";
    }
  }
}