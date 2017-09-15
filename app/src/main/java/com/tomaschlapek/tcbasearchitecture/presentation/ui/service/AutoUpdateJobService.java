package com.tomaschlapek.tcbasearchitecture.presentation.ui.service;

import com.firebase.jobdispatcher.JobService;

/**
 * Created by tomaschlapek on 22/8/17.
 */

public class AutoUpdateJobService extends JobService {

  /* Private Constants ****************************************************************************/
  /* Public Constants *****************************************************************************/

  public static final String AUTO_UPDATE_JOB_TAG = "tag_auto_update_job";

  /* Private Attributes ***************************************************************************/
  /* Constructor **********************************************************************************/

  public AutoUpdateJobService() {
  }

  /* Public Static Methods ************************************************************************/
  /* Public Methods *******************************************************************************/

  @Override
  public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
    return false;
  }

  @Override
  public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
    return false;
  }

  /* Private Methods ******************************************************************************/
  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
