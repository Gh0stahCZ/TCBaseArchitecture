package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.view.ViewGroup
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KSamplePresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import org.jetbrains.anko.find

/**
 * Base class with drawer.
 */
abstract class KDrawerActivity<TView : KIBaseView, TViewModel : KActivityPresenter<TView>> : KToolbarActivity<TView, TViewModel>() {

  /* Public Types *********************************************************************************/

  /**
   * Extra identifiers.
   */
  object Extra {
    val DRAWER_OPENED = "drawer_opened"
  }

  /* Protected Attributes *************************************************************************/

  lateinit var drawerContainer: ViewGroup
  lateinit var drawer: DrawerLayout

  /**
   * Main NavigationView menu.
   */
  lateinit var navigationView: NavigationView

  /* Private Attributes *************************************************************************/

  /**
   * Indicates if the navigation drawer is opened.
   */
  private var drawerOpened: Boolean = false

  /* Protected Methods ****************************************************************************/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /**
     * Adding our layout to parent class frame layout.
     */
    layoutInflater.inflate(R.layout.activity_drawer, toolbarContainer)

    drawer = find<DrawerLayout>(R.id.drawer_layout)
    drawerContainer = find<ViewGroup>(R.id.drawer_container)
    navigationView = find<NavigationView>(R.id.main_navigation)

    // Load or initialize attributes.
    if (savedInstanceState != null) {
      drawerOpened = savedInstanceState.getBoolean(Extra.DRAWER_OPENED, false)
    }

    //    initMenu();
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    drawer.setDrawerListener(object : DrawerLayout.DrawerListener {
      override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

      override fun onDrawerOpened(drawerView: View) {
        drawerOpened = true
      }

      override fun onDrawerClosed(drawerView: View) {
        drawerOpened = false
      }

      override fun onDrawerStateChanged(newState: Int) {}
    })

    if (toolbar != null) {
      val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name,
        R.string.app_name) {
        override fun onDrawerClosed(drawerView: View?) {
          super.onDrawerClosed(drawerView)
          isDrawerOpened = false
        }

        override fun onDrawerOpened(drawerView: View?) {
          super.onDrawerOpened(drawerView)
          isDrawerOpened = true
        }
      }
      drawer.setDrawerListener(actionBarDrawerToggle)
      actionBarDrawerToggle.isDrawerSlideAnimationEnabled = false
      actionBarDrawerToggle.syncState()
    } else {
      drawer.setDrawerListener(null)
    }
  }

  override abstract fun getPresenterClass(): Class<TViewModel>

  override fun getContentContainer(): ViewGroup {
    return drawerContainer
  }

  override fun onBackPressed() {
    if (isDrawerOpened) {
      isDrawerOpened = false
    } else {
      super.onBackPressed()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    outState.putBoolean(Extra.DRAWER_OPENED, drawerOpened)
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
  /**
   * Opens or closes the navigation drawer.

   * @param opened Whether the drawer should be opened.
   */
  var isDrawerOpened: Boolean
    get() = drawerOpened
    set(opened) {
      drawerOpened = opened
      if (drawerOpened != drawer.isDrawerOpen(GravityCompat.START)) {
        if (drawerOpened) {
          drawer.openDrawer(GravityCompat.START)
        } else {
          drawer.closeDrawer(GravityCompat.START)
        }
      }
    }
}
