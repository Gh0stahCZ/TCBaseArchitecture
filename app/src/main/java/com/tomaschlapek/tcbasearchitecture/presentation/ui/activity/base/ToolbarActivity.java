package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import android.support.annotation.Nullable;

import com.tomaschlapek.tcbasearchitecture.App;
import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.ActivityPresenter;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IBaseView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.SampleActivity;

/**
 * Base class with drawer.
 */
public abstract class ToolbarActivity<TView extends IBaseView, TViewModel extends
  ActivityPresenter<TView>> extends BaseActivity<TView, TViewModel> {

  /* Public Types *********************************************************************************/
  /* Protected Attributes *************************************************************************/

  protected ViewGroup mToolbarContainer;
  protected Toolbar mToolbar;


  /* Private Attributes *************************************************************************/

  /* Protected Methods ****************************************************************************/

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    updateToolbar(this.getClass().getSimpleName());
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /**
     * Adding our layout to parent class frame layout.
     */
    getLayoutInflater().inflate(R.layout.activity_toolbar, mBaseContainer);

    mToolbarContainer = (ViewGroup) findViewById(R.id.toolbar_container);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    setupToolbar();
  }

  @Override
  public ViewGroup getContentContainer() {
    return mToolbarContainer;
  }

  @Override
  public abstract Class getPresenterClass();

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  public Toolbar getToolbar() {
    return mToolbar;
  }

  public void updateToolbar(String title) {
    mToolbar.setTitle(title);
  }

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/
  /* Constructor **********************************************************************************/
  /* Public Static Methods ************************************************************************/
  /* Public Methods *******************************************************************************/
  /* Private Methods ******************************************************************************/

  private void setupToolbar() {
    if (mToolbar != null) {

      // Set elevation of the toolbar to display shadow.
      if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        mToolbar.setElevation(App.dpToPx(3));
      }

      mToolbar.setTitleTextColor(Color.WHITE);

      setSupportActionBar(mToolbar);
      //      activity.getSupportActionBar().setTitle(null);

      if (this instanceof SampleActivity) {
        hideBackArrowButton();
      } else {
        showBackArrowButton();
      }
    }
  }

  private void showBackArrowButton() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void hideBackArrowButton() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
