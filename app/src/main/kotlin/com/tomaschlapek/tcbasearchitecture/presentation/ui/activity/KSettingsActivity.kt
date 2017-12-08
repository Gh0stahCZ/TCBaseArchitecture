package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivitySettingsBinding
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KSettingsPresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISettingsActivityView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KToolbarActivity
import com.tomaschlapek.tcbasearchitecture.presentation.ui.fragment.ExamplePrefFragment
import com.tomaschlapek.tcbasearchitecture.util.ActivityBinder
import com.tomaschlapek.tcbasearchitecture.util.Konstants
import com.tomaschlapek.tcbasearchitecture.util.str
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Created by tomaschlapek on 15/9/17.
 */
class KSettingsActivity : KToolbarActivity<KISettingsActivityView, KSettingsPresenterImpl>(), KISettingsActivityView {

  /* Private Attributes ***************************************************************************/

  private val mViews: ActivitySettingsBinding by ActivityBinder(R.layout.activity_settings)
  lateinit var prefFragment: ExamplePrefFragment

  /* Public Methods *******************************************************************************/

  override fun abortNotification(): Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onCreatePresenter(savedInstanceState)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == Konstants.LOCTION_PICKER_REQUEST) {
      if (resultCode == RESULT_OK) {
        val place = PlaceAutocomplete.getPlace(this, data)
        Timber.i("Place:  ${place.name}")
        presenter.setLocation(place)
      } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
        val status = PlaceAutocomplete.getStatus(this, data)
        Timber.e("Status:  ${status.statusMessage}")
        showToast(App.getResString(R.string.location_error))
      } else if (resultCode == RESULT_CANCELED) {
        showToast(App.getResString(R.string.location_error))
      }
    }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    init()
  }

  override fun showToast(message: String?) {
    toast(message.toString())
  }

  override fun showPlace(place: Place) {
    prefFragment.showLocation(place.name.toString())
  }

  override fun getPresenterClass(): Class<KSettingsPresenterImpl> {
    return KSettingsPresenterImpl::class.java
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_settings)
  }

  /* Private Methods ******************************************************************************/

  fun init() {
    mViews
    prefFragment = KNavigationHelper.addExamplePrefFragment(this, R.id.content_container)
  }
}