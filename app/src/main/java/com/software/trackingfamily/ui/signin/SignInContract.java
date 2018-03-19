package com.software.trackingfamily.ui.signin;

import com.software.trackingfamily.models.User;
import com.software.trackingfamily.ui.base.BasePresenter;
import com.software.trackingfamily.ui.base.BaseView;

/**
 * Created by DuongKK on 3/18/2018.
 */

public interface SignInContract {
    interface View extends BaseView{
        void onLoginSuccess(User user);
    }

    interface  Presenter extends BasePresenter{
        void login(String phone, String pass);
    }
}
