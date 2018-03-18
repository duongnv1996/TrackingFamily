package com.software.trackingfamily.application;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.software.trackingfamily.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class AppController extends Application {
    private static AppController mInstance;
    private SPUtils mSetting;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Utils.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        mSetting = new SPUtils("setting");

    }


    public SPUtils getmSetting() {
        return mSetting;
    }

    public void setmSetting(SPUtils mSetting) {
        this.mSetting = mSetting;
    }

    public static AppController getInstance() {
        return mInstance;
    }
}
