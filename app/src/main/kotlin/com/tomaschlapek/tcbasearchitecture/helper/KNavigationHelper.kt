package com.tomaschlapek.tcbasearchitecture.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import com.google.android.gms.maps.model.LatLng
import com.tbruyelle.rxpermissions.RxPermissions
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSampleActivity
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSignInActivity
import com.tomaschlapek.tcbasearchitecture.util.str
import org.jetbrains.anko.*
import pl.aprilapps.easyphotopicker.EasyImage
import timber.log.Timber


/**
 * Navigation helper class.
 */
class KNavigationHelper {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  private val mTime: Long = System.currentTimeMillis()

  /* Constructor **********************************************************************************/
  /* Static Methods *******************************************************************************/

  companion object {

    @JvmStatic
    fun openKSampleActivity(context: Context, isInitActivity: Boolean) {
      val intent = context.intentFor<KSampleActivity>(/*KSamplePresenterImpl.Argument.EXTRA_GAME_ID to "game"*/)
      launchActivity(intent, context, isInitActivity)
    }


    @JvmStatic
    fun openKOnboardingActivity(context: Context, isInitActivity: Boolean) {
      val intent = context.intentFor<KSampleActivity>(/*KSamplePresenterImpl.Argument.EXTRA_GAME_ID to "game"*/)
      launchActivity(intent, context, isInitActivity)
    }


    @JvmStatic
    fun openSignInActivity(context: Context, isInitActivity: Boolean) {
      val intent = context.intentFor<KSignInActivity>(/*KSamplePresenterImpl.Argument.EXTRA_GAME_ID to "game"*/)
      launchActivity(intent, context, isInitActivity)
    }


    @JvmStatic
    fun openSignUpActivity(context: Context, isInitActivity: Boolean) {
      val intent = context.intentFor<KSignInActivity>(/*KSamplePresenterImpl.Argument.EXTRA_GAME_ID to "game"*/)
      launchActivity(intent, context, isInitActivity)
    }

//    @JvmStatic
//    fun openSettingsActivity(context: Context, isInitActivity: Boolean) {
//      val intent = context.intentFor<KSettingsActivity>(/*KSamplePresenterImpl.Argument.EXTRA_GAME_ID to "game"*/)
//      launchActivity(intent, context, isInitActivity)
//    }
//
//    @JvmStatic
//    fun openNewAdActivity(context: Context, isInitActivity: Boolean, dialogOption: Int) {
//      val intent = context.intentFor<KNewAdActivity>(KNewAdPresenterImpl.Argument.EXTRA_DIALOG_OPTION to dialogOption)
//      launchActivity(intent, context, isInitActivity)
//    }

    /**
     * Make custom actions.
     */

    @JvmStatic
    fun openBrowser(context: Context, url: String) {
      context.browse(url)
    }

    @JvmStatic
    fun openBrowser(context: Context, urlResId: Int) {
      val url = str(urlResId)
      context.browse(url)
    }

    @JvmStatic
    fun openNavigation(context: Context, location: LatLng? = null) {

      val gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia")
      val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
      mapIntent.`package` = "com.google.android.apps.maps"
      context.startActivity(mapIntent)
    }

    @JvmStatic
    fun openCamera(activity: Activity) {
      val rxPermissions = RxPermissions(activity)
      rxPermissions
        .request(Manifest.permission.CAMERA)
        .subscribe { granted ->
          if (granted!!) {
            // Always true pre-M
            EasyImage.openChooserWithGallery(activity, str(R.string.image_dialog_chooser), 0)
          } else {
            activity.toast(R.string.permission_not_granted)
          }
        }
    }

    @JvmStatic
    fun makeCall(context: Context, phoneNumber: String) {
      val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber))
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      context.startActivity(intent)
    }

    @JvmStatic
    fun openInitActivity(context: Context, activityName: String, isFirstRun: Boolean, isLogged: Boolean, sharingBundle: Bundle?) {
      openInitActivityWithData(context, activityName, isFirstRun, isLogged, sharingBundle)
    }

    // ******* Fragments ******* //

//    @JvmStatic
//    fun addPreferenceFragment(activity: FragmentActivity, containerId: Int) {
//      val fragment = KPreferenceFragment.newInstance()
//      val transaction = beginTransaction(activity)
//      replaceFragment(transaction, containerId, fragment, false)
//    }


    // ******* Private static functions ******* //

    private fun openInitActivityWithData(context: Context, activityName: String, isFirstRun: Boolean, isLogged: Boolean, sharingBundle: Bundle?) {
      val isInitActivity = true

      when {
        isFirstRun -> openKOnboardingActivity(context, isInitActivity)
        !isLogged -> openKSampleActivity(context, isInitActivity)
//        KBuySearchActivity::class.java.name == activityName -> openKBuySearchActivity(context, isInitActivity)
//        KBuyMineActivity::class.java.name == activityName -> openKBuyMineActivity(context, isInitActivity)
//        KLoginActivity::class.java.name == activityName -> openKLoginActivity(context, isInitActivity)
//        KCreateProfileActivity::class.java.name == activityName -> openKCreateProfileActivity(context, isInitActivity)
        else -> openKSampleActivity(context, isInitActivity)
      }

    }

    /**
     * Launches selected activity.
     */
    private fun launchActivity(intent: Intent, context: Context, isInitActivity: Boolean) {
      if (isInitActivity) {
        intent.newTask().clearTask()
      } else {
        intent.clearTop().newTask()
      }
      context.startActivity(intent)
    }


    /**
     * Replaces fragment in certain container.
     */
    private fun replaceFragment(transaction: FragmentTransaction, containerId: Int, fragment: Fragment, addToBackStack: Boolean) {
      replaceFragment(transaction, containerId, fragment, addToBackStack, -1, -1)
    }

    /**
     * Replaces fragment in certain container.
     */
    private fun replaceFragment(transaction: FragmentTransaction, containerId: Int, fragment: Fragment, addToBackStack: Boolean, enterAnim: Int, exitAnim: Int) {
      Timber.d("replaceFragment(): addToBackStack: " + addToBackStack)

      // Set transaction animations if passed.
      if (enterAnim >= 0 && exitAnim >= 0) {
        transaction.setCustomAnimations(enterAnim, R.anim.no_anim, R.anim.no_anim, exitAnim)
      }

      // Replace fragment in container.
      transaction.replace(containerId, fragment)

      // Add to back-stack.
      if (addToBackStack) {
        transaction.addToBackStack(null)
      }

      transaction.commitAllowingStateLoss()
    }

    /**
     * Commit fragment in activity.
     * @param activity Parent activity.
     * *
     * @return Fragment transaction.
     */
    @SuppressLint("CommitTransaction")
    private fun beginTransaction(activity: FragmentActivity): FragmentTransaction {
      return activity.supportFragmentManager.beginTransaction()
    }

    /**
     * Commit fragment nested in other fragment.
     * @param fragment Parent activity.
     * *
     * @return Fragment transaction.
     */
    @SuppressLint("CommitTransaction")
    private fun beginTransaction(fragment: Fragment): FragmentTransaction {
      return fragment.fragmentManager.beginTransaction()
    }
  }
}