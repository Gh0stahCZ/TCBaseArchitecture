package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.SupportMapFragment
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivityMapBinding
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KMapPresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIMapActivityView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KBottomNavigationActivity
import com.tomaschlapek.tcbasearchitecture.util.ActivityBinder
import com.tomaschlapek.tcbasearchitecture.util.str
import timber.log.Timber

/**
 * Map adapter.
 */
class KMapActivity : KBottomNavigationActivity<KIMapActivityView, KMapPresenterImpl>(), SearchView.OnQueryTextListener, KIMapActivityView {

  /* Private Attributes ***************************************************************************/

  private val mViews: ActivityMapBinding by ActivityBinder(R.layout.activity_map)

  /* Public Methods *******************************************************************************/

  override fun abortNotification(): Boolean = false

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val menuRes: Int = R.menu.menu_sample
    menuInflater.inflate(menuRes, menu)

    val menuItem = menu.findItem(R.id.action_search)
    val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
    searchView.setOnQueryTextListener(this)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val itemId = item.itemId
    if (itemId == R.id.share_item) {
      presenter.onShareDialog()
      return true
    }
    return false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onCreatePresenter(savedInstanceState)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    init()
  }

  override fun onQueryTextSubmit(query: String?): Boolean {
    return false
  }

  override fun onQueryTextChange(newText: String?): Boolean {
    if (!newText.isNullOrBlank() && newText?.length!! > 3) {
      Timber.d("Search text: " + newText)
    }
    return false
  }

  override fun getSelectedItemId(): Int {
    return R.id.action_map
  }

  override fun getPresenterClass(): Class<KMapPresenterImpl> {
    return KMapPresenterImpl::class.java
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_map)
  }

  /* Private Methods ******************************************************************************/


  private fun init() {
    mViews
    val mapFragment = supportFragmentManager.findFragmentById(R.id.example_map) as SupportMapFragment
  }
}