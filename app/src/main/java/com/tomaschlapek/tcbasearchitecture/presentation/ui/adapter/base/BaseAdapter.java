package com.tomaschlapek.tcbasearchitecture.presentation.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by tomaschlapek on 27/7/17.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  protected Context mContext;
  private List<T> mItemList;

  /* Constructor **********************************************************************************/

  public BaseAdapter(Context context, List<T> itemList) {
    mContext = context;
    mItemList = itemList;
  }

  /* Public Abstract  Methods *********************************************************************/

  public abstract RecyclerView.ViewHolder createCustomViewHolder(ViewGroup parent, int viewType);

  public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

  public abstract int onItemViewType(int pos);

  /* Public Methods *******************************************************************************/

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder holder = createCustomViewHolder(parent, viewType);
    return holder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    onBindData(holder, mItemList.get(position));
  }

  @Override
  public int getItemViewType(int position) {
    return onItemViewType(position);
  }

  @Override
  public int getItemCount() {
    return mItemList.size();
  }

  public void addItems(List<T> savedCardItems) {
    mItemList = savedCardItems;
    this.notifyDataSetChanged();
  }

  public T getItem(int position) {
    return mItemList.get(position);
  }

  public void removeItem(int position) {
    mItemList.remove(position);
    notifyItemRemoved(position);
  }



  /* Private Methods ******************************************************************************/
  /* Getters / Setters ****************************************************************************/



  /* Inner classes ********************************************************************************/
}
