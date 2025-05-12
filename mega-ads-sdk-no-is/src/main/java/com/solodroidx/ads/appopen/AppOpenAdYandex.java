package com.solodroidx.ads.appopen;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.solodroidx.ads.listener.OnShowAdCompleteListener;
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener;
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener;
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader;
import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;

import java.util.Date;

public class AppOpenAdYandex {

    private static final String LOG_TAG = "AppOpenAd";
    AppOpenAdLoader appOpenAdLoader;
    private com.yandex.mobile.ads.appopenad.AppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    public boolean isShowingAd = false;
    private long loadTime = 0;

    public AppOpenAdYandex() {
    }

    public void loadAd(Context context, String yandexAppOpenId) {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        isLoadingAd = true;
        appOpenAdLoader = new AppOpenAdLoader(context);
        appOpenAdLoader.setAdLoadListener(new AppOpenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull com.yandex.mobile.ads.appopenad.AppOpenAd ad) {
                appOpenAd = ad;
                isLoadingAd = false;
                loadTime = (new Date()).getTime();
                Log.d(AppOpenAd.TAG, "Yandex App Open Ad Loaded.");
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                isLoadingAd = false;
                Log.d(AppOpenAd.TAG, "Failed to load Yandex App Open Ad: " + adRequestError);
            }
        });
        AdRequestConfiguration adRequestConfiguration = new AdRequestConfiguration.Builder(yandexAppOpenId).build();
        appOpenAdLoader.loadAd(adRequestConfiguration);
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
        showAdIfAvailable(activity, appOpenAdUnitId, () -> {
        });
    }

    public void showAdIfAvailable(@NonNull final Activity activity, String yandexAppOpenAdUnitId, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (isShowingAd) {
            Log.d(AppOpenAd.TAG, "The app open ad is already showing.");
            return;
        }

        if (!isAdAvailable()) {
            Log.d(AppOpenAd.TAG, "The app open ad is not ready yet.");
            onShowAdCompleteListener.onShowAdComplete();
            loadAd(activity, yandexAppOpenAdUnitId);
            return;
        }

        Log.d(AppOpenAd.TAG, "Will show ad.");
        appOpenAd.setAdEventListener(new AppOpenAdEventListener() {
            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdFailedToShow(@NonNull AdError adError) {
                appOpenAd = null;
                isShowingAd = false;
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity, yandexAppOpenAdUnitId);
                Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getDescription());
            }

            @Override
            public void onAdDismissed() {
                appOpenAd = null;
                isShowingAd = false;
                onShowAdCompleteListener.onShowAdComplete();
                clearAppOpenAd();
                loadAd(activity, yandexAppOpenAdUnitId);
                Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdImpression(@Nullable ImpressionData impressionData) {

            }
        });

        isShowingAd = true;
        if (appOpenAd != null) {
            appOpenAd.show(activity);
        }

    }

    private void clearAppOpenAd() {
        if (appOpenAd != null) {
            appOpenAd.setAdEventListener(null);
            appOpenAd = null;
        }
    }

}