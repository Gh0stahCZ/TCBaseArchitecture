package com.tomaschlapek.tcbasearchitecture.util

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.KBaseActivity
import org.jetbrains.anko.layoutInflater
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Provides lazy initialization of data binding class.
 */
class ActivityBinder<out T : ViewDataBinding>(private val layoutRes: Int) : ReadOnlyProperty<KBaseActivity<*, *>, T> {

  private var binding: T? = null

  override operator fun getValue(thisRef: KBaseActivity<*, *>, property: KProperty<*>): T =
    binding ?: createBinding(thisRef).also { binding = it }

  private fun createBinding(activity: KBaseActivity<*, *>): T =
    DataBindingUtil.inflate(activity.layoutInflater, layoutRes,activity.getContentContainer(), true)
}