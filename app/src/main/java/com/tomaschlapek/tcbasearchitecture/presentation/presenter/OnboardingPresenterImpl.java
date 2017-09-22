//package com.tomaschlapek.tcbasearchitecture.presentation.presenter;
//
//import android.os.Bundle;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.tomaschlapek.tcbasearchitecture.App;
//import com.tomaschlapek.tcbasearchitecture.R;
//import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
//import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.KIOnboardingPresenter;
//import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IOnboardingActivityView;
//
//import timber.log.Timber;
//
///**
// * Onboarding activity
// */
//public class OnboardingPresenterImpl extends ActivityPresenter<IOnboardingActivityView> implements
//  KIOnboardingPresenter {
//
//  /* Public Constants *****************************************************************************/
//
//  /**
//   * Extra identifiers.
//   */
//  public static class Argument {
//    public static final String GAME_ID = "game_id";
//  }
//
//  /* Private Attributes ***************************************************************************/
//
//  /**
//   * ID of game.
//   */
//  private String mGameId;
//
//
//
//  /* Constructor **********************************************************************************/
//  /* Public Methods *******************************************************************************/
//
//  @Override
//  public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
//    super.onCreate(arguments, savedInstanceState);
//
//    Bundle state = (savedInstanceState != null) ? savedInstanceState : arguments;
//    loadArguments(state); // Load arguments.
//  }
//
//  @Override
//  public void onBindView(@NonNull IOnboardingActivityView view) {
//    super.onBindView(view);
//  }
//
//  @Override
//  public void clearView() {
//    super.clearView();
//  }
//
//  @Override
//  public void onDestroy() {
//    super.onDestroy();
//  }
//
//  @Override
//  public void onSaveInstanceState(@NonNull Bundle bundle) {
//    super.onSaveInstanceState(bundle);
//    bundle.putString(Argument.GAME_ID, mGameId);
//  }
//
//  @Override
//  public String getSharingText() {
//    return App.getResString(R.string.me);
//  }
//
////  @Override
////  public List<OnBoardingPage> createPageList() {
////    List<OnBoardingPage> onBoardingPageList = new ArrayList<>();
////
////    OnBoardingPage pageOne = createPageOne();
////    OnBoardingPage pageTwo = createPageTwo();
////    OnBoardingPage pageThree = createPageThree();
////
////    onBoardingPageList.add(pageOne);
////    onBoardingPageList.add(pageTwo);
////    onBoardingPageList.add(pageThree);
////
////    return onBoardingPageList;
////  }
//
//  /* Private Methods ******************************************************************************/
//
//  /**
//   * Loads arguments from SavedInstance or passed bundle.
//   *
//   * @param state Bundle with data.
//   */
//  private void loadArguments(Bundle state) {
//    // Load arguments.
//    if (state != null) {
//      if (state.containsKey(Argument.GAME_ID)) {
//        mGameId = state.getString(Argument.GAME_ID);
//      }
//    }
//  }
//
//  private void init() {
//    Timber.d("init()");
//  }
//
////  private OnBoardingPage createPageOne() {
////    return new OnBoardingPage.Builder()
////      .title(mActivity, R.string.sample_onboarding_page_one_title)
////      .message(mActivity, R.string.sample_onboarding_page_one_message)
////      .backgroundImage(R.mipmap.ic_launcher)
////      .build();
////  }
////
////  private OnBoardingPage createPageTwo() {
////    return new OnBoardingPage.Builder()
////      .title(mActivity, R.string.sample_onboarding_page_two_title)
////      .message(mActivity, R.string.sample_onboarding_page_two_message)
////      .permission(permission.ACCESS_FINE_LOCATION, () -> somePermissionRequest())
////      .positiveButtonText(mActivity, R.string.sample_onboarding_positive_button)
////      .negativeButtonText(mActivity, R.string.sample_onboarding_negative_button)
////      .build();
////  }
////
////  private OnBoardingPage createPageThree() {
////    return new OnBoardingPage.Builder()
////      .title(mActivity, R.string.sample_onboarding_page_three_title)
////      .message(mActivity, R.string.sample_onboarding_page_three_message)
////      .build();
////  }
//
//  private void somePermissionRequest() {
//    // TODO Handle your permission here
//    if (getView() != null) {
//      getView().onPermissionRequest();
//    }
//  }
//
//  /* Getters / Setters ****************************************************************************/
//  /* Inner classes ********************************************************************************/
//}
