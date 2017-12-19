package com.tomaschlapek.tcbasearchitecture.engine

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.helper.INVALID_STRING
import com.tomaschlapek.tcbasearchitecture.helper.KNetworkHelper
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.helper.KRealmHelper
import com.tomaschlapek.tcbasearchitecture.network.EmailPassBody
import com.tomaschlapek.tcbasearchitecture.network.UserInfoResponse
import com.tomaschlapek.tcbasearchitecture.network.UserService
import com.tomaschlapek.tcbasearchitecture.subscriber.DefaultEmptySubscriber
import com.tomaschlapek.tcbasearchitecture.subscriber.DefaultSubscriber
import com.tomaschlapek.tcbasearchitecture.subscriber.LoadingSubscriber
import com.tomaschlapek.tcbasearchitecture.util.applyTransform
import com.tomaschlapek.tcbasearchitecture.util.createErrorResponse
import org.jetbrains.anko.toast
import retrofit2.Response
import retrofit2.Retrofit
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription


/**
 * Holds current state of user and makes network requests.
 */
class UserEngine(private var retrofit: Retrofit, private var preferenceHelper: KPreferenceHelper, private val netHelper: KNetworkHelper, private val realmHelper: KRealmHelper) {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  /**
   * State that indicates loading progress.
   */
  private var loading = false
    set(value) {
      field = value
      propagateLoading()
    }

  private var lastMyProfileInfoRequest: CompositeSubscription? = null
  private var lastEmailLoginRequest: CompositeSubscription? = null
  private var lastSendPushTokenRequest: CompositeSubscription? = null
  private var lastDisablePushTokenRequest: CompositeSubscription? = null

  // Subjects ************************** //

  private var myProfileInfoOutputSubject = PublishSubject.create<Response<UserInfoResponse>>()
  private var emailLoginOutputSubject = PublishSubject.create<Response<UserInfoResponse>>()
  private var sendPushTokenOutputSubject = PublishSubject.create<Response<Void>>()
  private var disablePushTokenOutputSubject = PublishSubject.create<Response<Void>>()

  private var emailVerifiedOutputSubject = BehaviorSubject.create<Boolean>()
  private var loadingOutputSubject = BehaviorSubject.create<Boolean>()

  /* Public Methods *******************************************************************************/

  /**
   * Starts user session and sends FCM token to server.
   */
  fun startUserSession(loggedUser: FirebaseUser) {
    preferenceHelper.userLoginToken = loggedUser.uid
    realmHelper.createRUser(loggedUser)

    val fcmToken = preferenceHelper.userFirebaseToken
    // TODO Uncomment when ready   requestSendPushToken(fcmToken) // ! Warning ! - user will not get response
  }

  /**
   * Clears user session and sends request for disabling FCM token on server.
   */
  fun clearUserSession() {
    val fcmToken = preferenceHelper.userFirebaseToken
    requestDisablePushToken(fcmToken) // ! Warning ! - user will not get response

    netHelper.cancelAllJobs()
    preferenceHelper.clearUserData()
    realmHelper.clearDB()
    FirebaseAuth.getInstance().signOut()
  }

  fun isUserLogged(): Boolean {
    return !preferenceHelper.userLoginToken.isNullOrBlank() && preferenceHelper.userLoginToken != INVALID_STRING
  }

  fun loadAndRequest(req: () -> Unit) {
    loading = true
    req()
  }

  fun handleError() {
    App.getAppComponent().provideContext().toast(R.string.general_error_message)
    loading = false
  }

  //******** REGISTRATIONS ************************************************************************/

  fun registerLoadingSubscriber(subscriber: LoadingSubscriber<Boolean>): CompositeSubscription {
    return CompositeSubscription(loadingOutputSubject.subscribe(subscriber))
  }

  fun registerEmailVerifiedSubscriber(subscriber: LoadingSubscriber<Boolean>): CompositeSubscription {
    return CompositeSubscription(loadingOutputSubject.subscribe(subscriber))
  }

  fun registerEmailLoginSubscriber(subscriber: DefaultSubscriber<Response<UserInfoResponse>, UserInfoResponse>): CompositeSubscription {
    return CompositeSubscription(emailLoginOutputSubject.subscribe(subscriber))
  }

  fun registerMyProfileInfoSubscriber(subscriber: DefaultSubscriber<Response<UserInfoResponse>, UserInfoResponse>): CompositeSubscription {
    return CompositeSubscription(myProfileInfoOutputSubject.subscribe(subscriber))
  }

  fun registerSendPushTokenSubscriber(subscriber: DefaultEmptySubscriber<Response<Void>>): CompositeSubscription {
    return CompositeSubscription(sendPushTokenOutputSubject.subscribe(subscriber))
  }

  fun registerDisablePushTokenSubscriber(subscriber: DefaultEmptySubscriber<Response<Void>>): CompositeSubscription {
    return CompositeSubscription(disablePushTokenOutputSubject.subscribe(subscriber))
  }

  //******** NETWORK REQUESTS *********************************************************************/

  fun requestEmailLogin(emailPassBody: EmailPassBody) {
    loadAndRequest({
      lastEmailLoginRequest?.unsubscribe()
      lastEmailLoginRequest = getEmailLogin(emailPassBody)
    })
  }


  fun requestMyProfileInfo() {
    loadAndRequest({
      lastMyProfileInfoRequest?.unsubscribe()
      lastMyProfileInfoRequest = getMyProfileInfo()
    })
  }

  /**
   * Request sending FCM token to server.
   * @return If true then request was successfully executed.
   */
  fun requestSendPushToken(token: String?, dispatchedFromJob: Boolean = false): Boolean {
    if (!token.isNullOrBlank() && isUserLogged()) {
      loadAndRequest({
        lastSendPushTokenRequest?.unsubscribe()
        lastSendPushTokenRequest = sendPushToken(token!!, dispatchedFromJob)
      })
      return true
    }
    return false
  }

  fun requestDisablePushToken(token: String) {
    if (!token.isBlank()) {
      loadAndRequest({
        lastDisablePushTokenRequest?.unsubscribe()
        lastDisablePushTokenRequest = disablePushToken(token)
      })
    }
  }

  /* Private Methods ******************************************************************************/

  /**
   * Propagates change of loading state.
   */
  private fun propagateLoading() {
    loadingOutputSubject.onNext(loading)
  }

  /* Email Login */

  private fun getEmailLogin(body: EmailPassBody) = CompositeSubscription().apply {
    add(
      retrofit.create(UserService::class.java).setEmailLogin(body)
        .onErrorResumeNext { createErrorResponse(it) }
        .applyTransform({
          emailLoginOutputSubject.onNext(it as Response<UserInfoResponse>?)
          loading = false
        }, { handleError() }))
  }


  private fun getMyProfileInfo() = CompositeSubscription().apply {
    add(
      retrofit.create(UserService::class.java).getMyProfileInfo()
        .onErrorResumeNext { createErrorResponse(it) }
        .applyTransform({
          myProfileInfoOutputSubject.onNext(it as Response<UserInfoResponse>?)
          loading = false
        }, { handleError() }))
  }

  private fun sendPushToken(token: String, dispatchedFromJob: Boolean) = CompositeSubscription().apply {
    add(
      retrofit.create(UserService::class.java).sendPushToken(token)
        .onErrorResumeNext { createErrorResponse(it) }
        .applyTransform({

          if (!it.isSuccessful && !dispatchedFromJob && isUserLogged()) {
            netHelper.scheduleAutoResendPushTokenJob()
          }

          sendPushTokenOutputSubject.onNext(it as Response<Void>?)
          loading = false
        }, { handleError() }))
  }


  private fun disablePushToken(token: String) = CompositeSubscription().apply {
    add(
      retrofit.create(UserService::class.java).disablePushToken(token)
        .onErrorResumeNext { createErrorResponse(it) }
        .applyTransform({
          disablePushTokenOutputSubject.onNext(it as Response<Void>?)
          loading = false
        }, { handleError() }))
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/

}