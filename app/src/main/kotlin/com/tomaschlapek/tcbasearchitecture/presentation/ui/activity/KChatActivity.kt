package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.databinding.ActivityChatBinding
import com.tomaschlapek.tcbasearchitecture.helper.KNavigationHelper
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KChatPresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIChatActivityView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KBottomNavigationActivity
import com.tomaschlapek.tcbasearchitecture.util.str
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Chat activity.
 */
class KChatActivity : KBottomNavigationActivity<KIChatActivityView, KChatPresenterImpl>(), KIChatActivityView {

  /* Private Attributes ***************************************************************************/

  lateinit var mViews: ActivityChatBinding

  /* Public Methods *******************************************************************************/

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val menuRes: Int = R.menu.menu_chat
    menuInflater.inflate(menuRes, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val itemId = item.itemId
    if (itemId == R.id.share_item) {
      presenter.onShareDialog()
      return true
    }
    if (itemId == R.id.settings_item) {
      presenter.onSettingsClick()
      return true
    }
    return false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mViews = DataBindingUtil
      .inflate<ActivityChatBinding>(layoutInflater, R.layout.activity_chat, getContentContainer(), true)

    Timber.i("Binding: " + mViews)

    onCreatePresenter(savedInstanceState)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    init()
  }


  override fun showToast(message: String?) {
    toast(message.toString())
  }

  override fun openSettings() {
    KNavigationHelper.openKSettingsActivity(this, false)
  }

  override fun getSelectedItemId(): Int {
    return R.id.action_chat
  }

  override fun getPresenterClass(): Class<KChatPresenterImpl> {
    return KChatPresenterImpl::class.java
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_chat)
  }

  /* Private Methods ******************************************************************************/

  fun init() {
    with(mViews.chatSwipeRefresh) {
      setOnRefreshListener { isRefreshing = false }
    }
  }
}