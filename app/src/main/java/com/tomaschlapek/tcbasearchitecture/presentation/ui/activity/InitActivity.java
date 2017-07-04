package com.tomaschlapek.tcbasearchitecture.presentation.ui.activity;

import android.os.Bundle;


import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view.IBaseView;
import com.tomaschlapek.tcbasearchitecture.presentation.ui.activity.base.BaseActivity;


public class InitActivity extends BaseActivity implements IBaseView {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public Class getPresenterClass() {
    return null;
  }

}
