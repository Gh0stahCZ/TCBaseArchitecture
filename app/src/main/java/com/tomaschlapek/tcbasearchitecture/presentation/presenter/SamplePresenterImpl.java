package com.tomaschlapek.tcbasearchitecture.presentation.presenter;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.ISamplePresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.ISampleActivityView;

import io.realm.Realm;

import timber.log.Timber;

/**
 * Created by tomaschlapek on 17/5/17.
 */

public class SamplePresenterImpl extends ActivityPresenter<ISampleActivityView> implements
  ISamplePresenter {

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
  public void onBindView(@NonNull ISampleActivityView view) {
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
  public void onButtonClick() {
    Timber.i("onButtonClick()");

    if(getView() != null) {
      getView().showToast("Button clicked");
    }

  }

  @Override
  public String getSharingText() {
    return App.getResString(R.string.me);
  }

  public Realm getRealm(){
    return mRealm;
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
