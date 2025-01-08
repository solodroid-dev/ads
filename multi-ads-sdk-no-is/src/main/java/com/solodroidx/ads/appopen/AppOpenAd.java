package com.solodroidx.ads.appopen;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPLOVIN;
import static com.solodroidx.ads.util.Constant.APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.PANGLE;
import static com.solodroidx.ads.util.Constant.WORTISE;
import static com.solodroidx.ads.util.Constant.YANDEX;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAd;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdLoadListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenRequest;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.solodroidx.ads.listener.OnShowAdCompleteListener;
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener;
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener;
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;

public class AppOpenAd {

    public static final String TAG = "AppOpenAd";
    public static boolean isAppOpenAdLoaded = false;
    public com.google.android.gms.ads.appopen.AppOpenAd appOpenAd = null;
    public MaxAppOpenAd maxAppOpenAd = null;
    public PAGAppOpenAd pangleAppOpenAd = null;
    AppOpenAdLoader appOpenAdLoader;
    private com.yandex.mobile.ads.appopenad.AppOpenAd yandexAppOpenAd = null;
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

    public AppOpenAd() {

    }

    public AppOpenAd(Activity activity) {
        this.activity = activity;
    }

    public AppOpenAd initAppOpenAdMob(AppOpenAdMob appOpenAdMob) {
        this.appOpenAdMob = appOpenAdMob;
        return this;
    }

    public AppOpenAd initAppOpenAdManager(AppOpenAdManager appOpenAdManager) {
        this.appOpenAdManager = appOpenAdManager;
        return this;
    }

    public AppOpenAd initAppOpenAdAppLovin(AppOpenAdAppLovin appOpenAdAppLovin) {
        this.appOpenAdAppLovin = appOpenAdAppLovin;
        return this;
    }

    public AppOpenAd initAppOpenAdWortise(AppOpenAdWortise appOpenAdWortise) {
        this.appOpenAdWortise = appOpenAdWortise;
        return this;
    }

    public AppOpenAd initAppOpenAdPangle(AppOpenAdPangle appOpenAdPangle) {
        this.appOpenAdPangle = appOpenAdPangle;
        return this;
    }

    public AppOpenAd initAppOpenAdYandex(AppOpenAdYandex appOpenAdYandex) {
        this.appOpenAdYandex = appOpenAdYandex;
        return this;
    }

    public AppOpenAd setPlacementOnStart(boolean placementOnStart) {
        this.placementOnStart = placementOnStart;
        return this;
    }

    public AppOpenAd setPlacementOnResume(boolean placementOnResume) {
        this.placementOnResume = placementOnResume;
        return this;
    }

    public AppOpenAd build(OnShowAdCompleteListener onShowAdCompleteListener) {
        loadAppOpenAd(withListener, onShowAdCompleteListener);
        return this;
    }

    public AppOpenAd show(OnShowAdCompleteListener onShowAdCompleteListener) {
        showAppOpenAd(onShowAdCompleteListener);
        return this;
    }

    public AppOpenAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public AppOpenAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public AppOpenAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public AppOpenAd setAdMobAppOpenId(String adMobAppOpenId) {
        this.adMobAppOpenId = adMobAppOpenId;
        return this;
    }

    public AppOpenAd setAdManagerAppOpenId(String adManagerAppOpenId) {
        this.adManagerAppOpenId = adManagerAppOpenId;
        return this;
    }

    public AppOpenAd setApplovinAppOpenId(String applovinAppOpenId) {
        this.applovinAppOpenId = applovinAppOpenId;
        return this;
    }

    public AppOpenAd setWortiseAppOpenId(String wortiseAppOpenId) {
        this.wortiseAppOpenId = wortiseAppOpenId;
        return this;
    }

    public AppOpenAd setPangleAppOpenId(String pangleAppOpenId) {
        this.pangleAppOpenId = pangleAppOpenId;
        return this;
    }

    public AppOpenAd setYandexAppOpenId(String yandexAppOpenId) {
        this.yandexAppOpenId = yandexAppOpenId;
        return this;
    }

    public AppOpenAd setWithListener(boolean withListener) {
        this.withListener = withListener;
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

    private void onStartLifecycleObserver() {
        if (placementOnResume) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                        if (!adMobAppOpenId.equals("0")) {
                            if (!activity.getIntent().hasExtra("unique_id")) {
                                appOpenAdMob.showAdIfAvailable(activity, adMobAppOpenId);
                            }
                        }
                        break;
                    case GOOGLE_AD_MANAGER:
                        if (!adManagerAppOpenId.equals("0")) {
                            if (!activity.getIntent().hasExtra("unique_id")) {
                                appOpenAdManager.showAdIfAvailable(activity, adManagerAppOpenId);
                            }
                        }
                        break;
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                        if (!applovinAppOpenId.equals("0")) {
                            if (!activity.getIntent().hasExtra("unique_id")) {
                                appOpenAdAppLovin.showAdIfAvailable(activity, applovinAppOpenId);
                            }
                        }
                        break;
                    case WORTISE:
                        if (!wortiseAppOpenId.equals("0")) {
                            if (!activity.getIntent().hasExtra("unique_id")) {
                                appOpenAdWortise.showAdIfAvailable(activity, wortiseAppOpenId);
                            }
                        }
                        break;
                    case PANGLE:
                        if (!pangleAppOpenId.equals("0")) {
                            if (!activity.getIntent().hasExtra("unique_id")) {
                                appOpenAdPangle.showAdIfAvailable(activity, pangleAppOpenId);
                            }
                        }
                        break;
                    case YANDEX:
                        if (!yandexAppOpenId.equals("0")) {
                            if (!activity.getIntent().hasExtra("unique_id")) {
                                appOpenAdYandex.showAdIfAvailable(activity, yandexAppOpenId);
                            }
                        }
                        break;
                }
            }
        }
    }

    private void onStartActivityLifecycleCallbacks(Activity activity) {
        if (placementOnStart) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                        if (!adMobAppOpenId.equals("0")) {
                            if (!appOpenAdMob.isShowingAd) {
                                this.activity = activity;
                            }
                        }
                        break;
                    case GOOGLE_AD_MANAGER:
                        if (!adManagerAppOpenId.equals("0")) {
                            if (!appOpenAdManager.isShowingAd) {
                                this.activity = activity;
                            }
                        }
                        break;
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                        if (!applovinAppOpenId.equals("0")) {
                            if (!appOpenAdAppLovin.isShowingAd) {
                                this.activity = activity;
                            }
                        }
                        break;
                    case WORTISE:
                        if (!wortiseAppOpenId.equals("0")) {
                            if (!appOpenAdWortise.isShowingAd) {
                                this.activity = activity;
                            }
                        }
                        break;
                    case PANGLE:
                        if (!pangleAppOpenId.equals("0")) {
                            if (!appOpenAdPangle.isShowingAd) {
                                this.activity = activity;
                            }
                        }
                        break;
                    case YANDEX:
                        if (!yandexAppOpenId.equals("0")) {
                            if (!appOpenAdYandex.isShowingAd) {
                                this.activity = activity;
                            }
                        }
                        break;
                }
            }
        }
    }

    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (placementOnStart) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                        if (!adMobAppOpenId.equals("0")) {
                            appOpenAdMob.showAdIfAvailable(activity, adMobAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        }
                        break;
                    case GOOGLE_AD_MANAGER:
                        if (!adManagerAppOpenId.equals("0")) {
                            appOpenAdManager.showAdIfAvailable(activity, adManagerAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        }
                        break;
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                        if (!applovinAppOpenId.equals("0")) {
                            appOpenAdAppLovin.showAdIfAvailable(activity, applovinAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        }
                        break;
                    case WORTISE:
                        if (!wortiseAppOpenId.equals("0")) {
                            appOpenAdWortise.showAdIfAvailable(activity, wortiseAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        }
                        break;
                    case PANGLE:
                        if (!pangleAppOpenId.equals("0")) {
                            appOpenAdPangle.showAdIfAvailable(activity, pangleAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        }
                        break;
                    case YANDEX:
                        if (!yandexAppOpenId.equals("0")) {
                            appOpenAdYandex.showAdIfAvailable(activity, yandexAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        }
                        break;
                    default:
                        onShowAdCompleteListener.onShowAdComplete();
                        break;
                }
            }
        }
    }

    private void loadAppOpenAd(boolean withListener, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    AdRequest adRequest = new AdRequest.Builder().build();
                    com.google.android.gms.ads.appopen.AppOpenAd.load(activity, adMobAppOpenId, adRequest, new com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.appopen.AppOpenAd ad) {
                            appOpenAd = ad;
                            AppOpenAd.isAppOpenAdLoaded = true;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "App Open Ad Loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            appOpenAd = null;
                            AppOpenAd.isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "Failed to Load App Open Ad: " + loadAdError.getMessage());
                        }
                    });
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    @SuppressLint("VisibleForTests") AdManagerAdRequest adManagerAdRequest = new AdManagerAdRequest.Builder().build();
                    com.google.android.gms.ads.appopen.AppOpenAd.load(activity, adManagerAppOpenId, adManagerAdRequest, new com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.appopen.AppOpenAd ad) {
                            appOpenAd = ad;
                            AppOpenAd.isAppOpenAdLoaded = true;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "App Open Ad Loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            appOpenAd = null;
                            AppOpenAd.isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "Failed to Load App Open Ad: " + loadAdError.getMessage());
                        }
                    });
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                    maxAppOpenAd = new MaxAppOpenAd(applovinAppOpenId, activity);
                    maxAppOpenAd.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            isAppOpenAdLoaded = true;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "app open ad loaded");
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "App Open Ad Dismissed");
                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "failed to load app open ad: " + error.getMessage());
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "[on resume] failed to display app open ad: " + error.getMessage());
                        }
                    });
                    maxAppOpenAd.loadAd();
                    break;

                case PANGLE:
                    PAGAppOpenRequest request = new PAGAppOpenRequest();
                    request.setTimeout(3000);
                    PAGAppOpenAd.loadAd(pangleAppOpenId, request, new PAGAppOpenAdLoadListener() {
                        @Override
                        public void onAdLoaded(PAGAppOpenAd ad) {
                            pangleAppOpenAd = ad;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(()-> isAppOpenAdLoaded = true, 2000);
                            Log.d(TAG, "[" + adNetwork + "] " + "app open ad loaded");
                        }

                        @Override
                        public void onError(int code, String message) {
                            pangleAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "failed to load app open ad : " + message);
                        }
                    });
                    break;

                case YANDEX:
                    appOpenAdLoader = new AppOpenAdLoader(activity);
                    appOpenAdLoader.setAdLoadListener(new AppOpenAdLoadListener() {
                        @Override
                        public void onAdLoaded(@NonNull com.yandex.mobile.ads.appopenad.AppOpenAd ad) {
                            yandexAppOpenAd = ad;
                            isAppOpenAdLoaded = false;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "app open ad loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                            yandexAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "failed to load app open ad : " + adRequestError);
                        }
                    });
                    AdRequestConfiguration adRequestConfiguration = new AdRequestConfiguration.Builder(yandexAppOpenId).build();
                    appOpenAdLoader.loadAd(adRequestConfiguration);
                    break;

                default:
                    loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + "does not Support App Open Ad Format, try to load Backup Ads");
                    break;
            }
        }
    }

    private void showAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
        switch (adNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                if (appOpenAd != null) {
                    appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "App Open Ad Dismissed");
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            appOpenAd = null;
                            showBackupAppOpenAd(onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "Failed to Show App Open Ad Full Screen Content: " + adError.getMessage());
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(TAG, "[" + adNetwork + "] " + "App Open Ad Shown");
                        }
                    });
                    appOpenAd.show(activity);
                    Log.d(TAG, "[" + adNetwork + "] " + " App Open Ad is Showing");
                } else {
                    showBackupAppOpenAd(onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + " Failed to Show App Open Ad, try to show Backup Ads");
                }
                break;

            case APPLOVIN:
            case APPLOVIN_MAX:
                if (maxAppOpenAd != null) {
                    maxAppOpenAd.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            Log.d(TAG, "[" + adNetwork + "] " + "app open ad loaded");
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "App Open Ad Dismissed");
                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "failed to load app open ad: " + error.getMessage());
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "[on resume] failed to display app open ad: " + error.getMessage());
                        }
                    });
                    maxAppOpenAd.showAd();
                } else {
                    showBackupAppOpenAd(onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + " Failed to Show App Open Ad, try to show Backup Ads");
                }
                break;

            case PANGLE:
                if (pangleAppOpenAd != null) {
                    pangleAppOpenAd.setAdInteractionListener(new PAGAppOpenAdInteractionListener() {
                        @Override
                        public void onAdShowed() {
                            Log.d(TAG, "[" + adNetwork + "] " + "show app open ad");
                        }

                        @Override
                        public void onAdClicked() {

                        }

                        @Override
                        public void onAdDismissed() {
                            pangleAppOpenAd = null;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "close app open ad");
                        }
                    });
                    pangleAppOpenAd.show(activity);
                } else {
                    showBackupAppOpenAd(onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + " Failed to Show App Open Ad, try to show Backup Ads");
                }
                break;

            case YANDEX:
                if (yandexAppOpenAd != null) {
                    yandexAppOpenAd.setAdEventListener(new AppOpenAdEventListener() {
                        @Override
                        public void onAdShown() {
                            Log.d(TAG, "[" + adNetwork + "] " + "show app open ad");
                        }

                        @Override
                        public void onAdFailedToShow(@NonNull com.yandex.mobile.ads.common.AdError adError) {
                            yandexAppOpenAd = null;
                            showBackupAppOpenAd(onShowAdCompleteListener);
                            Log.d(TAG, "[" + adNetwork + "] " + "Failed to Show App Open Ad Full Screen Content: " + adError);
                        }

                        @Override
                        public void onAdDismissed() {
                            yandexAppOpenAd = null;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + adNetwork + "] " + "close app open ad");
                        }

                        @Override
                        public void onAdClicked() {

                        }

                        @Override
                        public void onAdImpression(@Nullable ImpressionData impressionData) {

                        }
                    });
                    yandexAppOpenAd.show(activity);
                } else {
                    showBackupAppOpenAd(onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + " Failed to Show App Open Ad, try to show Backup Ads");
                }
                break;

            default:
                showBackupAppOpenAd(onShowAdCompleteListener);
                break;
        }
    }

    private void loadBackupAppOpenAd(boolean withListener, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    AdRequest adRequest = new AdRequest.Builder().build();
                    com.google.android.gms.ads.appopen.AppOpenAd.load(activity, adMobAppOpenId, adRequest, new com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.appopen.AppOpenAd ad) {
                            appOpenAd = ad;
                            AppOpenAd.isAppOpenAdLoaded = true;
                            if (withListener) {
                                showBackupAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "App Open Ad Loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            appOpenAd = null;
                            AppOpenAd.isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Failed to Load App Open Ad: " + loadAdError.getMessage());
                        }
                    });
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    @SuppressLint("VisibleForTests") AdManagerAdRequest adManagerAdRequest = new AdManagerAdRequest.Builder().build();
                    com.google.android.gms.ads.appopen.AppOpenAd.load(activity, adManagerAppOpenId, adManagerAdRequest, new com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.appopen.AppOpenAd ad) {
                            appOpenAd = ad;
                            AppOpenAd.isAppOpenAdLoaded = true;
                            if (withListener) {
                                showBackupAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "App Open Ad Loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            appOpenAd = null;
                            AppOpenAd.isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Failed to Load App Open Ad: " + loadAdError.getMessage());
                        }
                    });
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                    maxAppOpenAd = new MaxAppOpenAd(applovinAppOpenId, activity);
                    maxAppOpenAd.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            isAppOpenAdLoaded = true;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "app open ad loaded");
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "App Open Ad Dismissed");
                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "failed to load app open ad: " + error.getMessage());
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "[on resume] failed to display app open ad: " + error.getMessage());
                        }
                    });
                    maxAppOpenAd.loadAd();
                    break;

                case PANGLE:
                    PAGAppOpenRequest request = new PAGAppOpenRequest();
                    request.setTimeout(3000);
                    PAGAppOpenAd.loadAd(pangleAppOpenId, request, new PAGAppOpenAdLoadListener() {
                        @Override
                        public void onAdLoaded(PAGAppOpenAd ad) {
                            pangleAppOpenAd = ad;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(()-> isAppOpenAdLoaded = true, 2000);
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "app open ad loaded");
                        }

                        @Override
                        public void onError(int code, String message) {
                            pangleAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "failed to load app open ad : " + message);
                        }
                    });
                    break;

                case YANDEX:
                    appOpenAdLoader = new AppOpenAdLoader(activity);
                    appOpenAdLoader.setAdLoadListener(new AppOpenAdLoadListener() {
                        @Override
                        public void onAdLoaded(@NonNull com.yandex.mobile.ads.appopenad.AppOpenAd ad) {
                            yandexAppOpenAd = ad;
                            isAppOpenAdLoaded = true;
                            if (withListener) {
                                showAppOpenAd(onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "app open ad loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                            yandexAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "failed to load app open ad : " + adRequestError);
                        }
                    });
                    AdRequestConfiguration adRequestConfiguration = new AdRequestConfiguration.Builder(yandexAppOpenId).build();
                    appOpenAdLoader.loadAd(adRequestConfiguration);
                    break;

                default:
                    onShowAdCompleteListener.onShowAdComplete();
                    Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Selected Ad Network does not Support App Open Ad Format");
                    break;
            }
        }
    }

    private void showBackupAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
        switch (backupAdNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                if (appOpenAd != null) {
                    appOpenAd.show(activity);
                    appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "App Open Ad Dismissed");
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            appOpenAd = null;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Failed to Show App Open Ad Full Screen Content: " + adError.getMessage());
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "App Open Ad Shown");
                        }
                    });
                } else {
                    onShowAdCompleteListener.onShowAdComplete();
                }
                break;

            case APPLOVIN:
            case APPLOVIN_MAX:
                if (maxAppOpenAd != null) {
                    maxAppOpenAd.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "app open ad loaded");
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "App Open Ad Dismissed");
                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "failed to load app open ad: " + error.getMessage());
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {
                            maxAppOpenAd = null;
                            isAppOpenAdLoaded = false;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "[on resume] failed to display app open ad: " + error.getMessage());
                        }
                    });
                    maxAppOpenAd.showAd();
                } else {
                    onShowAdCompleteListener.onShowAdComplete();
                    Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + " Failed to Show App Open Ad, try to show Backup Ads");
                }
                break;

            case PANGLE:
                if (pangleAppOpenAd != null) {
                    pangleAppOpenAd.setAdInteractionListener(new PAGAppOpenAdInteractionListener() {
                        @Override
                        public void onAdShowed() {
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "show app open ad");
                        }

                        @Override
                        public void onAdClicked() {

                        }

                        @Override
                        public void onAdDismissed() {
                            pangleAppOpenAd = null;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "close app open ad");
                        }
                    });
                    pangleAppOpenAd.show(activity);
                } else {
                    onShowAdCompleteListener.onShowAdComplete();
                }
                break;

            case YANDEX:
                if (yandexAppOpenAd != null) {
                    yandexAppOpenAd.setAdEventListener(new AppOpenAdEventListener() {
                        @Override
                        public void onAdShown() {
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "show app open ad");
                        }

                        @Override
                        public void onAdFailedToShow(@NonNull com.yandex.mobile.ads.common.AdError adError) {
                            yandexAppOpenAd = null;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Failed to Show App Open Ad Full Screen Content: " + adError);
                        }

                        @Override
                        public void onAdDismissed() {
                            yandexAppOpenAd = null;
                            if (withListener) {
                                onShowAdCompleteListener.onShowAdComplete();
                            } else {
                                loadAppOpenAd(false, onShowAdCompleteListener);
                            }
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "close app open ad");
                        }

                        @Override
                        public void onAdClicked() {

                        }

                        @Override
                        public void onAdImpression(@Nullable ImpressionData impressionData) {

                        }
                    });
                    yandexAppOpenAd.show(activity);
                } else {
                    showBackupAppOpenAd(onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + " Failed to Show App Open Ad, try to show Backup Ads");
                }
                break;

            default:
                break;
        }
    }

    public void destroyOpenAd() {
        AppOpenAd.isAppOpenAdLoaded = false;
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (appOpenAd != null) {
                        appOpenAd = null;
                    }
                    break;

                case PANGLE:
                    if (pangleAppOpenAd != null) {
                        pangleAppOpenAd = null;
                    }
                    break;

                case YANDEX:
                    if (yandexAppOpenAd != null) {
                        yandexAppOpenAd.setAdEventListener(null);
                        yandexAppOpenAd = null;
                    }
                    break;

                default:
                    break;
            }
        }
    }

}
