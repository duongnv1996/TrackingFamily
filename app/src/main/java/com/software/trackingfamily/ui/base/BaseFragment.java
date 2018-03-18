package com.software.trackingfamily.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 16/12/2016.
 */

public abstract class BaseFragment extends Fragment {
    private View view;

    protected void onReloadDataInPage() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(initLayout(), container, false);
            initViews(view);
            initVariables();

        }
        return view;
    }

    protected abstract int initLayout();

    protected abstract void initViews(View view);

    protected abstract void initVariables();

    public void onAttackEventRightToolbar() {

    }


    public void replaceNewOtherFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //fragmentManager.beginTransaction();
        //.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)

        if (getFragmentByTag(fragment.getClass().getSimpleName()) != null) {
            removeFragment(getFragmentByTag(fragment.getClass().getSimpleName()));
        }
        fragmentManager.beginTransaction().replace(getId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    public void replaceToOtherFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentManager.beginTransaction().replace(getId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    public void replaceOtherFragmentWithTag(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(getId(), fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public void replaceChildFragment(Fragment fragment, int layoutID) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(layoutID, fragment, fragment.getClass().getName()).addToBackStack(null).commit();
    }

    public Fragment getFragmentByTag(String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(tag);
    }

    public Fragment getFragmentById(int idLayout) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        return fragmentManager.findFragmentById(idLayout);
    }

    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
        fragmentManager.popBackStack();
    }

    protected void showDialogExpired() {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (baseActivity != null) baseActivity.showDialogExpired();
    }

    protected void finishFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(this);
        trans.commit();
        manager.popBackStack();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
