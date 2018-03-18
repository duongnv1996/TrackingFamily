package com.software.trackingfamily.ui.base;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.software.trackingfamily.interfaces.OnBackPressedListener;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 16/12/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final int RC_GALLERY = 101;
    public static final int RC_CAMERA = 102;
    private static final int RC_PIC_FILE = 103;
    private File mFileTempImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());

        initViews();
        initVariables();
    }

    protected boolean initToolbar() {
        return true;
    }

    protected abstract int initLayout();

    protected abstract void initVariables();

    protected abstract void initViews();


    public void setVisibleToolbar(boolean visible) {
        if (visible) {
            getActionBar().show();
        } else {
            getActionBar().hide();
        }
    }

    public void replaceFragmentI(Fragment fragment, int layout) {
        getSupportFragmentManager().beginTransaction().replace(layout, fragment, fragment.getClass().getName()).commit();
    }

    public Fragment finFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public Fragment finFragmentById(int idLayout) {
        return getSupportFragmentManager().findFragmentById(idLayout);
    }

    public void startFastActivity(Class<?> classA) {
        startActivity(new Intent(this, classA));
    }

    public void replaceFragment(Fragment fragment, int idLayout) {
        if (fragment != null) {
//            lastFragment = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            if (finFragmentByTag(fragment.getClass().getSimpleName()) != null) {
                fragmentTransaction.replace(idLayout, fragment);

            } else {
                fragmentTransaction.replace(idLayout, fragment, fragment.getClass().getSimpleName());
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    public void replaceAndRemoveFragment(Fragment fragment, int idLayout) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            if (finFragmentByTag(fragment.getClass().getSimpleName()) != null) {
                removeFragment(finFragmentByTag(fragment.getClass().getSimpleName()));
                fragmentTransaction.replace(idLayout, fragment);
            } else {
                fragmentTransaction.replace(idLayout, fragment, fragment.getClass().getSimpleName());
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
        fragmentManager.popBackStack();
    }

    public void replaceFragmentNoBackStack(Fragment fragment, int idLayout) {
        if (fragment != null) {
//            lastFragment = fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            if (finFragmentByTag(fragment.getClass().getSimpleName()) != null) {
                fragmentTransaction.replace(idLayout, fragment);
            } else {
                fragmentTransaction.replace(idLayout, fragment, fragment.getClass().getSimpleName());
            }
            fragmentTransaction.commit();
        }
    }


    public void takePhotoFromLib() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"), RC_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
           if(fragment!=null)
            fragment.onActivityResult(requestCode, resultCode, data); // TODO: 12/8/17 bug crash nullpointer
//            for (Fragment fragmentChild : fragment.getChildFragmentManager().getFragments()) {
//                fragmentChild.onActivityResult(requestCode, resultCode, data);
//            }
        }
    }

    public void showDialogExpired() {

    }

    @Override
    public void onBackPressed() {
        handlerBackPress();
    }

    @Override
    public boolean onSupportNavigateUp() {
        handlerBackPress();
        return true;
    }

    private void handlerBackPress() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof OnBackPressedListener) {
                    ((OnBackPressedListener) fragment).onBackPressed();

                }
            }
        }
    }
}
