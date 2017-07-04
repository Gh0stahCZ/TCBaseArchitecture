package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter;

/**
 * Created by tomaschlapek on 17/5/17.
 */

public interface ISignInPresenter {

  boolean isValidEmail(String email);

  boolean isValidPassword(String pass);

  void onSignInButtonClick(String email, String pass);
}
