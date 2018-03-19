package com.software.trackingfamily.ui.base;

import android.content.Context;

/**
 * Created by hoaph on 10/4/2017.
 */

public interface BaseView {
    Context getContext();

    void showProgress();

    void hiddenProgress();

    void onErrorApi(String message);

    void onError(String message);

    void onErrorAuthorization();


}
