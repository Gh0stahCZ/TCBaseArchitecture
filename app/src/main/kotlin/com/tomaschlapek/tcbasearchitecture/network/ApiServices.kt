package com.tomaschlapek.tcbasearchitecture.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

/* Example requests */

// TODO - Get inspired by commented example
/*
interface SomeRequestService {

  @GET("example/example")
  fun getSomething(): Observable<Response<ExampleResponse>>

  @POST("example/{exampleId}/example")
  fun createSomething(@Path("exampleId") exampleId: String, @Body body: ExampleBody): Observable<Response<Void>>

  @PATCH("example/{exampleId}/example")
  fun updateSomething(@Path("exampleId") exampleId: String, @Body body: ExampleBody): Observable<Response<Void>>

  @Multipart
  @POST("example/photos")
  fun uploadSomePhoto(@Part file: MultipartBody.Part): Observable<Response<ExampleResponse>>

  @DELETE("example/{exampleId}")
  fun deleteOffer(@Path("exampleId") exampleId: String): Observable<Response<Void>>
}
*/



interface UserService {
  @POST("users/login")
  fun setEmailLogin(@Body body: EmailPassBody): Observable<Response<UserInfoResponse>>

  @GET("users/profile")
  fun getMyProfileInfo(): Observable<Response<UserInfoResponse>>

  @POST("users/fcm-register/{token}")
  fun sendPushToken(@Path("token") token: String): Observable<Response<Void>>

  @POST("users/fcm-unregister/{token}")
  fun disablePushToken(@Path("token") token: String): Observable<Response<Void>>

}

