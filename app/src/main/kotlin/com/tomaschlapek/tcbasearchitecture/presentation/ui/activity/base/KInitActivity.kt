package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base

import android.os.Bundle
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.KBasePresenterImpl
import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.KIBaseView

class KInitActivity : KBaseActivity<KIBaseView, KBasePresenterImpl>(), KIBaseView {

  override fun abortNotification(): Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun getPresenterClass(): Class<KBasePresenterImpl>? {
    return null
  }

}