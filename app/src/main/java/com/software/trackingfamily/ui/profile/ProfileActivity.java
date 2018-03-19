package com.software.trackingfamily.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.software.trackingfamily.R;
import com.software.trackingfamily.application.AppController;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.ui.AuthActivity;
import com.software.trackingfamily.ui.base.BaseActivity;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class ProfileActivity extends BaseActivity implements OnBackPressedListener, ProfileContract.View {

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtYourFamily)
    EditText edtYourFamily;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.avi)
    AVLoadingIndicatorView loading;
    private ProfileContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.activity_profile;
    }


    @Override
    protected void initVariables() {
        presenter = new ProfilePresenter(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        edtEmail.setText(AppController.getInstance().getUser().getEmail());
        edtName.setText(AppController.getInstance().getUser().getUsername());
        edtPhone.setText(AppController.getInstance().getUser().getPhone());
        edtYourFamily.setText(AppController.getInstance().getUser().getFamily_id());
    }


    @OnClick({R.id.save, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save:
                presenter.signUp(edtName.getText().toString(), edtEmail.getText().toString(), edtPhone.getText().toString(), edtYourFamily.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.logout:
                logOut();
                break;
        }
    }

    private void logOut() {
        AppController.getInstance().setUser(null);
        AppController.getInstance().getmSetting().clear();
        startActivity(new Intent(this, AuthActivity.class));
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        //getFragmentManager().popBackStack();
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenProgress() {
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onErrorApi(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void onSuccessSignUp() {
        Toast.makeText(getContext(), "Update successful! Go back  now!", Toast.LENGTH_SHORT).show();
    }
}
