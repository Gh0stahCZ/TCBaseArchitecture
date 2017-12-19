package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter

/**
 * Created by tomaschlapek on 15/9/17.
 */
interface KISignInPresenter {

  fun isValidEmail(email: String): Boolean

  fun isValidPassword(pass: String): Boolean

  fun onSignInButtonClick(email: String, pass: String)
  fun onSkipClick()
}