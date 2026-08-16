package com.solodroidx.ads.appopen;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd;
import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAdEventCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.solodroidx.ads.listener.OnShowAdCompleteListener;

import java.util.Date;

public class AppOpenAdMob {

    private static final String LOG_TAG = "AppOpenAd";
    private AppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    public boolean isShowingAd = false;
    private long loadTime = 0;

    public AppOpenAdMob() {}

    public void loadAd(Context context, String adMobAppOpenAdUnitId) {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }

        isLoadingAd = true;
        try {
            AdRequest request = new AdRequest.Builder(adMobAppOpenAdUnitId).build();
            AppOpenAd.load(request, new AdLoadCallback<AppOpenAd>() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd ad) {
                    appOpenAd = ad;
                    isLoadingAd = false;
                    loadTime = (new Date()).getTime();
                    Log.d(LOG_TAG, "onAdLoaded.");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    isLoadingAd = false;
                    Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                }
            });
        } catch (IllegalStateException e) {
            isLoadingAd = false;
            Log.e(LOG_TAG, "Mencegah Crash: MobileAds belum diinisialisasi! " + e.getMessage());
        }
    }

    public boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    public void showAdIfAvailable(@NonNull final Activity activity, String appOpenAdUnitId) {
        showAdIfAvailable(activity, appOpenAdUnitId, () -> {});
    }

    public void showAdIfAvailable(@NonNull final Activity activity, String appOpenAdUnitId, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.");
            return;
        }

        if (!isAdAvailable()) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.");
            onShowAdCompleteListener.onShowAdComplete();
            loadAd(activity, appOpenAdUnitId);
            return;
        }

        Log.d(LOG_TAG, "Will show ad.");

        appOpenAd.setAdEventCallback(new AppOpenAdEventCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                appOpenAd = null;
                isShowingAd = false;
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity, appOpenAdUnitId);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                appOpenAd = null;
                isShowingAd = false;
                Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity, appOpenAdUnitId);
            }
        });

        isShowingAd = true;
        appOpenAd.show(activity);
    }
}