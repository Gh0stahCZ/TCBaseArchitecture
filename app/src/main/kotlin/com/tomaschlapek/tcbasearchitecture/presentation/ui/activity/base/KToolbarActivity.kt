package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.KSignInActivity
import com.tomaschlapek.tcbasearchitecture.util.str

/**
 * Base class with drawer.
 */
abstract class KToolbarActivity<TView : KIBaseView, TViewModel : KActivityPresenter<TView>> : KBaseActivity<TView, TViewModel>() {

  /* Protected Attributes *************************************************************************/

  lateinit protected var toolbarContainer: ViewGroup
  lateinit var loadingContainer: ViewGroup
  var toolbar: Toolbar? = null

  /* Public Methods *******************************************************************************/

  override fun onPrepareOptionsMenu(menu: Menu): Boolean {
    return super.onPrepareOptionsMenu(menu)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        NavUtils.navigateUpFromSameTask(this)

        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }


  /* Protected Methods ****************************************************************************/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    layoutInflater.inflate(R.layout.activity_toolbar, mBaseContainer)

    toolbarContainer = findViewById(R.id.toolbar_container)
    toolbar = findViewById(R.id.toolbar)
    loadingContainer = findViewById(R.id.progress_bar_container)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    setupToolbar()
  }

  override fun setLoadingProgressVisibility(visible: Boolean) {
    loadingContainer.visibility = if (visible) View.VISIBLE else View.GONE
  }

  override abstract fun getPresenterClass(): Class<TViewModel>

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
  }

  override fun getContentContainer(): ViewGroup {
    return toolbarContainer
  }

  open fun getToolbarTitle(): String {
    return str(R.string.app_name)
  }

  fun updateToolbar(titleResId: Int) {
    toolbar!!.title = str(titleResId)
  }

  fun updateToolbar(title: String) {
    toolbar!!.title = title
  }

  /* Private Methods ******************************************************************************/

  private fun setupToolbar() {
    if (toolbar != null) {

      // Set elevation of the toolbar to display shadow.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        toolbar!!.elevation = resources.getDimensionPixelSize(R.dimen.toolbar_elevation).toFloat()
      }

      toolbar!!.setTitleTextColor(Color.WHITE)

      setSupportActionBar(toolbar)

      val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
      upArrow.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
      supportActionBar?.setHomeAsUpIndicator(upArrow)
      supportActionBar?.title = getToolbarTitle()

      if (this is KBottomNavigationActivity<*, *> || this is KSignInActivity) {
        hideBackArrowButton()
      } else {
        showBackArrowButton()
      }
    }
  }

  private fun showBackArrowButton() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun hideBackArrowButton() {
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }
}
