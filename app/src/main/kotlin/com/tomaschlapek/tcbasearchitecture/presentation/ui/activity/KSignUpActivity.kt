package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivitySignUpBinding
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KSignUpPresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISignUpActivityView
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
class KSignUpActivity : KToolbarActivity<KISignUpActivityView, KSignUpPresenterImpl>(), KISignUpActivityView {

  /* Private Attributes ***************************************************************************/

  /**
   * Data binding.
   */
  private val mViews: ActivitySignUpBinding by ActivityBinder(R.layout.activity_sign_up)

  val mAuth = FirebaseAuth.getInstance()

  /* Public Methods *******************************************************************************/

  override fun abortNotification(): Boolean = false

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val menuRes: Int = R.menu.menu_sign_up
    menuInflater.inflate(menuRes, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val itemId = item.itemId
    if (itemId == R.id.forget_pass_item) {
      presenter.onForgetPassClick()
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

  override fun getPresenterClass(): Class<KSignUpPresenterImpl> {
    return KSignUpPresenterImpl::class.java
  }

  override fun onSuccessfulSignUp() {
    KNavigationHelper.openKSampleActivity(this, true)
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_sign_up)
  }

  override fun onForgetPass() {
    showNotImplemented()
  }

  /* Private Methods ******************************************************************************/

  private fun init() {

    val emailChangeObservable = RxTextView.textChangeEvents(mViews.sampleSignUpEmailEditText)
    val passwordChangeObservable = RxTextView.textChangeEvents(mViews.sampleSignUpPassEditText)

    // force-disable the button
    mViews.sampleSignUpButton.isEnabled = false

    checkEmail(emailChangeObservable)

    checkPassword(passwordChangeObservable)

    checkValidity(emailChangeObservable, passwordChangeObservable)

    mViews.sampleSignUpButton.setOnClickListener { v ->

      val email = mViews.sampleSignUpEmailEditText.text.toString()
      val pass = mViews.sampleSignUpPassEditText.text.toString()

      presenter.onSignUpButtonClick(email, pass)
    }
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
      .debounce(KSignUpPresenterImpl.MIN_TEXT_TYPING_INTERVAL.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe({ aBoolean -> mViews.sampleSignUpButton.isEnabled = aBoolean!! }) { e -> Timber.e("e: " + e.localizedMessage) }
  }

  private fun checkPassword(passwordChangeObservable: Observable<TextViewTextChangeEvent>) {
    passwordChangeObservable
      .skip(1)
      .debounce(KSignUpPresenterImpl.MIN_TEXT_TYPING_INTERVAL.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe({ passObservable ->
        val pass = passObservable.text().toString().trim { it <= ' ' }

        if (presenter.isValidPassword(pass)) {
          mViews.sampleSignUpPassInput.isErrorEnabled = false
        } else {
          mViews.sampleSignUpPassInput.error = str(R.string.sample_sign_invalid_pass)
        }
      }
      ) { e -> Timber.e("e: " + e.localizedMessage) }
  }

  private fun checkEmail(emailChangeObservable: Observable<TextViewTextChangeEvent>) {
    emailChangeObservable
      .skip(1)
      .debounce(KSignUpPresenterImpl.MIN_TEXT_TYPING_INTERVAL.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe({ emailObservable ->
        val email = emailObservable.text().toString().trim { it <= ' ' }

        if (presenter.isValidEmail(email)) {
          mViews.sampleSignUpEmailInput.isErrorEnabled = false
        } else {
          mViews.sampleSignUpEmailInput.error = str(R.string.sample_sign_invalid_email)
        }
      }) { e -> Timber.e("e: " + e.localizedMessage) }
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}