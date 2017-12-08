package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import com.firebase.jobdispatcher.JobService
import com.tomaschlapek.tcbasearchitecture.domain.model.GeneralError
import com.tomaschlapek.tcbasearchitecture.engine.UserEngine
import com.tomaschlapek.tcbasearchitecture.helper.KPreferenceHelper
import com.tomaschlapek.tcbasearchitecture.subscriber.DefaultEmptySubscriber
import dagger.android.AndroidInjection
import retrofit2.Response
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Auto update job.
 */
class KAutoResendPushTokenJobService : JobService() {

  @Inject internal lateinit var userEngine: UserEngine
  @Inject internal lateinit var preferenceHelper: KPreferenceHelper

  private var disableTokenSubscription: CompositeSubscription? = null

  override fun onCreate() {
    AndroidInjection.inject(this)
    super.onCreate()
  }

  /* Public Methods *******************************************************************************/

  /**
   * Resends FCM token to server.
   */
  override fun onStartJob(job: com.firebase.jobdispatcher.JobParameters): Boolean {
    disableTokenSubscription = userEngine.registerDisablePushTokenSubscriber(DisableTokenSubscriber(job))

    // Don't reschedule anymore if request is not valid
    if (!userEngine.requestSendPushToken(preferenceHelper.userFirebaseToken, true)) {
      jobFinished(job, false)
    }
    return true
  }

  /**
   * Stops resending.
   */
  override fun onStopJob(job: com.firebase.jobdispatcher.JobParameters): Boolean {
    disableTokenSubscription?.unsubscribe()
    return true
  }

  private inner class DisableTokenSubscriber(val job: com.firebase.jobdispatcher.JobParameters) : DefaultEmptySubscriber<Response<Void>>() {
    override fun onNextSuccess() {
      jobFinished(job, false)
    }

    override fun onNextError(error: GeneralError) {
      jobFinished(job, true)
    }
  }

}
