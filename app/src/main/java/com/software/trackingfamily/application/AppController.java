package com.software.trackingfamily.application;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.software.trackingfamily.R;
import com.software.trackingfamily.models.User;
import com.software.trackingfamily.utils.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class AppController extends Application {
    private static AppController mInstance;
    private SPUtils mSetting;
    private User user;

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
        String userString = mSetting.getString(Constants.KEY_USER, "");
        if (!userString.isEmpty()) user = new Gson().fromJson(userString, User.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            mSetting.put(Constants.KEY_USER, new Gson().toJson(user));
        }
    }

    public SPUtils getmSetting() {
        return mSetting;
    }

    public static AppController getInstance() {
        return mInstance;
    }
}
