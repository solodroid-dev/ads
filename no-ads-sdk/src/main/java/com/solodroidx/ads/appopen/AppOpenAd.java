package com.solodroidx.ads.appopen;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPLOVIN;
import static com.solodroidx.ads.util.Constant.APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.PANGLE;
import static com.solodroidx.ads.util.Constant.WORTISE;
import static com.solodroidx.ads.util.Constant.YANDEX;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.solodroidx.ads.listener.OnShowAdCompleteListener;

public class AppOpenAd {

    public static final String TAG = "AppOpenAd";
    public static boolean isAppOpenAdLoaded = false;
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
                        } else {
                            onShowAdCompleteListener.onShowAdComplete();
                        }
                        break;
                    case GOOGLE_AD_MANAGER:
                        if (!adManagerAppOpenId.equals("0")) {
                            appOpenAdManager.showAdIfAvailable(activity, adManagerAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        } else {
                            onShowAdCompleteListener.onShowAdComplete();
                        }
                        break;
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                        if (!applovinAppOpenId.equals("0")) {
                            appOpenAdAppLovin.showAdIfAvailable(activity, applovinAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        } else {
                            onShowAdCompleteListener.onShowAdComplete();
                        }
                        break;
                    case WORTISE:
                        if (!wortiseAppOpenId.equals("0")) {
                            appOpenAdWortise.showAdIfAvailable(activity, wortiseAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        } else {
                            onShowAdCompleteListener.onShowAdComplete();
                        }
                        break;
                    case PANGLE:
                        if (!pangleAppOpenId.equals("0")) {
                            appOpenAdPangle.showAdIfAvailable(activity, pangleAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        } else {
                            onShowAdCompleteListener.onShowAdComplete();
                        }
                        break;
                    case YANDEX:
                        if (!yandexAppOpenId.equals("0")) {
                            appOpenAdYandex.showAdIfAvailable(activity, yandexAppOpenId, onShowAdCompleteListener);
                            AppOpenAd.isAppOpenAdLoaded = true;
                        } else {
                            onShowAdCompleteListener.onShowAdComplete();
                        }
                        break;
                    default:
                        onShowAdCompleteListener.onShowAdComplete();
                        break;
                }
            } else {
                onShowAdCompleteListener.onShowAdComplete();
            }
        } else {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    private void loadAppOpenAd(boolean withListener, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                default:
                    loadBackupAppOpenAd(withListener, onShowAdCompleteListener);
                    Log.d(TAG, "[" + adNetwork + "] " + "does not Support App Open Ad Format, try to load Backup Ads");
                    break;
            }
        }
    }

    private void showAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
        switch (adNetwork) {
            default:
                showBackupAppOpenAd(onShowAdCompleteListener);
                break;
        }
    }

    private void loadBackupAppOpenAd(boolean withListener, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (backupAdNetwork) {
                default:
                    onShowAdCompleteListener.onShowAdComplete();
                    Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Selected Ad Network does not Support App Open Ad Format");
                    break;
            }
        }
    }

    private void showBackupAppOpenAd(OnShowAdCompleteListener onShowAdCompleteListener) {
        switch (backupAdNetwork) {
            default:
                break;
        }
    }

    public void destroyOpenAd() {
        AppOpenAd.isAppOpenAdLoaded = false;
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                default:
                    break;
            }
        }
    }

}
