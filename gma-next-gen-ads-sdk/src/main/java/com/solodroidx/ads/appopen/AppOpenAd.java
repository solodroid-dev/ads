package com.solodroidx.ads.appopen;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPLOVIN;
import static com.solodroidx.ads.util.Constant.APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;
import static com.solodroidx.ads.util.Constant.PANGLE;
import static com.solodroidx.ads.util.Constant.WORTISE;
import static com.solodroidx.ads.util.Constant.YANDEX;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAdEventCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.solodroidx.ads.listener.OnShowAdCompleteListener;

// NEXT-GEN SDK IMPORTS (Tanpa import AppOpenAd agar tidak bentrok dengan nama class ini)

public class AppOpenAd {

    public static final String TAG = "AppOpenAd";
    public static boolean isAppOpenAdLoaded = false;

    // PERBAIKAN: Gunakan Fully Qualified Name untuk objek Next-Gen SDK
    public com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd mAppOpenAd = null;

    AppOpenAdMob appOpenAdMob;
    AppOpenAdManager appOpenAdManager;
    AppOpenAdAppLovin appOpenAdAppLovin;
    AppOpenAdWortise appOpenAdWortise;
    AppOpenAdPangle appOpenAdPangle;
    AppOpenAdYandex appOpenAdYandex;

    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    boolean placementOnStart;
    boolean placementOnResume;
    private String adMobAppOpenId = "";
    private String adManagerAppOpenId = "";
    private String applovinAppOpenId = "";
    private String wortiseAppOpenId = "";
    private String pangleAppOpenId = "";
    private String yandexAppOpenId = "";
    boolean withListener = false;
    Activity activity;

    public AppOpenAd() {}

    public AppOpenAd(Activity activity) {
        this.activity = activity;
    }

    // --- INIT ROUTERS ---
    public AppOpenAd initAppOpenAdMob(AppOpenAdMob appOpenAdMob) { this.appOpenAdMob = appOpenAdMob; return this; }
    public AppOpenAd initAppOpenAdManager(AppOpenAdManager appOpenAdManager) { this.appOpenAdManager = appOpenAdManager; return this; }
    public AppOpenAd initAppOpenAdAppLovin(AppOpenAdAppLovin appOpenAdAppLovin) { this.appOpenAdAppLovin = appOpenAdAppLovin; return this; }
    public AppOpenAd initAppOpenAdWortise(AppOpenAdWortise appOpenAdWortise) { this.appOpenAdWortise = appOpenAdWortise; return this; }
    public AppOpenAd initAppOpenAdPangle(AppOpenAdPangle appOpenAdPangle) { this.appOpenAdPangle = appOpenAdPangle; return this; }
    public AppOpenAd initAppOpenAdYandex(AppOpenAdYandex appOpenAdYandex) { this.appOpenAdYandex = appOpenAdYandex; return this; }

    // --- BUILDER SETTERS ---
    public AppOpenAd setPlacementOnStart(boolean placementOnStart) { this.placementOnStart = placementOnStart; return this; }
    public AppOpenAd setPlacementOnResume(boolean placementOnResume) { this.placementOnResume = placementOnResume; return this; }
    public AppOpenAd setAdStatus(String adStatus) { this.adStatus = adStatus; return this; }
    public AppOpenAd setAdNetwork(String adNetwork) { this.adNetwork = adNetwork; return this; }
    public AppOpenAd setBackupAdNetwork(String backupAdNetwork) { this.backupAdNetwork = backupAdNetwork; return this; }
    public AppOpenAd setAdMobAppOpenId(String adMobAppOpenId) { this.adMobAppOpenId = adMobAppOpenId; return this; }
    public AppOpenAd setAdManagerAppOpenId(String adManagerAppOpenId) { this.adManagerAppOpenId = adManagerAppOpenId; return this; }
    public AppOpenAd setApplovinAppOpenId(String applovinAppOpenId) { this.applovinAppOpenId = applovinAppOpenId; return this; }
    public AppOpenAd setWortiseAppOpenId(String wortiseAppOpenId) { this.wortiseAppOpenId = wortiseAppOpenId; return this; }
    public AppOpenAd setPangleAppOpenId(String pangleAppOpenId) { this.pangleAppOpenId = pangleAppOpenId; return this; }
    public AppOpenAd setYandexAppOpenId(String yandexAppOpenId) { this.yandexAppOpenId = yandexAppOpenId; return this; }
    public AppOpenAd setWithListener(boolean withListener) { this.withListener = withListener; return this; }

    // --- PUBLIC METHODS ---

    public AppOpenAd build(OnShowAdCompleteListener onShowAdCompleteListener) {
        loadAppOpenAd(withListener, onShowAdCompleteListener);
        return this;
    }

    public AppOpenAd show(OnShowAdCompleteListener onShowAdCompleteListener) {
        showAppOpenAd(onShowAdCompleteListener);
        return this;
    }

    public AppOpenAd setOnStartLifecycleObserver() {
        onStartLifecycleObserver();
        return this;
    }

    public AppOpenAd setOnStartActivityLifecycleCallbacks(Activity activity) {
        onStartActivityLifecycleCallbacks(activity);
        return this;
    }

    // --- LIFECYCLE ROUTERS (Unchanged, delegates to external classes) ---

    private void onStartLifecycleObserver() {
        if (placementOnResume && adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                case ADMOB:
                    if (!adMobAppOpenId.equals("0") && !activity.getIntent().hasExtra("unique_id")) appOpenAdMob.showAdIfAvailable(activity, adMobAppOpenId);
                    break;
                case GOOGLE_AD_MANAGER:
                    if (!adManagerAppOpenId.equals("0") && !activity.getIntent().hasExtra("unique_id")) appOpenAdManager.showAdIfAvailable(activity, adManagerAppOpenId);
                    break;
                case APPLOVIN:
                case APPLOVIN_MAX:
                    if (!applovinAppOpenId.equals("0") && !activity.getIntent().hasExtra("unique_id")) appOpenAdAppLovin.showAdIfAvailable(activity, applovinAppOpenId);
                    break;
                case WORTISE:
                    if (!wortiseAppOpenId.equals("0") && !activity.getIntent().hasExtra("unique_id")) appOpenAdWortise.showAdIfAvailable(activity, wortiseAppOpenId);
                    break;
                case PANGLE:
                    if (!pangleAppOpenId.equals("0") && !activity.getIntent().hasExtra("unique_id")) appOpenAdPangle.showAdIfAvailable(activity, pangleAppOpenId);
                    break;
                case YANDEX:
                    if (!yandexAppOpenId.equals("0") && !activity.getIntent().hasExtra("unique_id")) appOpenAdYandex.showAdIfAvailable(activity, yandexAppOpenId);
                    break;
            }
        }
    }

    private void onStartActivityLifecycleCallbacks(Activity activity) {
        if (placementOnStart && adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                case ADMOB:
                    if (!adMobAppOpenId.equals("0") && !appOpenAdMob.isShowingAd) this.activity = activity;
                    break;
                case GOOGLE_AD_MANAGER:
                    if (!adManagerAppOpenId.equals("0") && !appOpenAdManager.isShowingAd) this.activity = activity;
                    break;
                case APPLOVIN:
                case APPLOVIN_MAX:
                    if (!applovinAppOpenId.equals("0") && !appOpenAdAppLovin.isShowingAd) this.activity = activity;
                    break;
                case WORTISE:
                    if (!wortiseAppOpenId.equals("0") && !appOpenAdWortise.isShowingAd) this.activity = activity;
                    break;
                case PANGLE:
                    if (!pangleAppOpenId.equals("0") && !appOpenAdPangle.isShowingAd) this.activity = activity;
                    break;
                case YANDEX:
                    if (!yandexAppOpenId.equals("0") && !appOpenAdYandex.isShowingAd) this.activity = activity;
                    break;
            }
        }
    }

    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (placementOnStart && adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                case ADMOB:
                    if (!adMobAppOpenId.equals("0")) {
                        appOpenAdMob.showAdIfAvailable(activity, adMobAppOpenId, onShowAdCompleteListener);
                        AppOpenAd.isAppOpenAdLoaded = true;
                    } else onShowAdCompleteListener.onShowAdComplete();
                    break;
                case GOOGLE_AD_MANAGER:
                    if (!adManagerAppOpenId.equals("0")) {
                        appOpenAdManager.showAdIfAvailable(activity, adManagerAppOpenId, onShowAdCompleteListener);
                        AppOpenAd.isAppOpenAdLoaded = true;
                    } else onShowAdCompleteListener.onShowAdComplete();
                    break;
                case APPLOVIN:
                case APPLOVIN_MAX:
                    if (!applovinAppOpenId.equals("0")) {
                        appOpenAdAppLovin.showAdIfAvailable(activity, applovinAppOpenId, onShowAdCompleteListener);
                        AppOpenAd.isAppOpenAdLoaded = true;
                    } else onShowAdCompleteListener.onShowAdComplete();
                    break;
                case WORTISE:
                    if (!wortiseAppOpenId.equals("0")) {
                        appOpenAdWortise.showAdIfAvailable(activity, wortiseAppOpenId, onShowAdCompleteListener);
                        AppOpenAd.isAppOpenAdLoaded = true;
                    } else onShowAdCompleteListener.onShowAdComplete();
                    break;
                case PANGLE:
                    if (!pangleAppOpenId.equals("0")) {
                        appOpenAdPangle.showAdIfAvailable(activity, pangleAppOpenId, onShowAdCompleteListener);
                        AppOpenAd.isAppOpenAdLoaded = true;
                    } else onShowAdCompleteListener.onShowAdComplete();
                    break;
                case YANDEX:
                    if (!yandexAppOpenId.equals("0")) {
                        appOpenAdYandex.showAdIfAvailable(activity, yandexAppOpenId, onShowAdCompleteListener);
                        AppOpenAd.isAppOpenAdLoaded = true;
                    } else onShowAdCompleteListener.onShowAdComplete();
                    break;
                default:
                    onShowAdCompleteListener.onShowAdComplete();
                    break;
            }
        } else {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    // --- NEXT-GEN LOAD & SHOW LOGIC ---

    private void loadAppOpenAd(boolean withListener, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (!adStatus.equals(AD_STATUS_ON)) return;
        routeAppOpenRequest(adNetwork, false, withListener, onShowAdCompleteListener);
    }

    private void loadBackupAppOpenAd(boolean withListener, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (!adStatus.equals(AD_STATUS_ON) || backupAdNetwork.equals(NONE)) {
            if (withListener && onShowAdCompleteListener != null) onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        routeAppOpenRequest(backupAdNetwork, true, withListener, onShowAdCompleteListener);
    }

    private void routeAppOpenRequest(String targetNetwork, boolean isBackup, boolean withListener, OnShowAdCompleteListener onComplete) {
        switch (targetNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
                loadNextGenAppOpen(adMobAppOpenId, targetNetwork, isBackup, withListener, onComplete);
                break;
            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                loadNextGenAppOpen(adManagerAppOpenId, targetNetwork, isBackup, withListener, onComplete);
                break;
            default:
                if (!isBackup) {
                    loadBackupAppOpenAd(withListener, onComplete);
                } else {
                    if (withListener && onComplete != null) onComplete.onShowAdComplete();
                }
                break;
        }
    }

    private void loadNextGenAppOpen(String adUnitId, String networkName, boolean isBackup, boolean withListener, OnShowAdCompleteListener onComplete) {
        if (adUnitId == null || adUnitId.equals("0") || adUnitId.isEmpty()) {
            handleFailedLoad(isBackup, withListener, onComplete);
            return;
        }

        try {
            AdRequest adRequest = new AdRequest.Builder(adUnitId).build();
            com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd.load(
                    adRequest,
                    new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd>() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAd ad) {
                            mAppOpenAd = ad;
                            isAppOpenAdLoaded = true;
                            Log.d(TAG, "[" + networkName + "] App Open Ad Loaded (" + (isBackup ? "Backup" : "Main") + ")");
                            if (withListener) showAppOpenAd(onComplete);
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.e(TAG, "[" + networkName + "] Failed to Load App Open Ad: " + loadAdError.getMessage());
                            handleFailedLoad(isBackup, withListener, onComplete);
                        }
                    }
            );
        } catch (IllegalStateException e) {
            mAppOpenAd = null;
            isAppOpenAdLoaded = false;
            Log.e(TAG, "Mencegah Crash: MobileAds belum diinisialisasi! Menunda pemuatan iklan. " + e.getMessage());
            handleFailedLoad(isBackup, withListener, onComplete);
        }
    }

    private void handleFailedLoad(boolean isBackup, boolean withListener, OnShowAdCompleteListener onComplete) {
        if (!isBackup) {
            loadBackupAppOpenAd(withListener, onComplete);
        } else {
            if (withListener && onComplete != null) onComplete.onShowAdComplete();
        }
    }

    private void showAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
        if (mAppOpenAd != null) {

            mAppOpenAd.setAdEventCallback(new AppOpenAdEventCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    mAppOpenAd = null;
                    isAppOpenAdLoaded = false;
                    Log.d(TAG, "App Open Ad Dismissed");

                    if (withListener && onShowAdCompleteListener != null) {
                        onShowAdCompleteListener.onShowAdComplete();
                    } else {
                        // Muat ulang otomatis untuk sesi berikutnya
                        loadAppOpenAd(false, onShowAdCompleteListener);
                    }
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError adError) {
                    mAppOpenAd = null;
                    Log.e(TAG, "Failed to Show App Open Ad: " + adError.getMessage());
                    if (onShowAdCompleteListener != null) {
                        onShowAdCompleteListener.onShowAdComplete();
                    }
                }
            });

            mAppOpenAd.show(activity);
            Log.d(TAG, "App Open Ad is Showing");

        } else {
            Log.d(TAG, "App Open Ad is null, cannot show. Triggering complete listener.");
            if (onShowAdCompleteListener != null) {
                onShowAdCompleteListener.onShowAdComplete();
            }
        }
    }

    public void destroyOpenAd() {
        AppOpenAd.isAppOpenAdLoaded = false;
        mAppOpenAd = null;
    }
}