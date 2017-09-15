package com.tomaschlapek.tcbasearchitecture.presentation.presenter;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.ISignUpPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.ISignUpActivityView;

import timber.log.Timber;

/**
 * Sign up activity
 */
public class SignUpPresenterImpl extends ActivityPresenter<ISignUpActivityView> implements
  ISignUpPresenter {

  /* Public Constants *****************************************************************************/

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
  public void onBindView(@NonNull ISignUpActivityView view) {
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
    return App.getResString(R.string.me);
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
