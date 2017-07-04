package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base;

import android.os.Bundle;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IBaseView;

/**
 * Base class with drawer.
 */
public abstract class BottomNavigationActivity<TView extends IBaseView, TViewModel extends
  ActivityPresenter<TView>> extends DrawerActivity<TView, TViewModel> {

  /* Public Types *********************************************************************************/
  /* Protected Attributes *************************************************************************/

  protected ViewGroup mBottomNavigationContainer;
  protected BottomNavigationView mBottomNavigationView;

  /* Private Attributes *************************************************************************/
  /* Protected Methods ****************************************************************************/

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /**
     * Adding our layout to parent class frame layout.
     */
    getLayoutInflater().inflate(R.layout.activity_bottom_navigation, mDrawerContainer);

    mBottomNavigationContainer = (ViewGroup) findViewById(R.id.bottom_navigation_container);
    mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    mBottomNavigationView.setOnNavigationItemSelectedListener(
      item -> {
        switch (item.getItemId()) {
          case R.id.action_favorites:
            item.setEnabled(!item.isEnabled());
            break;
          case R.id.action_schedules:
            item.setEnabled(!item.isEnabled());
            break;
          case R.id.action_music:
            item.setEnabled(!item.isEnabled());
            break;
        }
        return true;
      });
  }

  @Override
  public ViewGroup getContentContainer() {
    return mBottomNavigationContainer;
  }

  @Override
  public abstract Class getPresenterClass();

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }
}
