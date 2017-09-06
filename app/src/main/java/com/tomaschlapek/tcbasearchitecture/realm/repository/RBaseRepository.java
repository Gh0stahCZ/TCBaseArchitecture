package com.tomaschlapek.tcbasearchitecture.realm.repository;

import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;

/**
 * Created by tomaschlapek on 23/8/17.
 */

public class RBaseRepository {

  protected PreferenceHelper mPreferenceHelper;

  public RBaseRepository(PreferenceHelper preferenceHelper) {
    mPreferenceHelper = preferenceHelper;
  }

}
