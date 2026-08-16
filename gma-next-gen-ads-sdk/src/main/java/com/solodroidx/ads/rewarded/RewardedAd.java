package com.solodroidx.ads.rewarded;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAdEventCallback;
import com.solodroidx.ads.listener.OnRewardedAdCompleteListener;
import com.solodroidx.ads.listener.OnRewardedAdDismissedListener;
import com.solodroidx.ads.listener.OnRewardedAdErrorListener;
import com.solodroidx.ads.listener.OnRewardedAdLoadedListener;

// NEXT-GEN SDK IMPORTS
// Perhatikan: Import RewardedAd dihapus agar tidak bentrok dengan class buatan Anda

public class RewardedAd {

    private static final String TAG = "SoloRewarded";
    private final Activity activity;

    // PERBAIKAN: Gunakan Fully Qualified Name untuk objek Next-Gen SDK
    private com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd mRewardedAd;

    private String adStatus = "";
    private String mainAds = "";
    private String backupAds = "";
    private String adMobRewardedId = "";
    private String adManagerRewardedId = "";
    private String fanRewardedId = "";
    private String unityRewardedId = "";
    private String applovinMaxRewardedId = "";
    private String applovinDiscRewardedZoneId = "";
    private String ironSourceRewardedId = "";
    private String wortiseRewardedId = "";
    private String alienAdsRewardedId = "";
    private String pangleRewardedId = "";
    private String huaweiRewardedId = "";
    private String yandexRewardedId = "";
    private int placementStatus = 1;
    private boolean legacyGDPR = false;

    public RewardedAd(Activity activity) {
        this.activity = activity;
    }

    // --- PUBLIC BUILDER METHODS ---

    public RewardedAd build(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        loadRewardedAd(onComplete, onDismiss);
        return this;
    }

    public RewardedAd build(OnRewardedAdLoadedListener onLoaded, OnRewardedAdErrorListener onError, OnRewardedAdDismissedListener onDismiss, OnRewardedAdCompleteListener onComplete) {
        loadAndShowRewardedAd(onLoaded, onError, onDismiss, onComplete);
        return this;
    }

    public RewardedAd show(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
        showRewardedAd(onComplete, onDismiss, onError);
        return this;
    }

    // --- SETTERS ---
    public RewardedAd setAdStatus(String adStatus) { this.adStatus = adStatus; return this; }
    public RewardedAd setMainAds(String mainAds) { this.mainAds = mainAds; return this; }
    public RewardedAd setBackupAds(String backupAds) { this.backupAds = backupAds; return this; }
    public RewardedAd setAdMobRewardedId(String adMobRewardedId) { this.adMobRewardedId = adMobRewardedId; return this; }
    public RewardedAd setAdManagerRewardedId(String adManagerRewardedId) { this.adManagerRewardedId = adManagerRewardedId; return this; }
    public RewardedAd setFanRewardedId(String fanRewardedId) { this.fanRewardedId = fanRewardedId; return this; }
    public RewardedAd setUnityRewardedId(String unityRewardedId) { this.unityRewardedId = unityRewardedId; return this; }
    public RewardedAd setApplovinMaxRewardedId(String id) { this.applovinMaxRewardedId = id; return this; }
    public RewardedAd setApplovinDiscRewardedZoneId(String id) { this.applovinDiscRewardedZoneId = id; return this; }
    public RewardedAd setIronSourceRewardedId(String ironSourceRewardedId) { this.ironSourceRewardedId = ironSourceRewardedId; return this; }
    public RewardedAd setWortiseRewardedId(String wortiseRewardedId) { this.wortiseRewardedId = wortiseRewardedId; return this; }
    public RewardedAd setAlienAdsRewardedId(String alienAdsRewardedId) { this.alienAdsRewardedId = alienAdsRewardedId; return this; }
    public RewardedAd setPangleRewardedId(String pangleRewardedId) { this.pangleRewardedId = pangleRewardedId; return this; }
    public RewardedAd setHuaweiRewardedId(String huaweiRewardedId) { this.huaweiRewardedId = huaweiRewardedId; return this; }
    public RewardedAd setYandexRewardedId(String yandexRewardedId) { this.yandexRewardedId = yandexRewardedId; return this; }
    public RewardedAd setPlacementStatus(int placementStatus) { this.placementStatus = placementStatus; return this; }
    public RewardedAd setLegacyGDPR(boolean legacyGDPR) { this.legacyGDPR = legacyGDPR; return this; }

    // --- LOGIC UTAMA ---

    private boolean isAdConfigValid() {
        return adStatus.equals(AD_STATUS_ON) && placementStatus != 0;
    }

    public void loadRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        if (!isAdConfigValid()) return;
        routeRewardedRequest(mainAds, false, false, null, null, onDismiss, onComplete);
    }

    private void loadRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        if (!isAdConfigValid() || backupAds.equals(NONE)) return;
        routeRewardedRequest(backupAds, true, false, null, null, onDismiss, onComplete);
    }

    public void loadAndShowRewardedAd(OnRewardedAdLoadedListener onLoaded, OnRewardedAdErrorListener onError, OnRewardedAdDismissedListener onDismiss, OnRewardedAdCompleteListener onComplete) {
        if (!isAdConfigValid()) return;
        routeRewardedRequest(mainAds, false, true, onLoaded, onError, onDismiss, onComplete);
    }

    private void loadAndShowRewardedBackupAd(OnRewardedAdLoadedListener onLoaded, OnRewardedAdErrorListener onError, OnRewardedAdDismissedListener onDismiss, OnRewardedAdCompleteListener onComplete) {
        if (!isAdConfigValid() || backupAds.equals(NONE)) {
            if (onError != null) onError.onRewardedAdError();
            return;
        }
        routeRewardedRequest(backupAds, true, true, onLoaded, onError, onDismiss, onComplete);
    }

    private void routeRewardedRequest(String targetNetwork, boolean isBackup, boolean showImmediately,
                                      OnRewardedAdLoadedListener onLoaded, OnRewardedAdErrorListener onError,
                                      OnRewardedAdDismissedListener onDismiss, OnRewardedAdCompleteListener onComplete) {
        switch (targetNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
                loadNextGenRewarded(adMobRewardedId, targetNetwork, isBackup, showImmediately, onLoaded, onError, onDismiss, onComplete);
                break;

            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                loadNextGenRewarded(adManagerRewardedId, targetNetwork, isBackup, showImmediately, onLoaded, onError, onDismiss, onComplete);
                break;

            case NONE:
                if (showImmediately && onError != null) onError.onRewardedAdError();
                break;
        }
    }

    private void loadNextGenRewarded(String adUnitId, String networkName, boolean isBackup, boolean showImmediately,
                                     OnRewardedAdLoadedListener onLoaded, OnRewardedAdErrorListener onError,
                                     OnRewardedAdDismissedListener onDismiss, OnRewardedAdCompleteListener onComplete) {

        if (adUnitId == null || adUnitId.isEmpty()) {
            handleFailedLoad(isBackup, showImmediately, onLoaded, onError, onDismiss, onComplete);
            return;
        }

        AdRequest adRequest = new AdRequest.Builder(adUnitId).build();

        // PERBAIKAN: Gunakan Fully Qualified Name untuk load method & tipe callback
        com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd.load(
                adRequest,
                new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd>() {
                    @Override
                    public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd ad) {
                        mRewardedAd = ad;
                        Log.d(TAG, "[" + networkName + "] Rewarded ad loaded (" + (isBackup ? "Backup" : "Main") + ")");

                        if (onLoaded != null) onLoaded.onRewardedAdLoaded();

                        if (showImmediately) {
                            showRewardedAd(onComplete, onDismiss, onError);
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError error) {
                        mRewardedAd = null;
                        Log.e(TAG, "[" + networkName + "] Failed to load rewarded ad: " + error.getMessage());
                        handleFailedLoad(isBackup, showImmediately, onLoaded, onError, onDismiss, onComplete);
                    }
                }
        );
    }

    private void handleFailedLoad(boolean isBackup, boolean showImmediately,
                                  OnRewardedAdLoadedListener onLoaded, OnRewardedAdErrorListener onError,
                                  OnRewardedAdDismissedListener onDismiss, OnRewardedAdCompleteListener onComplete) {
        if (!isBackup) {
            if (showImmediately) {
                loadAndShowRewardedBackupAd(onLoaded, onError, onDismiss, onComplete);
            } else {
                loadRewardedBackupAd(onComplete, onDismiss);
            }
        } else {
            // Backup juga gagal
            if (showImmediately && onError != null) onError.onRewardedAdError();
        }
    }

    public void showRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
        if (!isAdConfigValid()) return;

        if (mRewardedAd != null) {

            mRewardedAd.setAdEventCallback(new RewardedAdEventCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    mRewardedAd = null;
                    loadRewardedAd(onComplete, onDismiss);
                    // Pastikan callback dismissed juga aman di UI thread
                    activity.runOnUiThread(() -> {
                        if (onDismiss != null) onDismiss.onRewardedAdDismissed();
                    });
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError error) {
                    mRewardedAd = null;
                    Log.e(TAG, "Rewarded ad failed to show: " + error.getMessage());
                    activity.runOnUiThread(() -> {
                        if (onError != null) onError.onRewardedAdError();
                    });
                }
            });

            // PERBAIKAN UTAMA: Bungkus callback reward dengan activity.runOnUiThread(...)
            mRewardedAd.show(activity, rewardItem -> {
                Log.d(TAG, "The user earned the reward.");
                activity.runOnUiThread(() -> {
                    if (onComplete != null) {
                        onComplete.onRewardedAdComplete();
                    }
                });
            });

        } else {
            Log.d(TAG, "Rewarded Ad is null (belum di-load atau gagal dimuat).");
            activity.runOnUiThread(() -> {
                if (onError != null) onError.onRewardedAdError();
            });
        }
    }

    public void destroyRewardedAd() {
        mRewardedAd = null;
    }
}