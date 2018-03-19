package com.software.trackingfamily.ui.signin;

import android.os.Handler;

import com.software.trackingfamily.application.AppController;
import com.software.trackingfamily.models.User;
import com.software.trackingfamily.net.retrofit.ApiResponse;
import com.software.trackingfamily.ui.base.Presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class SignInPresenter extends Presenter implements SignInContract.Presenter {
    SignInContract.View mView;

    public SignInPresenter(SignInContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }

    @Override
    public void login(String phone, String pass) {
        mView.showProgress();
        getmService().login("login_user", phone, pass).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, final Response<ApiResponse<User>> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.hiddenProgress();
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getCode() == 1) {
                                AppController.getInstance().setUser(response.body().getData());
                                mView.onLoginSuccess(response.body().getData());
                            } else {
                                mView.onError(response.body().getMessage());
                            }
                        } else {
                            mView.onError(response.message());
                        }
                    }
                },3000);

            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                mView.hiddenProgress();
                mView.onErrorApi(t.getMessage());
            }
        });
    }


}
