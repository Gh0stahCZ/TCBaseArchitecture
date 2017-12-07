package com.tomaschlapek.tcbasearchitecture.presentation.ui.adapter.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

abstract class KBaseViewHolder<T : ViewDataBinding>(protected var mBinding: T) : RecyclerView.ViewHolder(mBinding.root)