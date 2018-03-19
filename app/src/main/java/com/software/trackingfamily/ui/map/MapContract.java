package com.software.trackingfamily.ui.map;

import com.google.android.gms.maps.model.LatLng;
import com.software.trackingfamily.models.User;
import com.software.trackingfamily.ui.base.BasePresenter;
import com.software.trackingfamily.ui.base.BaseView;

import java.util.List;

/**
 * Created by DuongKK on 3/18/2018.
 */

public interface MapContract {
    interface View extends BaseView {
        void onSuccessGetListMember(List<User> listUser);
    }

    interface Presenter extends BasePresenter {
        void getListMember();
        void updateLatlng(LatLng latLng);
    }
}
