package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;

import com.tomaschlapek.tcbasearchitecture.R;
import com.tomaschlapek.tcbasearchitecture.databinding.ActivitySampleBinding;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.SamplePresenterImpl;
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.ISampleActivityView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.BottomNavigationActivity;

import timber.log.Timber;

/**
 * Created by tomaschlapek on 17/5/17.
 */

public class SampleActivity
  extends BottomNavigationActivity<ISampleActivityView, SamplePresenterImpl>
  implements ISampleActivityView, SearchView.OnQueryTextListener {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  private ActivitySampleBinding mViews;

  /* Public Methods *******************************************************************************/

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    int menuRes = R.menu.menu_sample;
    getMenuInflater().inflate(menuRes, menu);

    MenuItem menuItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
    searchView.setOnQueryTextListener(this);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.share_item) {
      getPresenter().onShareDialog();
      return true;
    }

    return false;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViews = DataBindingUtil
      .inflate(getLayoutInflater(), R.layout.activity_sample, getContentContainer(), true);

    Timber.i("Binding: " + mViews);

    onCreatePresenter(savedInstanceState);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    init();
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    if (newText.length() > 3) {
      Timber.d("Search text: " + newText);
    }
    return false;
  }

  @Override
  public int getSelectedItemId() {
    return R.id.action_favorites;
  }

  /* Private Methods ******************************************************************************/

  private void init() {
    mViews.sampleButton.setOnClickListener(view -> getPresenter().onButtonClick());
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/

  @Override
  public Class getPresenterClass() {
    return SamplePresenterImpl.class;
  }

  @Override
  public void showToast(String text) {
    mViews.sampleTextView.setText(getPresenter().getSharingText());
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    mRealmHelper.getLoggedUser(getPresenter().getRealm());
  }
}
