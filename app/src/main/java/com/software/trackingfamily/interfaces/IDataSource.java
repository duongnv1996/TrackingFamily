package com.software.trackingfamily.interfaces;

/**
 * Created by DuongKK on 3/18/2018.
 */

public interface IDataSource {
    interface LoadDataCallback<T> {

        void onDataLoaded(T responseObject);

        void onDataNotAvailable();
    }
}
