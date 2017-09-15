package com.tomaschlapek.tcbasearchitecture.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;

import com.tomaschlapek.tcbasearchitecture.R;

import java.io.Serializable;

/**
 * Sample custom behavior sheet fragment.
 */
public class CustomBottomSheetFragment extends BottomSheetDialogFragment {

  /* Private Constants ****************************************************************************/
  /* Public Constants *****************************************************************************/

  /**
   * Extra identifiers.
   */
  public static class Argument {
    public static final String CUSTOM_OBJECT = "arg_custom_object";
  }


  /* Private Attributes ***************************************************************************/

  private Serializable mDeliveredObject;

  private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
    new BottomSheetBehavior.BottomSheetCallback() {

      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
          dismiss();
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    };

  /* Constructor **********************************************************************************/

  public CustomBottomSheetFragment() {
    super();
  }

  /* Public Static Methods ************************************************************************/

  public static CustomBottomSheetFragment newInstance(Serializable deliveredObject) {
    CustomBottomSheetFragment shopItemDetailFragment = new CustomBottomSheetFragment();
    Bundle args = new Bundle();
    args.putSerializable(Argument.CUSTOM_OBJECT, deliveredObject);
    shopItemDetailFragment.setArguments(args);
    return shopItemDetailFragment;
  }

  /* Public Methods *******************************************************************************/

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle state = (savedInstanceState != null) ? savedInstanceState : getArguments();
    loadArguments(state);
  }

  @Override
  @SuppressLint("RestrictedApi")
  public void setupDialog(Dialog dialog, int style) {
    super.setupDialog(dialog, style);
    View contentView = View.inflate(getContext(), R.layout.fragment_custom_bottom_sheet, null);

    dialog.setContentView(contentView);

    CoordinatorLayout.LayoutParams params =
      (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
    CoordinatorLayout.Behavior behavior = params.getBehavior();

    if (behavior != null && behavior instanceof BottomSheetBehavior) {
      ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(Argument.CUSTOM_OBJECT, mDeliveredObject);
  }

  /* Private Methods ******************************************************************************/

  private void loadArguments(Bundle state) {
    if (state != null) {
      if (state.containsKey(Argument.CUSTOM_OBJECT)) {
        mDeliveredObject = state.getSerializable(Argument.CUSTOM_OBJECT);
      }
    }
  }
  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/
}
