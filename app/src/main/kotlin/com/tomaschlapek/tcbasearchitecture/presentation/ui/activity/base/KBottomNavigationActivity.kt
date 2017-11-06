package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.ViewGroup
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView
import org.jetbrains.anko.find

/**
 * Base class with drawer.
 */
abstract class KBottomNavigationActivity<TView : KIBaseView, TViewModel : KActivityPresenter<TView>> : KDrawerActivity<TView, TViewModel>() {

  /* Public Types *********************************************************************************/
  /* Protected Attributes *************************************************************************/

  lateinit var bottomNavigationContainer: ViewGroup
  lateinit var bottomNavigationView: BottomNavigationView

  /* Private Attributes *************************************************************************/
  /* Protected Methods ****************************************************************************/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /**
     * Adding our layout to parent class frame layout.
     */
    layoutInflater.inflate(R.layout.activity_bottom_navigation, drawerContainer)

    bottomNavigationContainer = find<ViewGroup>(R.id.bottom_navigation_container)
    bottomNavigationView = find<BottomNavigationView>(R.id.bottom_navigation_view)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    bottomNavigationView.selectedItemId = getSelectedItemId()

    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_favorites -> {
          //          KNavigationHelper.openKBuySearchActivity(this, false)
          item.isEnabled = !item.isEnabled
        }
        R.id.action_schedules -> {
          //          KNavigationHelper.openKSellActivity(this, false)
          item.isEnabled = !item.isEnabled
        }
        R.id.action_music -> {
          //          KNavigationHelper.openKChatActivity(this, false)
          item.isEnabled = !item.isEnabled
        }
      }

      // TODO : Uncomment when screens will be implemented
      if (getSelectedItemId() != item.itemId) {
        finish()
      }

      true
    }
  }

  override fun getContentContainer(): ViewGroup {
    return bottomNavigationContainer
  }

  abstract fun getSelectedItemId(): Int
  abstract override fun getPresenterClass(): Class<TViewModel>

}
