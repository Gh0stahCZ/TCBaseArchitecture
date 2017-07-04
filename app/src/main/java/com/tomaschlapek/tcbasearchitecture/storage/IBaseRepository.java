package com.tomaschlapek.tcbasearchitecture.storage;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by root on 16.04.2017.
 */

public interface IBaseRepository {

  Observable processResponseFromServer(Response response);
}
