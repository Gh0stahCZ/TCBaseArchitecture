package com.tomaschlapek.tcbasearchitecture.engine

import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.network.EmailLoginRequestService
import com.tomaschlapek.tcbasearchitecture.network.EmailPassBody
import com.tomaschlapek.tcbasearchitecture.network.MyProfileInfoRequestService
import com.tomaschlapek.tcbasearchitecture.network.UserInfoResponse
import com.tomaschlapek.tcbasearchitecture.subscriber.DefaultSubscriber
import com.tomaschlapek.tcbasearchitecture.subscriber.LoadingSubscriber
import com.tomaschlapek.tcbasearchitecture.util.applyTransform
import org.jetbrains.anko.toast
import retrofit2.Response
import retrofit2.Retrofit
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * Holds current state of user and makes network requests.
 */
class UserEngine(private var retrofit: Retrofit, private var preferenceHelper: KPreferenceHelper) {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  /**
   * State that indicates loading progress.
   */
  private var loading = false
    get() = field
    set(value) {
      field = value
      propagateLoading()
    }

  private var lastMyProfileInfoRequest: CompositeSubscription? = null
  private var lastEmailLoginRequest: CompositeSubscription? = null

  // Subjects ************************** //

  private var myProfileInfoOutputSubject = PublishSubject.create<Response<UserInfoResponse>>()
  private var emailLoginOutputSubject = PublishSubject.create<Response<UserInfoResponse>>()

  private var loadingOutputSubject = BehaviorSubject.create<Boolean>()

  /* Public Methods *******************************************************************************/

  fun startUserSession(userInfo: UserInfoResponse) {
    preferenceHelper.setUserLoginToken(userInfo.token)
  }

  fun clearUserSession() {
    preferenceHelper.setUserLoginToken(null)
    preferenceHelper.setFbLoginToken(null)
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

  fun registerEmailLoginSubscriber(subscriber: DefaultSubscriber<Response<UserInfoResponse>, UserInfoResponse>): CompositeSubscription {
    return CompositeSubscription(emailLoginOutputSubject.subscribe(subscriber))
  }

  fun registerMyProfileInfoSubscriber(subscriber: DefaultSubscriber<Response<UserInfoResponse>, UserInfoResponse>): CompositeSubscription {
    return CompositeSubscription(myProfileInfoOutputSubject.subscribe(subscriber))
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

  /* Private Methods ******************************************************************************/

  /**
   * Propagates change of loading state.
   */
  private fun propagateLoading() {
    loadingOutputSubject.onNext(loading)
  }

  /* Email Login */

  private fun getEmailLogin(body: EmailPassBody): CompositeSubscription {
    val subscription = CompositeSubscription().apply {
      add(
        retrofit.create(EmailLoginRequestService::class.java).setEmailLogin(body).applyTransform({
          emailLoginOutputSubject.onNext(it as Response<UserInfoResponse>?)
          loading = false
        }, { handleError() }))
    }
    return subscription
  }


  private fun getMyProfileInfo(): CompositeSubscription {
    val subscription = CompositeSubscription().apply {
      add(
        retrofit.create(MyProfileInfoRequestService::class.java).getMyProfileInfo().applyTransform({
          myProfileInfoOutputSubject.onNext(it as Response<UserInfoResponse>?)
          loading = false
        }, { handleError() }))
    }
    return subscription
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/

}