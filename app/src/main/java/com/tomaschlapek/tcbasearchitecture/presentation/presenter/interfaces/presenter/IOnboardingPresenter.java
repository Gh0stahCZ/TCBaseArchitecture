package com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter;

import com.tomaschlapek.fancyonboarding.OnBoardingPage;

import java.util.List;

/**
 * Created by tomaschlapek on 17/5/17.
 */

public interface IOnboardingPresenter {

  List<OnBoardingPage> createPageList();
}
