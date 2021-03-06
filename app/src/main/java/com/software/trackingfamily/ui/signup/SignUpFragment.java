package com.software.trackingfamily.ui.signup;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.trackingfamily.R;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.ui.base.BaseFragment;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class SignUpFragment extends BaseFragment implements OnBackPressedListener, SignUpContract.View {

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
    @BindView(R.id.btnSignUp)
    ImageView btnSignUp;
    @BindView(R.id.btnGotoSignIn)
    TextView btnGotoSignIn;
    @BindView(R.id.avi)
    AVLoadingIndicatorView loading;
    private SignUpContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_signup;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initVariables() {
        presenter = new SignUpPresenter(this);
    }


    @OnClick({R.id.btnSignUp, R.id.btnGotoSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                presenter.signUp(edtName.getText().toString(), edtEmail.getText().toString(), edtPhone.getText().toString(), edtYourFamily.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.btnGotoSignIn:
                finishFragment();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //getFragmentManager().popBackStack();
        finishFragment();
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
        Toast.makeText(getContext(), "Sign Up successful! Sign In now!", Toast.LENGTH_SHORT).show();
        finishFragment();
    }
}
