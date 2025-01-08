package com.solodroid.ads.sdkdemo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

import com.solodroid.ads.sdkdemo.data.Constant;
import com.solodroidx.ads.appopen.AppOpenAd;
import com.solodroidx.ads.appopen.AppOpenAdAppLovin;
import com.solodroidx.ads.appopen.AppOpenAdManager;
import com.solodroidx.ads.appopen.AppOpenAdMob;
import com.solodroidx.ads.appopen.AppOpenAdPangle;
import com.solodroidx.ads.appopen.AppOpenAdWortise;
import com.solodroidx.ads.appopen.AppOpenAdYandex;
import com.solodroidx.ads.listener.OnShowAdCompleteListener;

@SuppressWarnings("ConstantConditions")
public class MyApplication extends Application {

    AppOpenAd appOpenAds;

    @Override
    public void onCreate() {
        super.onCreate();
        initOpenAds();
    }

    private void initOpenAds() {
        if (!Constant.FORCE_TO_SHOW_APP_OPEN_AD_ON_START) {
            registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleObserver);
            appOpenAds = new AppOpenAd()
                    .initAppOpenAdMob(new AppOpenAdMob())
                    .initAppOpenAdManager(new AppOpenAdManager())
                    .initAppOpenAdAppLovin(new AppOpenAdAppLovin())
                    .initAppOpenAdWortise(new AppOpenAdWortise())
                    .initAppOpenAdPangle(new AppOpenAdPangle())
                    .initAppOpenAdYandex(new AppOpenAdYandex())
                    .setAdStatus(Constant.AD_STATUS)
                    .setAdNetwork(Constant.AD_NETWORK)
                    .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                    .setPlacementOnStart(Constant.OPEN_ADS_ON_START)
                    .setPlacementOnResume(Constant.OPEN_ADS_ON_RESUME)
                    .setAdMobAppOpenId(Constant.ADMOB_APP_OPEN_AD_ID)
                    .setAdManagerAppOpenId(Constant.GOOGLE_AD_MANAGER_APP_OPEN_AD_ID)
                    .setApplovinAppOpenId(Constant.APPLOVIN_APP_OPEN_AP_ID)
                    .setWortiseAppOpenId(Constant.WORTISE_APP_OPEN_AD_ID)
                    .setPangleAppOpenId(Constant.PANGLE_APP_OPEN_AD_ID)
                    .setYandexAppOpenId(Constant.YANDEX_APP_OPEN_AD_ID);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    LifecycleObserver lifecycleObserver = new DefaultLifecycleObserver() {
        @Override
        public void onStart(@NonNull LifecycleOwner owner) {
            DefaultLifecycleObserver.super.onStart(owner);
            if (AppOpenAd.isAppOpenAdLoaded) {
                appOpenAds.setOnStartLifecycleObserver();
            }
        }
    };

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            appOpenAds.setOnStartActivityLifecycleCallbacks(activity);
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    };

    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        appOpenAds.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

}
