package com.tomaschlapek.tcbasearchitecture.presentation.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R
import com.tomaschlapek.tcbasearchitecture.engine.UserEngine
import org.jetbrains.anko.find

/**
 * Example Bottom Sheet Fragment
 */
class ExampleBottomSheetFragment : BottomSheetDialogFragment() {

  private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

    override fun onStateChanged(bottomSheet: View, newState: Int) {
      if (newState == BottomSheetBehavior.STATE_HIDDEN) {
        dismiss()
      }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
  }

  private lateinit var userEngine: UserEngine


  /* Public Static Methods ************************************************************************/

  companion object {
    fun newInstance(): ExampleBottomSheetFragment {
      val sortFragment = ExampleBottomSheetFragment()
      return sortFragment
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val state = savedInstanceState ?: arguments
    loadArguments(state)

    userEngine = App.getAppComponent().provideUserEngine()
  }

  @SuppressLint("RestrictedApi")
  override fun setupDialog(dialog: Dialog, style: Int) {
    super.setupDialog(dialog, style)
    val contentView = View.inflate(context, R.layout.fragment_bottom_sheet_example, null)

    dialog.setContentView(contentView)

    val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
    val behavior = params.behavior

    if (behavior != null && behavior is BottomSheetBehavior<*>) {
      behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
    }

    init(contentView)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    //    outState.putSerializable(Argument.CUSTOM_OBJECT, mDeliveredObject)
  }

  /* Private Methods ******************************************************************************/

  private fun loadArguments(state: Bundle?) {
    if (state != null) {
      //      if (state.containsKey(Argument.CUSTOM_OBJECT)) {
      //        mDeliveredObject = state.getSerializable(Argument.CUSTOM_OBJECT)
      //      }
    }
  }

  private fun init(contentView: View) {
    var resetButton = contentView.find<Button>(R.id.filter_sort_reset_button)
    var closeButton = contentView.find<ImageButton>(R.id.filter_sort_close_button)
    var searchButton = contentView.find<Button>(R.id.filter_sort_search_button)

    var byDateButton = contentView.find<RadioButton>(R.id.filter_sort_by_date)
    var byPriceButton = contentView.find<RadioButton>(R.id.filter_sort_by_price)


    resetButton.setOnClickListener {
      // TODO On reset button click
      dismiss()
    }

    closeButton.setOnClickListener {
      // TODO On close button click
      dismiss()
    }

    searchButton.setOnClickListener {
      // TODO On confirm button click
      dismiss()
    }
  }
}