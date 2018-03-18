package com.software.trackingfamily.ui.signup;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.trackingfamily.R;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class SignUpFragment extends BaseFragment implements OnBackPressedListener{

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

    @Override
    protected int initLayout() {
        return R.layout.fragment_signup;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initVariables() {

    }


    @OnClick({R.id.btnSignUp, R.id.btnGotoSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
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
}
