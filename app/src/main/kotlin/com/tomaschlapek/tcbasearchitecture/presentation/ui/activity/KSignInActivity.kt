package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivitySignInBinding
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KSignInPresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISignInActivityView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KToolbarActivity
import com.tomaschlapek.tcbasearchitecture.util.ActivityBinder
import com.tomaschlapek.tcbasearchitecture.util.str
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Sign In screen.
 */
class KSignInActivity : KToolbarActivity<KISignInActivityView, KSignInPresenterImpl>(), KISignInActivityView {

  /* Private Attributes ***************************************************************************/

  /**
   * Data binding.
   */
  private val mViews: ActivitySignInBinding by ActivityBinder(R.layout.activity_sign_in)

  /* Public Methods *******************************************************************************/

  override fun abortNotification(): Boolean = false

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val menuRes: Int = R.menu.menu_sign_in
    menuInflater.inflate(menuRes, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val itemId = item.itemId
    if (itemId == R.id.skip_item) {
      presenter.onSkipClick()
      return true
    }
    return false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onCreatePresenter(savedInstanceState)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    init()
  }

  override fun getPresenterClass(): Class<KSignInPresenterImpl> {
    return KSignInPresenterImpl::class.java
  }

  override fun onSuccessfulSignIn() {
    KNavigationHelper.openKSampleActivity(this, true)
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_sign_in)
  }

  /* Private Methods ******************************************************************************/

  private fun init() {

    val emailChangeObservable = RxTextView.textChangeEvents(mViews.sampleSignInEmailEditText)
    val passwordChangeObservable = RxTextView.textChangeEvents(mViews.sampleSignInPassEditText)

    // force-disable the button
    mViews.sampleSignInButton.isEnabled = false

    checkEmail(emailChangeObservable)

    checkPassword(passwordChangeObservable)

    checkValidity(emailChangeObservable, passwordChangeObservable)

    mViews.sampleSignInButton.setOnClickListener { v ->

      val email = mViews.sampleSignInEmailEditText.text.toString()
      val pass = mViews.sampleSignInPassEditText.text.toString()

      presenter.onSignInButtonClick(email, pass)
    }

    mViews.sampleSignInLinkTextView.setOnClickListener { v -> onSignUpLinkClick() }
  }

  private fun checkValidity(emailChangeObservable: Observable<TextViewTextChangeEvent>,
    passwordChangeObservable: Observable<TextViewTextChangeEvent>) {
    Observable
      .combineLatest(emailChangeObservable, passwordChangeObservable
      ) { emailObservable, passObservable ->
        val email = emailObservable.text().toString().trim { it <= ' ' }
        val pass = passObservable.text().toString().trim { it <= ' ' }

        val emailCheck = presenter.isValidEmail(email)
        val passwordCheck = presenter.isValidPassword(pass)
        emailCheck && passwordCheck
      }
      .debounce(KSignInPresenterImpl.MIN_TEXT_TYPING_INTERVAL.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe({ aBoolean -> mViews.sampleSignInButton.isEnabled = aBoolean!! }) { e -> Timber.e("e: " + e.localizedMessage) }
  }

  private fun checkPassword(passwordChangeObservable: Observable<TextViewTextChangeEvent>) {
    passwordChangeObservable
      .skip(1)
      .debounce(KSignInPresenterImpl.MIN_TEXT_TYPING_INTERVAL.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe({ passObservable ->
        val pass = passObservable.text().toString().trim { it <= ' ' }

        if (presenter.isValidPassword(pass)) {
          mViews.sampleSignInPassInput.isErrorEnabled = false
        } else {
          mViews.sampleSignInPassInput.error = str(R.string.sample_sign_invalid_pass)
        }
      }
      ) { e -> Timber.e("e: " + e.localizedMessage) }
  }

  private fun checkEmail(emailChangeObservable: Observable<TextViewTextChangeEvent>) {
    emailChangeObservable
      .skip(1)
      .debounce(KSignInPresenterImpl.MIN_TEXT_TYPING_INTERVAL.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe({ emailObservable ->
        val email = emailObservable.text().toString().trim { it <= ' ' }

        if (presenter.isValidEmail(email)) {
          mViews.sampleSignInEmailInput.isErrorEnabled = false
        } else {
          mViews.sampleSignInEmailInput.error = str(R.string.sample_sign_invalid_email)
        }
      }) { e -> Timber.e("e: " + e.localizedMessage) }
  }

  private fun onSignUpLinkClick() {
    KNavigationHelper.openSignUpActivity(this, false)
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}