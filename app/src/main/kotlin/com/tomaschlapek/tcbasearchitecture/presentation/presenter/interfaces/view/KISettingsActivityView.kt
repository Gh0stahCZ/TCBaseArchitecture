package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view

import com.google.android.gms.location.places.Place

/**
 * Created by tomaschlapek on 15/9/17.
 */
interface KISettingsActivityView : KIBaseView {
  fun showPlace(place: Place)
}