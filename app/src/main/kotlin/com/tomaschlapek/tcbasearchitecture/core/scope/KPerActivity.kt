package com.tomaschlapek.tcbasearchitecture.core.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Scope for dependency injection within activity.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class KPerActivity