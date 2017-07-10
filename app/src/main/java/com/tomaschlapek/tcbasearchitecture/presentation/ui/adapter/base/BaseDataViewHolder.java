package com.tomaschlapek.tcbasearchitecture.presentation.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base ViewHolder.
 * Description:
 * https://android.jlelse.eu/what-type-of-android-developer-are-you-4d92a5eb7c9e
 */
public abstract class BaseDataViewHolder<Data> extends RecyclerView.ViewHolder {

  public BaseDataViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void setDataInViews(Data data);
}
