package com.tomaschlapek.tcbasearchitecture.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.util.Konstants
import com.tomaschlapek.tcbasearchitecture.util.str
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber

/**
 * Settings.
 */
class ExamplePrefFragment : PreferenceFragmentCompat() {

  /* Private Attributes ***************************************************************************/

  var mCustomView: ViewGroup? = null
  var mUpgradeView: TextView? = null

  lateinit var mName: Preference
  lateinit var mLocation: Preference
  lateinit var mPhone: Preference

  lateinit var mTerms: Preference
  lateinit var mLogout: Preference

  /* Constructor **********************************************************************************/
  /* Public Static Methods ************************************************************************/

  companion object {
    fun newInstance(): ExamplePrefFragment {
      val fragment = ExamplePrefFragment()
      return fragment
    }
  }

  /* Public Methods *******************************************************************************/

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    addPreferencesFromResource(R.xml.pref_example)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    init()
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mCustomView = view?.find<ViewGroup>(R.id.custom_view_group)

    mUpgradeView = mCustomView?.find<TextView>(R.id.pref_custom_upgrade)
    mUpgradeView?.setOnClickListener {
      toast("Click!")
    }
  }

  fun showLocation(place: String) {
    mLocation.summary = place
  }

  /* Private Methods ******************************************************************************/

  private fun init() {

    mName = findPreference(str(R.string.key_name))
    mLocation = findPreference(str(R.string.key_location))
    mPhone = findPreference(str(R.string.key_phone))

    mTerms = findPreference(str(R.string.key_terms))
    mLogout = findPreference(str(R.string.key_logout))

    mName.setOnPreferenceChangeListener { preference, newValue ->
      mName.summary = newValue.toString()
      true
    }

    mLocation.setOnPreferenceClickListener {
      findPlace()
      true
    }

    mPhone.setOnPreferenceChangeListener { preference, newValue ->
      mPhone.summary = newValue.toString()
      true
    }


    mTerms.setOnPreferenceClickListener {
      KNavigationHelper.openBrowser(context, "http://www.example.com")
      true
    }

    mLogout.setOnPreferenceClickListener {
      App.getAppComponent().provideUserEngine().clearUserSession()
      KNavigationHelper.openSignInActivity(context, true)
      true
    }
  }

  fun findPlace() {
    try {
      val filter = AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build()
      val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
        .setFilter(filter)
        .build(activity)
      activity.startActivityForResult(intent, Konstants.LOCTION_PICKER_REQUEST)
    } catch (e: GooglePlayServicesRepairableException) {
      Timber.e(e.localizedMessage)
      toast(str(R.string.location_error))
    } catch (e: GooglePlayServicesNotAvailableException) {
      Timber.e(e.localizedMessage)
      toast(str(R.string.location_error))
    }
  }
}