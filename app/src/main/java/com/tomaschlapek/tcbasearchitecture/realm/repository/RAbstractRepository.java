package com.tomaschlapek.tcbasearchitecture.realm.repository;

import com.tomaschlapek.tcbasearchitecture.helper.PreferenceHelper;

import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.Realm.Transaction.OnError;
import io.realm.Realm.Transaction.OnSuccess;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import java.util.List;

/**
 * Realm CRUD interface.
 *
 * @param <T> Definer Realm object.
 */
public abstract class RAbstractRepository<T extends RealmModel> extends RBaseRepository {

  public RAbstractRepository(PreferenceHelper preferenceHelper) {
    super(preferenceHelper);
  }

  // No instance defined

  public void addOrUpdateAsync(T item, OnSuccess onSuccess, OnError onError) {
    try (Realm realm = Realm.getDefaultInstance()) {
      addOrUpdateAsync(realm, item, onSuccess, onError);
    }
  }

  public void addOrUpdateAsync(Iterable<T> item, OnSuccess onSuccess, OnError onError) {
    try (Realm realm = Realm.getDefaultInstance()) {
      addOrUpdateAsync(realm, item, onSuccess, onError);
    }
  }

  public void addOrUpdate(Iterable<T> item) {
    try (Realm realm = Realm.getDefaultInstance()) {
      addOrUpdate(realm, item);
    }
  }

  public void addOrUpdate(T item) {
    try (Realm realm = Realm.getDefaultInstance()) {
      addOrUpdate(realm, item);
    }
  }

  // Instance defined

  void addOrUpdateAsync(Realm realm, T item, OnSuccess onSuccess, OnError onError) {
    realm.executeTransactionAsync(new Transaction() {
      @Override
      public void execute(Realm realmInstance) {
        create(realmInstance, item);
      }
    }, onSuccess, onError);
  }

  void addOrUpdateAsync(Realm realm, Iterable<T> item, OnSuccess onSuccess, OnError onError) {
    realm.executeTransactionAsync(new Transaction() {
      @Override
      public void execute(Realm realmInstance) {
        create(realmInstance, item);
      }
    }, onSuccess, onError);
  }

  void addOrUpdate(Realm realm, Iterable<T> item) {
    realm.executeTransaction(realmInstance -> create(realmInstance, item));
  }

  void addOrUpdate(Realm realm, T item) {
    realm.executeTransaction(realmInstance -> create(realmInstance, item));
  }

  // Simple create

  public <T extends RealmModel> T create(Realm realmInstance, T item) {
    return realmInstance.copyToRealmOrUpdate(item);
  }

  public <T extends RealmModel> List<T> create(Realm realmInstance, Iterable<T> item) {
    return realmInstance.copyToRealmOrUpdate(item);
  }

  // Simple delete

  public boolean remove(RealmQuery query) {
    return query.findAll().deleteAllFromRealm();
  }

  // Simple read

  public RealmResults<T> get(RealmQuery query) {
    return query.findAll();
  }

  public T getFirst(RealmQuery query) {
    return (T) query.findFirst();
  }

  // Transform

  public RealmList<T> mapToRealm(List<T> list) {
    RealmList<T> realmList = new RealmList<>();
    realmList.addAll(list);
    return realmList;
  }
}
