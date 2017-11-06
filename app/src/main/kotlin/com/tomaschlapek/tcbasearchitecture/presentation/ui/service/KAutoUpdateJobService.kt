package com.tomaschlapek.tcbasearchitecture.presentation.ui.service

import com.firebase.jobdispatcher.JobService

/**
 * Auto update job.
 */
class KAutoUpdateJobService : JobService() {


  /* Public Methods *******************************************************************************/

  override fun onStartJob(job: com.firebase.jobdispatcher.JobParameters): Boolean {
    return false
  }

  override fun onStopJob(job: com.firebase.jobdispatcher.JobParameters): Boolean {
    return false
  }

}
