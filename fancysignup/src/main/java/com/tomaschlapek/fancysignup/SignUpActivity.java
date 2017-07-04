package com.tomaschlapek.fancysignup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import android.support.v4.content.LocalBroadcastManager;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.tomaschlapek.fancysignup.databinding.ActivitySignUpBinding;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class SignUpActivity extends AppCompatActivity {

  /* Private Constants ****************************************************************************/

  private static final int MIN_FULL_NAME_SIZE = 5;
  private static final int MIN_PASS_SIZE = 5;
  private static final int MIN_TEXT_TYPING_INTERVAL = 500; // 500 ms

  /* Private Attributes ***************************************************************************/

  ActivitySignUpBinding mViews;

  /* Public Methods *******************************************************************************/

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViews = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

    mViews.sampleSignUpLoginLink
      .setText(fromHtml(getResources().getString(R.string.sample_sign_up_login)));

    mViews.sampleSignUpLoginLink.setOnClickListener(v -> onLoginLinkClick());
    mViews.sampleCreateAccountButton.setOnClickListener(v -> onCreateAccountClick());

    Observable<TextViewTextChangeEvent> fullNameChangeObservable =
      RxTextView.textChangeEvents(mViews.sampleSignUpFullNameEditText);

    Observable<TextViewTextChangeEvent> emailChangeObservable =
      RxTextView.textChangeEvents(mViews.sampleSignUpEmailEditText);

    Observable<TextViewTextChangeEvent> passChangeObservable =
      RxTextView.textChangeEvents(mViews.sampleSignUpPassEditText);

    checkFullName(fullNameChangeObservable);
    checkEmail(emailChangeObservable);
    checkPassword(passChangeObservable);
  }

  private void checkFullName(Observable<TextViewTextChangeEvent> fullNameChangeObservable) {
    fullNameChangeObservable
      .skip(1)
      .debounce(MIN_TEXT_TYPING_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe(fullNameObservable -> {
        String email = fullNameObservable.text().toString().trim();

        if (isValidFullName(email)) {
          mViews.sampleSignUpFullNameEditText
            .setCompoundDrawablesWithIntrinsicBounds(null, null,
              getResources().getDrawable(R.drawable.ic_check_black_24dp), null);
          mViews.sampleSignUpFullNameInput.setErrorEnabled(false);
        } else {
          mViews.sampleSignUpFullNameEditText.setCompoundDrawables(null, null, null, null);
          mViews.sampleSignUpFullNameInput
            .setError(getString(R.string.sample_sign_up_invalid_full_name));
        }
      });
  }

  private void checkEmail(Observable<TextViewTextChangeEvent> emailChangeObservable) {
    emailChangeObservable
      .skip(1)
      .debounce(MIN_TEXT_TYPING_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe(emailObservable -> {
        String email = emailObservable.text().toString().trim();

        if (isValidEmail(email)) {
          mViews.sampleSignUpEmailEditText
            .setCompoundDrawablesWithIntrinsicBounds(null, null,
              getResources().getDrawable(R.drawable.ic_check_black_24dp), null);
          mViews.sampleSignUpEmailInput.setErrorEnabled(false);
        } else {
          mViews.sampleSignUpEmailEditText.setCompoundDrawables(null, null, null, null);
          mViews.sampleSignUpEmailInput
            .setError(getString(R.string.sample_sign_up_invalid_email));
        }
      });
  }

  private void checkPassword(Observable<TextViewTextChangeEvent> passChangeObservable) {
    passChangeObservable
      .skip(1)
      .debounce(MIN_TEXT_TYPING_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
      .subscribe(passObservable -> {
        String email = passObservable.text().toString().trim();

        if (isValidPassword(email)) {
          mViews.sampleSignUpPassEditText
            .setCompoundDrawablesWithIntrinsicBounds(null, null,
              getResources().getDrawable(R.drawable.ic_check_black_24dp), null);
          mViews.sampleSignUpPassInput.setErrorEnabled(false);
        } else {
          mViews.sampleSignUpPassEditText.setCompoundDrawables(null, null, null, null);
          mViews.sampleSignUpPassInput
            .setError(getString(R.string.sample_sign_up_invalid_pass));
        }
      });
  }

  private void onCreateAccountClick() {
    broadcastCreateAccountMessage();
  }

  private void onLoginLinkClick() {
    broadcastSignInMessage();
  }

  private void broadcastCreateAccountMessage() {
    Log.d("sender", "Broadcasting message");
    Intent intent = new Intent(getResources().getString(R.string.broadcast_create_account));
    // You can also include some extra data.
    intent.putExtra("message", "This is my message!");
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }

  private void broadcastSignInMessage() {
    Log.d("sender", "Broadcasting message");
    Intent intent = new Intent(getResources().getString(R.string.broadcast_sign_up));
    // You can also include some extra data.
    intent.putExtra("message", "This is my message!");
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }

  public boolean isValidFullName(String pass) {
    return !TextUtils.isEmpty(pass) && pass.length() >= MIN_FULL_NAME_SIZE;
  }

  public boolean isValidEmail(String email) {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public boolean isValidPassword(String pass) {
    return !TextUtils.isEmpty(pass) && pass.length() >= MIN_PASS_SIZE;
  }

  @SuppressWarnings("deprecation")
  public static Spanned fromHtml(String html) {
    Spanned result;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
    } else {
      result = Html.fromHtml(html);
    }
    return result;
  }
}
