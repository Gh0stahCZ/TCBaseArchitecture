package com.tomaschlapek.tcbasearchitecture.helper

import android.content.Context
import android.os.Bundle
import com.firebase.jobdispatcher.*
import com.squareup.picasso.Picasso
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.KAutoResendPushTokenJobService
import com.tomaschlapek.tcbasearchitecture.util.Konstants

const val AUTO_RESEND_PUSH_TOKEN_JOB = "tag_auto_update_resend_push_token_job"

/**
 * Network helper.
 */
class KNetworkHelper(context: Context, val picasso: Picasso) {


  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/
  /**
   * Dispatcher for scheduling network jobs.
   */
  private val mDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))

  /* Public Methods *******************************************************************************/

  fun scheduleAutoResendPushTokenJob() {
    val autoUpdateJob = mDispatcher.newJobBuilder()
      .setService(KAutoResendPushTokenJobService::class.java) // the JobService that will be called
      .setTag(AUTO_RESEND_PUSH_TOKEN_JOB)        // uniquely identifies the job
      .build()

    mDispatcher.mustSchedule(autoUpdateJob)
  }

  fun scheduleAutoUpdateJob() {
    val autoUpdateJob = mDispatcher.newJobBuilder()
      .setService(KAutoResendPushTokenJobService::class.java) // the JobService that will be called
      .setTag(Konstants.AUTO_UPDATE_JOB_TAG)        // uniquely identifies the job
      .build()

    mDispatcher.mustSchedule(autoUpdateJob)
  }

  fun scheduleAutoUpdateComplexJob() {
    val myExtrasBundle = Bundle()
    myExtrasBundle.putString("some_key", "some_value")

    val myJob = mDispatcher.newJobBuilder()
      // the JobService that will be called
      .setService(KAutoResendPushTokenJobService::class.java)
      // uniquely identifies the job
      .setTag(Konstants.AUTO_UPDATE_JOB_TAG)
      // one-off job
      .setRecurring(false)
      // don't persist past a device reboot
      .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
      // start between 0 and 60 seconds from now
      .setTrigger(Trigger.executionWindow(0, 60))
      // don't overwrite an existing job with the same tag
      .setReplaceCurrent(false)
      // retry with exponential backoff
      .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
      // constraints that need to be satisfied for the job to run

      .setExtras(myExtrasBundle)
      .build()

    mDispatcher.mustSchedule(myJob)
  }

  fun cancelAutoUpdateJob() {
    mDispatcher.cancel(Konstants.AUTO_UPDATE_JOB_TAG)
  }

  fun cancelAllJobs() {
    mDispatcher.cancelAll()
  }


}