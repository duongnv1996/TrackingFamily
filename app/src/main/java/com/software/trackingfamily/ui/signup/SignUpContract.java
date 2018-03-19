package com.software.trackingfamily.ui.signup;

import com.software.trackingfamily.ui.base.BasePresenter;
import com.software.trackingfamily.ui.base.BaseView;

/**
 * Created by DuongKK on 3/19/2018.
 */

public interface SignUpContract {
    interface View extends BaseView {
        void onSuccessSignUp();
    }

    interface Presenter extends BasePresenter {
        void signUp(String user, String email, String phone, String family, String pass);
    }
}
