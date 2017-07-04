package com.tomaschlapek.tcbasearchitecture.presentation.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.ISignInPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.ISignInActivityView;

import timber.log.Timber;

/**
 * Sign in activity
 */
public class SignInPresenterImpl extends ActivityPresenter<ISignInActivityView> implements
  ISignInPresenter {

  /* Public Constants *****************************************************************************/

  private static final int MIN_PASS_SIZE = 5;

  /**
   * Extra identifiers.
   */
  public static class Argument {
    public static final String GAME_ID = "game_id";
  }

  /* Private Attributes ***************************************************************************/

  /**
   * ID of game.
   */
  private String mGameId;

  /* Constructor **********************************************************************************/
  /* Public Methods *******************************************************************************/

  @Override
  public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
    super.onCreate(arguments, savedInstanceState);

    Bundle state = (savedInstanceState != null) ? savedInstanceState : arguments;
    loadArguments(state); // Load arguments.
  }

  @Override
  public void onBindView(@NonNull ISignInActivityView view) {
    super.onBindView(view);
  }

  @Override
  public void clearView() {
    super.clearView();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle bundle) {
    super.onSaveInstanceState(bundle);
    bundle.putString(Argument.GAME_ID, mGameId);
  }

  @Override
  public String getSharingText() {
    return "Shiiiiiiit";
  }

  @Override
  public boolean isValidEmail(String email) {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  @Override
  public boolean isValidPassword(String pass) {
    return !TextUtils.isEmpty(pass) && pass.length() >= MIN_PASS_SIZE;
  }

  @Override
  public void onSignInButtonClick(String email, String pass) {
    if(getView() != null){
      getView().onSuccessfulSignIn();
    }
  }

  /* Private Methods ******************************************************************************/

  /**
   * Loads arguments from SavedInstance or passed bundle.
   *
   * @param state Bundle with data.
   */
  private void loadArguments(Bundle state) {
    // Load arguments.
    if (state != null) {
      if (state.containsKey(Argument.GAME_ID)) {
        mGameId = state.getString(Argument.GAME_ID);
      }
    }
  }

  private void init() {
    Timber.d("init()");
  }


  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
