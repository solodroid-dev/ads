package com.solodroidx.ads.appopen;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAd;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdLoadListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenRequest;
import com.solodroidx.ads.listener.OnShowAdCompleteListener;

import java.util.Date;

public class AppOpenAdPangle {

    private PAGAppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    public boolean isShowingAd = false;
    private long loadTime = 0;

    public AppOpenAdPangle() {
    }

    public void loadAd(Context context, String pangleAppOpenId) {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        isLoadingAd = true;
        PAGAppOpenRequest request = new PAGAppOpenRequest();
        request.setTimeout(3000);
        PAGAppOpenAd.loadAd(pangleAppOpenId, request, new PAGAppOpenAdLoadListener() {
            @Override
            public void onError(int code, String message) {
                isLoadingAd = false;
                Log.d(AppOpenAd.TAG, "Failed to load Pangle App Open Ad: " + message);
            }

            @Override
            public void onAdLoaded(PAGAppOpenAd ad) {
                appOpenAd = ad;
                isLoadingAd = false;
                loadTime = (new Date()).getTime();
                Log.d(AppOpenAd.TAG, "Pangle App Open Ad Loaded.");
            }
        });
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

    public void showAdIfAvailable(@NonNull final Activity activity, String pangleAppOpenId, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (isShowingAd) {
            Log.d(AppOpenAd.TAG, "The app open ad is already showing.");
            return;
        }

        if (!isAdAvailable()) {
            Log.d(AppOpenAd.TAG, "The app open ad is not ready yet.");
            onShowAdCompleteListener.onShowAdComplete();
            loadAd(activity, pangleAppOpenId);
            return;
        }

        Log.d(AppOpenAd.TAG, "Will show ad.");

        appOpenAd.setAdInteractionListener(new PAGAppOpenAdInteractionListener() {
            @Override
            public void onAdShowed() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdDismissed() {
                appOpenAd = null;
                isShowingAd = false;

                Log.d(AppOpenAd.TAG, "onAdDismissedFullScreenContent.");

                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity, pangleAppOpenId);
            }
        });

        isShowingAd = true;
        if (appOpenAd != null) {
            appOpenAd.show(activity);
        }

    }

}