package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView;

/**
 * Base class with drawer.
 */
public abstract class DrawerActivity<TView extends KIBaseView, TViewModel extends
  ActivityPresenter<TView>> extends ToolbarActivity<TView, TViewModel> {

   /* Public Types *********************************************************************************/

  /**
   * Extra identifiers.
   */
  public static class Extra {
    public static final String DRAWER_OPENED = "drawer_opened";
  }

  /* Protected Attributes *************************************************************************/

  protected ViewGroup mDrawerContainer;
  protected DrawerLayout mDrawer;

  /**
   * Main NavigationView menu.
   */
  protected NavigationView mNavigationView;

  /* Private Attributes *************************************************************************/

  /**
   * Indicates if the navigation drawer is opened.
   */
  private boolean mDrawerOpened;

  /* Protected Methods ****************************************************************************/

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /**
     * Adding our layout to parent class frame layout.
     */
    getLayoutInflater().inflate(R.layout.activity_drawer, mToolbarContainer);

    mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerContainer = (ViewGroup) findViewById(R.id.drawer_container);
    mNavigationView = (NavigationView) findViewById(R.id.main_navigation);

    // Load or initialize attributes.
    if (savedInstanceState != null) {
      mDrawerOpened = savedInstanceState.getBoolean(Extra.DRAWER_OPENED, false);
    }

    //    initMenu();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    mDrawer.setDrawerListener(new DrawerListener() {
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        mDrawerOpened = true;
      }

      @Override
      public void onDrawerClosed(View drawerView) {
        mDrawerOpened = false;
      }

      @Override
      public void onDrawerStateChanged(int newState) {
      }
    });

    if (getToolbar() != null) {
      ActionBarDrawerToggle actionBarDrawerToggle =
        new ActionBarDrawerToggle(this, mDrawer, getToolbar(), R.string.app_name,
          R.string.app_name) {
          @Override
          public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            setDrawerOpened(false);
          }

          @Override
          public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            setDrawerOpened(true);
          }
        };
      mDrawer.setDrawerListener(actionBarDrawerToggle);
      actionBarDrawerToggle.setDrawerSlideAnimationEnabled(false);
      actionBarDrawerToggle.syncState();
    } else {
      mDrawer.setDrawerListener(null);
    }
  }

  @Override
  public ViewGroup getContentContainer() {
    return mDrawerContainer;
  }

  @Override
  public abstract Class getPresenterClass();

  @Override
  public void onBackPressed() {
    if (isDrawerOpened()) {
      setDrawerOpened(false);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putBoolean(Extra.DRAWER_OPENED, mDrawerOpened);
  }

  //  private void initMenu() {
  //    Context context = this;
  //
  //    BasicUserData userData = App.getAppComponent().providePreferenceHelper().getUserData();
  //    Timber.d(userData.toString());
  //    ImageView profilePic = (ImageView) findViewById(R.id.menu_profile_pic);
  //    String profilePicUrl = userData.getProfilePicUrl();
  //    if (profilePicUrl != null) {
  //      Picasso.with(this).load(profilePicUrl).into(profilePic);
  //    }
  //
  //    ((TextView) findViewById(R.id.menu_user_name)).setText(userData.getName());
  //    ((TextView) findViewById(R.id.menu_available_funds))
  //      .setText(
  //        String.format("%s%d", getString(R.string.profile_funds_available),
  //          userData.getFund().getCurrentBalance()));
  //
  //    boolean isInitActivity = false;
  //
  //    findViewById(R.id.menu_profile_manager).setOnClickListener(
  //      view -> {
  //        setDrawerOpened(false);
  //        NavigationHelper.openProfileManagerActivity(context, isInitActivity);
  //      }
  //    );
  //    findViewById(R.id.menu_project_manager).setOnClickListener(
  //      view -> {
  //        setDrawerOpened(false);
  //        NavigationHelper.openProjectManagerActivity(context, isInitActivity);
  //      }
  //
  //    );
  //    findViewById(R.id.menu_funds_manager).setOnClickListener(
  //      view ->
  //      {
  //        setDrawerOpened(false);
  //        NavigationHelper.openFundsManagerActivity(context, isInitActivity);
  //      }
  //    );
  //    findViewById(R.id.menu_messages).setOnClickListener(
  //      view ->
  //      {
  //        setDrawerOpened(false);
  //        NavigationHelper.openMessagesActivity(context, isInitActivity);
  //      }
  //
  //    );
  //    findViewById(R.id.menu_feedback).setOnClickListener(
  //      view ->
  //      {
  //        setDrawerOpened(false);
  //        NavigationHelper.openFeedbackActivity(context, isInitActivity);
  //      }
  //
  //    );
  //    findViewById(R.id.menu_settings).setOnClickListener(
  //      view ->
  //      {
  //        setDrawerOpened(false);
  //        NavigationHelper.openSettingsActivity(context, isInitActivity);
  //      }
  //
  //    );
  //    findViewById(R.id.menu_create_project).setOnClickListener(
  //      view -> {
  //        setDrawerOpened(false);
  //        NavigationHelper.openNewProjectActivity(context, isInitActivity);
  //      }
  //    );
  //  }

  /**
   * Indicates whether the drawer is opened.
   */
  public boolean isDrawerOpened() {
    return mDrawerOpened;
  }

  /**
   * Opens or closes the navigation drawer.
   *
   * @param opened Whether the drawer should be opened.
   */
  public void setDrawerOpened(boolean opened) {
    mDrawerOpened = opened;
    if (mDrawerOpened != mDrawer.isDrawerOpen(GravityCompat.START)) {
      if (mDrawerOpened) {
        mDrawer.openDrawer(GravityCompat.START);
      } else {
        mDrawer.closeDrawer(GravityCompat.START);
      }
    }
  }
}
