package com.tomaschlapek.tcbasearchitecture.presentation.ui.adapter.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tomaschlapek on 27/7/17.
 */

public abstract class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

  protected T mBinding;

  public BaseViewHolder(T binding) {
    super(binding.getRoot());
    mBinding = binding;
  }
}
