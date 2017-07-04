package com.tomaschlapek.tcbasearchitecture.domain.interactors.base;

/**
 * This is the main interfaces of an interactor. Each interactor serves a specific use case.
 */
public interface Interactor {

  /**
   * This is the main method that starts an interactor. It will make sure that the interactor
   * operation is done on a
   * background thread.
   */
  void execute();
}
