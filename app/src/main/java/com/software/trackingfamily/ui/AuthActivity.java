package com.software.trackingfamily.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.software.trackingfamily.R;
import com.software.trackingfamily.interfaces.OnBackPressedListener;
import com.software.trackingfamily.ui.base.BaseActivity;
import com.software.trackingfamily.ui.signin.SigninFragment;

import butterknife.ButterKnife;

public class AuthActivity extends BaseActivity implements OnBackPressedListener{



    @Override
    protected int initLayout() {
        return R.layout.activity_auth;
    }

    @Override
    protected void initVariables() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
            }
            return;
        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        replaceFragment(new SigninFragment(),R.id.layoutRoot);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount()==1) finish();
    }
}
