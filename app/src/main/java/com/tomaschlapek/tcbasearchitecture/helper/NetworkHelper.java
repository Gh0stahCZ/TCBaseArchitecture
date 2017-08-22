package com.tomaschlapek.tcbasearchitecture.helper;

import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.squareup.picasso.Picasso;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.service.AutoUpdateJobService;

import retrofit2.Retrofit;

/**
 * Created by tomaschlapek on 31/8/16.
 */
public class NetworkHelper {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  /**
   * Application context.
   */
  private Context mContext;

  /**
   * Dispatcher for scheduling network jobs.
   */
  private FirebaseJobDispatcher mDispatcher;

  /**
   * Retrofit instance.
   */
  private Retrofit mRetrofit;

  /**
   * Picasso instance
   */
  private Picasso mPicasso;

  /* Constructor **********************************************************************************/

  public NetworkHelper(Context context, Retrofit retrofit, Picasso picasso) {
    mContext = context;
    mRetrofit = retrofit;
    mPicasso = picasso;
    mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    init();
  }

  /* Public Static Methods ************************************************************************/
  /* Public Methods *******************************************************************************/

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

  public void scheduleAutoUpdateJob() {
    Job autoUpdateJob = mDispatcher.newJobBuilder()
      .setService(AutoUpdateJobService.class) // the JobService that will be called
      .setTag(AutoUpdateJobService.AUTO_UPDATE_JOB_TAG)        // uniquely identifies the job
      .build();

    mDispatcher.mustSchedule(autoUpdateJob);
  }

  public void scheduleAutoUpdateComplexJob() {
    Bundle myExtrasBundle = new Bundle();
    myExtrasBundle.putString("some_key", "some_value");

    Job myJob = mDispatcher.newJobBuilder()
      // the JobService that will be called
      .setService(AutoUpdateJobService.class)
      // uniquely identifies the job
      .setTag(AutoUpdateJobService.AUTO_UPDATE_JOB_TAG)
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
      .setConstraints(
        // only run on an unmetered network
        Constraint.ON_UNMETERED_NETWORK,
        // only run when the device is charging
        Constraint.DEVICE_CHARGING
      )
      .setExtras(myExtrasBundle)
      .build();

    mDispatcher.mustSchedule(myJob);
  }

  public void cancelAutoUpdateJob() {
    mDispatcher.cancel(AutoUpdateJobService.AUTO_UPDATE_JOB_TAG);
  }

  public void cancelAllJobs() {
    mDispatcher.cancelAll();
  }

  /* Private Methods ******************************************************************************/

  private void init() {
    // TODO
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/



}
