package com.tomaschlapek.tcbasearchitecture.core.module

import android.content.Context
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.util.Konstants
import com.tomaschlapek.tcbasearchitecture.util.NetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton


@Module
class KNetModule {

  @Provides
  @Singleton
  internal fun provideNetworkInterceptor(): NetworkInterceptor {
    return NetworkInterceptor()
  }

  @Provides
  @Singleton
  internal fun provideOkHttpClient(cache: Cache, networkInterceptor: NetworkInterceptor): OkHttpClient {
    return createApiClient(cache, networkInterceptor)
  }

  @Provides
  @Singleton
  fun provideRetrofit(context: Context, client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
      .baseUrl(context.resources.getString(R.string.base_url))
      .client(client)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Provides
  @Singleton
  fun provideGson(): GsonConverterFactory {
    val gson = GsonBuilder()
      .setDateFormat(Konstants.JSON_DATE_FORMAT)
      .create()
    return GsonConverterFactory.create(gson)
  }

  @Provides
  @Singleton
  fun providePicasso(context: Context): Picasso {

    return Picasso.Builder(context)
      .downloader(OkHttpDownloader(context))
      .listener { picasso, uri, exception -> Timber.d("Exception " + exception.stackTrace) }
      .build()
  }

  @Provides
  @Singleton
  internal fun provideOkHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, Konstants.CACHE_SIZE.toLong())
  }


  /**
   * Creates OkHttp client with 10MiB cache.

   * @return Instance of OkHttp client.
   */
  private fun createApiClient(cache: Cache, interceptor: NetworkInterceptor): OkHttpClient {

    val builder = OkHttpClient.Builder()
    builder.addInterceptor(interceptor)
    builder.cache(cache)
    return builder.build()
  }
}