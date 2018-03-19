package com.software.trackingfamily.ui.signup;

import com.software.trackingfamily.models.User;
import com.software.trackingfamily.net.retrofit.ApiResponse;
import com.software.trackingfamily.ui.base.Presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DuongKK on 3/19/2018.
 */

public class SignUpPresenter extends Presenter implements SignUpContract.Presenter {
    SignUpContract.View mView;

    public SignUpPresenter(SignUpContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }


    @Override
    public void signUp(String user, String email, String phone, String family, String pass) {
        mView.showProgress();
        getmService().register("reg_user", phone, pass, user, email, family).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                mView.hiddenProgress();
                if (response.isSuccessful() && response.body() != null && response.body().getCode() == 1) {
                    mView.onSuccessSignUp();
                } else {
                    mView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                mView.hiddenProgress();
                mView.onErrorApi(t.getMessage());
            }
        });
    }
}
