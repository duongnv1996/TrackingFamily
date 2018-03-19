package com.software.trackingfamily.ui.base;

import com.software.trackingfamily.net.retrofit.ApiUtil;
import com.software.trackingfamily.net.retrofit.ServiceRetrofit;

/**
 * Created by DuongKK on 3/18/2018.
 */

public abstract class Presenter {
    private ServiceRetrofit mService;


    public ServiceRetrofit getmService() {
        return mService;
    }

    public Presenter() {
      mService = ApiUtil.createService().create(ServiceRetrofit.class);
    }
}
