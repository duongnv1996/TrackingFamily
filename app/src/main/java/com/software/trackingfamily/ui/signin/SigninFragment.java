package com.software.trackingfamily.ui.signin;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.software.trackingfamily.ui.map.MapsActivity;
import com.software.trackingfamily.R;
import com.software.trackingfamily.application.AppController;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.models.User;
import com.software.trackingfamily.ui.base.BaseFragment;
import com.software.trackingfamily.ui.signup.SignUpFragment;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class SigninFragment extends BaseFragment implements OnBackPressedListener, SignInContract.View {

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSignIn)
    ImageView btnSignIn;
    @BindView(R.id.btnGotoSignUp)
    TextView btnGotoSignUp;
    @BindView(R.id.avi)
    AVLoadingIndicatorView loading;
    SignInContract.Presenter mPresenter;

    protected int initLayout() {
        return R.layout.fragment_signin;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initVariables() {
        if (AppController.getInstance().getUser() != null) {
            goToMap();
        }
        mPresenter = new SignInPresenter(this);
    }

    private void goToMap() {
        startActivity(new Intent(getActivity(), MapsActivity.class));
        getActivity().finish();
    }


    @OnClick({R.id.btnSignIn, R.id.btnGotoSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                mPresenter.login(edtName.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.btnGotoSignUp:
                replaceToOtherFragment(new SignUpFragment());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //  finishFragment();
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
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);

    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    public void onLoginSuccess(User user) {
        goToMap();
    }
}
