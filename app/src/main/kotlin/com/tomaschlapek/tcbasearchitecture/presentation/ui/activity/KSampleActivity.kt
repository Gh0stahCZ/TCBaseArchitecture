package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivitySampleBinding
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KSamplePresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KISampleActivityView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KBottomNavigationActivity
import com.tomaschlapek.tcbasearchitecture.util.ActivityBinder
import com.tomaschlapek.tcbasearchitecture.util.str
import org.jetbrains.anko.toast
import timber.log.Timber


/**
 * Sample Activity
 */
class KSampleActivity : KBottomNavigationActivity<KISampleActivityView, KSamplePresenterImpl>(), SearchView.OnQueryTextListener, KISampleActivityView {

  /* Private Attributes ***************************************************************************/

  private val mViews: ActivitySampleBinding by ActivityBinder(R.layout.activity_sample)

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

  override fun showToast(message: String?) {
    mViews.sampleTextView.text = presenter.getSharingText()
    toast(message.toString())
  }

  override fun getSelectedItemId(): Int {
    return R.id.action_sample
  }

  override fun getPresenterClass(): Class<KSamplePresenterImpl> {
    return KSamplePresenterImpl::class.java
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_sample)
  }

  /* Private Methods ******************************************************************************/

  fun init() {
    mViews.sampleButton.setOnClickListener { view -> presenter.onButtonClick() }
  }
}