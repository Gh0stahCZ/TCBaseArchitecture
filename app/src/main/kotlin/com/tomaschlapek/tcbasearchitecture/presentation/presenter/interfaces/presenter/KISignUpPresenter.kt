package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter

/**
 * Created by tomaschlapek on 15/9/17.
 */
interface KISignUpPresenter {

  fun isValidEmail(email: String): Boolean

  fun isValidPassword(pass: String): Boolean

  fun onSignUpButtonClick(email: String, pass: String)

  fun onForgetPassClick()

}