package com.software.trackingfamily.ui.map;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.software.trackingfamily.application.AppController;
import com.software.trackingfamily.models.User;
import com.software.trackingfamily.net.retrofit.ApiResponse;
import com.software.trackingfamily.ui.base.Presenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class MapPresenter extends Presenter implements MapContract.Presenter {
    private MapContract.View mView;

    public MapPresenter(MapContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }

    @Override
    public void getListMember() {
        if (AppController.getInstance().getUser().getFamily_id() == null) return;
        getmService().getMemberOfFamily("get_user_family", AppController.getInstance().getUser().getFamily_id()).enqueue(new Callback<ApiResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<User>>> call, Response<ApiResponse<List<User>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == 1) {
                        mView.onSuccessGetListMember(response.body().getData());
                    } else {
                        mView.onError(response.body().getMessage());
                    }
                } else {
                    mView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<User>>> call, Throwable t) {
                mView.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public void updateLatlng(final LatLng latLng) {
        getmService().updateLatlng("edit_user", AppController.getInstance().getUser().getUser_id(), latLng.latitude, latLng.longitude).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == 1) {
                        LogUtils.e("Update latlng ---->>" + latLng.toString());
                    } else {
                        mView.onError(response.body().getMessage());
                    }
                } else {
                    mView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mView.onErrorApi(t.getMessage());

            }
        });
    }
}
