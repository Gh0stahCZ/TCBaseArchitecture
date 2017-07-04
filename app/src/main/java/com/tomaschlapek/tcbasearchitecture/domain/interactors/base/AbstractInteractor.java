package com.tomaschlapek.tcbasearchitecture.domain.interactors.base;

import com.tomaschlapek.tcbasearchitecture.domain.executor.MainThread;
import com.tomaschlapek.tcbasearchitecture.domain.executor.impl.ThreadExecutor;

import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dmilicic on 8/4/15.
 * <p/>
 * This abstract class implements some common methods for all interactors. Cancelling an
 * interactor,
 * check if its running
 * and finishing an interactor has mostly the same code throughout so that is why this class was
 * created. Field methods
 * are declared volatile as we might use these methods from different threads (mainly from UI).
 * <p/>
 * For example, when an activity is getting destroyed then we should probably cancel an interactor
 * but the request will come from the UI thread unless the request was specifically assigned to a
 * background thread.
 */
public abstract class AbstractInteractor implements Interactor {

  protected ThreadExecutor mThreadExecutor;
  protected MainThread mMainThread;
  protected Transformer<Object, Object> mSchedulersTransformer;

  protected volatile boolean mIsCanceled;
  protected volatile boolean mIsRunning;

  public AbstractInteractor(ThreadExecutor threadExecutor, MainThread mainThread) {
    mThreadExecutor = threadExecutor;
    mMainThread = mainThread;
    mSchedulersTransformer = tObservable -> tObservable
      .subscribeOn(Schedulers.from(mThreadExecutor.getThreadPoolExecutor()))
      .observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED
   * DIRECTLY but, instead, a
   * developer should call the execute() method of an interactor to make sure the operation is done
   * on a background thread.
   * <p/>
   * This method should only be called directly while doing unit/integration tests. That is the
   * only
   * reason it is declared
   * public as to help with easier testing.
   */
  public abstract void run();

  public void cancel() {
    mIsCanceled = true;
    mIsRunning = false;
  }

  public boolean isRunning() {
    return mIsRunning;
  }

  public void onFinished() {
    mIsRunning = false;
    mIsCanceled = false;
  }

  public void execute() {

    // mark this interactor as running
    this.mIsRunning = true;

    // start running this interactor in a background thread
    mThreadExecutor.execute(this);
  }

  /**
   * Apply schedulers for setting observable on working thread from Thread pool and setting
   * subscriber on MainUiThread
   *
   * @param <T>
   *
   * @return Transformer with set observable.
   */
  public <T> Transformer<T, T> applySchedulers() {
    return (Transformer<T, T>) mSchedulersTransformer;
  }
}
