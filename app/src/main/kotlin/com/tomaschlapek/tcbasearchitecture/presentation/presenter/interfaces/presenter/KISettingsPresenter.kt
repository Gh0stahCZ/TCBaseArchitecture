package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter

import com.google.android.gms.location.places.Place

/**
 * Created by tomaschlapek on 15/9/17.
 */
interface KISettingsPresenter {
  fun setLocation(place: Place)

}