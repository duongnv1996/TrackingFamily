package com.software.trackingfamily.ui.signin;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.trackingfamily.MapsActivity;
import com.software.trackingfamily.R;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.ui.base.BaseFragment;
import com.software.trackingfamily.ui.signup.SignUpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class SigninFragment extends BaseFragment implements OnBackPressedListener{

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSignIn)
    ImageView btnSignIn;
    @BindView(R.id.btnGotoSignUp)
    TextView btnGotoSignUp;

    @Override
    protected int initLayout() {
        return R.layout.fragment_signin;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void initVariables() {

    }



    @OnClick({R.id.btnSignIn, R.id.btnGotoSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                startActivity(new Intent(getActivity(), MapsActivity.class));
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
}
