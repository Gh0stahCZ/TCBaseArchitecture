package com.tomaschlapek.tcbasearchitecture.presentation.ui.adapter.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlin.properties.Delegates


/**
 * General base adapter.
 */
abstract class KBaseAdapter<T>(protected var mContext: Context, itemList: MutableList<T>, private val emptyCheck: (Boolean) -> Unit = {}) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  /**
   * Delegate notifies base adapter about changes after every data change.
   */
  protected var mItemList: MutableList<T> by Delegates.observable(itemList) { _, _, _ ->
    notifyDataSetChanged()
  }

  private var attachedRecycler: RecyclerView? = null

  private val baseObserver = object : RecyclerView.AdapterDataObserver() {
    override fun onChanged() {
      checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
      checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
      checkIfEmpty()
    }
  }

  /* Abstract Methods *****************************************************************************/

  abstract fun createCustomViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
  abstract fun onBindData(holder: RecyclerView.ViewHolder, value: T)
  abstract fun onItemViewType(pos: Int): Int
  abstract fun countItems(): Int

  /* Public Methods *******************************************************************************/

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
    super.onAttachedToRecyclerView(recyclerView)
    attachedRecycler = recyclerView
    registerAdapterDataObserver(baseObserver)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
    super.onDetachedFromRecyclerView(recyclerView)
    attachedRecycler = null
    unregisterAdapterDataObserver(baseObserver)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val holder = createCustomViewHolder(parent, viewType)
    return holder
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    onBindData(holder, mItemList[position])
  }

  override fun getItemViewType(position: Int): Int {
    return onItemViewType(position)
  }

  override fun getItemCount(): Int {
    return countItems()
  }

  open fun addItems(savedCardItems: MutableList<T>) {
    mItemList = savedCardItems
  }

  fun removeItem(itemToRemove: T) {
    val itemToRemovePos = mItemList.indexOf(itemToRemove)
    mItemList.remove(itemToRemove)
    this.notifyItemRemoved(itemToRemovePos)
  }

  fun getItem(position: Int): T {
    return mItemList[position]
  }

  fun scrollToTop() {
    attachedRecycler?.scrollToPosition(0)
  }

  fun smoothScrollToTop() {
    attachedRecycler?.smoothScrollToPosition(0)
  }

  fun scrollToBottom() {
    attachedRecycler?.scrollToPosition(mItemList.lastIndex)
  }

  fun smoothScrollToBottom() {
    attachedRecycler?.smoothScrollToPosition(mItemList.lastIndex)
  }

  fun reportError(): Nothing {
    throw RuntimeException()
  }

  /* Private Methods ******************************************************************************/

  private fun checkIfEmpty() {
    emptyCheck(itemCount == 0)
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/

}