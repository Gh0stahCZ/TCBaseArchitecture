package com.tomaschlapek.tcbasearchitecture.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

interface EmailLoginRequestService {
  @POST("users/login")
  fun setEmailLogin(@Body body: EmailPassBody): Observable<Response<UserInfoResponse>>
}


interface MyProfileInfoRequestService {
  @GET("users/profile")
  fun getMyProfileInfo(): Observable<Response<UserInfoResponse>>
}

