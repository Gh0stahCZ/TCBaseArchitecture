package com.tomaschlapek.tcbasearchitecture.domain.model

import com.tomaschlapek.tcbasearchitecture.App
import com.tomaschlapek.tcbasearchitecture.R

const val GENERAL_ERROR_DEFAULT_CODE = -1

data class GeneralErrorResponse(val error: GeneralError)

data class GeneralError(val field: String?, val message: String, val value: String?, val code: Int) {
  constructor() : this(null, App.getResString(R.string.general_error_message), null, GENERAL_ERROR_DEFAULT_CODE)
}