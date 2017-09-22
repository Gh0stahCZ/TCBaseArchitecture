package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.ActivitySignInBinding;
import com.tomaschlapek.tcbasearchitecture.helper.NavigationHelper;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.SignInPresenterImpl;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISignInActivityView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by tomaschlapek on 18/5/17.
 */

public class SignInActivity extends BaseActivity<KISignInActivityView, SignInPresenterImpl>
  implements KISignInActivityView {

  /* Private Constants ****************************************************************************/

  private static final int MIN_FULL_NAME_SIZE = 5;
  private static final int MIN_PASS_SIZE = 5;
  private static final int MIN_TEXT_TYPING_INTERVAL = 500; // 500 ms

  /* Private Attributes ***************************************************************************/

  /**
   * Data binding.
   */
  ActivitySignInBinding mViews;

  /* Public Methods *******************************************************************************/

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViews = DataBindingUtil
      .inflate(getLayoutInflater(), R.layout.activity_sign_in, getContentContainer(), true);

    onCreatePresenter(savedInstanceState);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    init();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public Class getPresenterClass() {
    return SignInPresenterImpl.class;
  }

  @Override
  public void onSuccessfulSignIn() {
    NavigationHelper.openKSampleActivity(this, false);
    finish();
  }

  /* Private Methods ******************************************************************************/

  private void init() {

    Observable<TextViewTextChangeEvent> emailChangeObservable =
      RxTextView.textChangeEvents(mViews.sampleSignInEmailEditText);
    Observable<TextViewTextChangeEvent> passwordChangeObservable =
      RxTextView.textChangeEvents(mViews.sampleSignInPassEditText);

    // force-disable the button
    mViews.sampleSignInButton.setEnabled(false);

    checkEmail(emailChangeObservable);

    checkPassword(passwordChangeObservable);

    checkValidity(emailChangeObservable, passwordChangeObservable);

    mViews.sampleSignInButton.setOnClickListener(v -> getPresenter()
      .onSignInButtonClick(mViews.sampleSignInEmailEditText.getText().toString(),
        mViews.sampleSignInPassEditText.getText().toString()));

    mViews.sampleSignInLinkTextView.setOnClickListener(v -> onSignUpLinkClick());
  }

  private void checkValidity(Observable<TextViewTextChangeEvent> emailChangeObservable,
    Observable<TextViewTextChangeEvent> passwordChangeObservable) {
    Observable
      .combineLatest(emailChangeObservable, passwordChangeObservable,
        (emailObservable, passObservable) -> {
          String email = emailObservable.text().toString().trim();
          String pass = passObservable.text().toString().trim();

          boolean emailCheck = getPresenter().isValidEmail(email);
          boolean passwordCheck = getPresenter().isValidPassword(pass);
          return emailCheck && passwordCheck;
        })
      .debounce(MIN_TEXT_TYPING_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe(aBoolean -> {
        mViews.sampleSignInButton.setEnabled(aBoolean);
      }, (e) -> Timber.e("e: " + e.getLocalizedMessage()));
  }

  private void checkPassword(Observable<TextViewTextChangeEvent> passwordChangeObservable) {
    passwordChangeObservable
      .skip(1)
      .debounce(MIN_TEXT_TYPING_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe(passObservable -> {
          String pass = passObservable.text().toString().trim();

          if (getPresenter().isValidPassword(pass)) {
            mViews.sampleSignInPassInput.setErrorEnabled(false);
          } else {
            mViews.sampleSignInPassInput
              .setError(App.getResString(R.string.sample_sign_in_invalid_pass));
          }
        }, (e) -> Timber.e("e: " + e.getLocalizedMessage())
      );
  }

  private void checkEmail(Observable<TextViewTextChangeEvent> emailChangeObservable) {
    emailChangeObservable
      .skip(1)
      .debounce(MIN_TEXT_TYPING_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe(emailObservable -> {
        String email = emailObservable.text().toString().trim();

        if (getPresenter().isValidEmail(email)) {
          mViews.sampleSignInEmailInput.setErrorEnabled(false);
        } else {
          mViews.sampleSignInEmailInput
            .setError(App.getResString(R.string.sample_sign_in_invalid_email));
        }
      }, (e) -> Timber.e("e: " + e.getLocalizedMessage()));
  }

  private void onSignUpLinkClick() {
    NavigationHelper.openSignUpActivity(this, false);
  }


  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
