//package com.tomaschlapek.tcbasearchitecture;
//
//import android.text.TextUtils;
//
//import com.tomaschlapek.tcbasearchitecture.presentation.presenter.SignInPresenterImpl;
//import com.tomaschlapek.tcbasearchitecture.presentation.presenter.interfaces.presenter.ISignInPresenter;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//ė
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(TextUtils.class)
//public class SignInPresenterUnitTest {
//
//  ISignInPresenter mSignInPresenter;
//
//  @Before
//  public void setUp() {
//    PowerMockito.mockStatic(TextUtils.class);
//    PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
//      @Override
//      public Boolean answer(InvocationOnMock invocation) throws Throwable {
//        CharSequence a = (CharSequence) invocation.getArguments()[0];
//        return !(a != null && a.length() > 0);
//      }
//    });
//
//    mSignInPresenter = new SignInPresenterImpl();
//  }
//
//
//
//  @Test
//  public void testPassword() throws Exception {
//
//    assertTrue(mSignInPresenter != null);
//    assertFalse(mSignInPresenter.isValidPassword("shrt"));
//    assertTrue(mSignInPresenter.isValidPassword("longpassword"));
//  }
//}