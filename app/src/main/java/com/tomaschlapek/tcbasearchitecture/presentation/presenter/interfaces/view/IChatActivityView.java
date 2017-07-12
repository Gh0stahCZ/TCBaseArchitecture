package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.view;

/**
 * Created by tomaschlapek on 17/5/17.
 */

public interface IChatActivityView extends IBaseView {

  void showToast(String text);

  void onMessageDelivered();

  void onMessageFailed();

  void onLikeDelivered();
}
