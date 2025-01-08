package com.solodroidx.ads.interstitial;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPLOVIN;
import static com.solodroidx.ads.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroidx.ads.util.Constant.APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.FACEBOOK;
import static com.solodroidx.ads.util.Constant.FAN;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_IRONSOURCE;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.HUAWEI;
import static com.solodroidx.ads.util.Constant.IRONSOURCE;
import static com.solodroidx.ads.util.Constant.NONE;
import static com.solodroidx.ads.util.Constant.PANGLE;
import static com.solodroidx.ads.util.Constant.STARTAPP;
import static com.solodroidx.ads.util.Constant.UNITY;
import static com.solodroidx.ads.util.Constant.WORTISE;
import static com.solodroidx.ads.util.Constant.YANDEX;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdk;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BiddingParam;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener;
import com.solodroidx.ads.appopen.AppOpenAd;
import com.solodroidx.ads.helper.AppLovinCustomEventInterstitial;
import com.solodroidx.ads.listener.OnInterstitialAdDismissedListener;
import com.solodroidx.ads.listener.OnShowAdCompleteListener;
import com.solodroidx.ads.util.Tools;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class InterstitialAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    private com.google.android.gms.ads.interstitial.InterstitialAd adMobInterstitialAd;
    private AdManagerInterstitialAd adManagerInterstitialAd;
    private com.facebook.ads.InterstitialAd fanInterstitialAd;
    private StartAppAd startAppAd;
    private IUnityAdsLoadListener unityAdsLoadListener;
    private MaxInterstitialAd maxInterstitialAd;
    public AppLovinInterstitialAdDialog appLovinInterstitialAdDialog;
    public AppLovinAd appLovinAd;
    public com.wortise.ads.interstitial.InterstitialAd wortiseInterstitialAd;
    private PAGInterstitialAd pangleInterstitialAd;
    private com.huawei.hms.ads.InterstitialAd huaweiInterstitialAd;
    private com.yandex.mobile.ads.interstitial.InterstitialAd yandexInterstitialAd;
    private InterstitialAdLoader yandexInterstitialAdLoader;
    private int retryAttempt;
    private int counter = 1;

    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobInterstitialId = "";
    private String googleAdManagerInterstitialId = "";
    private String fanInterstitialId = "";
    private String unityInterstitialId = "";
    private String appLovinInterstitialId = "";
    private String appLovinInterstitialZoneId = "";
    private String mopubInterstitialId = "";
    private String ironSourceInterstitialId = "";
    private String wortiseInterstitialId = "";
    private String alienAdsInterstitialId = "";
    private String pangleInterstitialId = "";
    private String huaweiInterstitialId = "";
    private String yandexInterstitialId = "";
    private int placementStatus = 1;
    private int interval = 3;
    private boolean legacyGDPR = false;
    private boolean withListener = false;
    private OnInterstitialAdDismissedListener onInterstitialAdDismissedListener;

    public InterstitialAd(Activity activity) {
        this.activity = activity;
    }

    public InterstitialAd build() {
        loadInterstitialAd();
        return this;
    }

    public void show() {
        showInterstitialAd();
    }

    public InterstitialAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public InterstitialAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public InterstitialAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public InterstitialAd setAdMobInterstitialId(String adMobInterstitialId) {
        this.adMobInterstitialId = adMobInterstitialId;
        return this;
    }

    public InterstitialAd setGoogleAdManagerInterstitialId(String googleAdManagerInterstitialId) {
        this.googleAdManagerInterstitialId = googleAdManagerInterstitialId;
        return this;
    }

    public InterstitialAd setFanInterstitialId(String fanInterstitialId) {
        this.fanInterstitialId = fanInterstitialId;
        return this;
    }

    public InterstitialAd setUnityInterstitialId(String unityInterstitialId) {
        this.unityInterstitialId = unityInterstitialId;
        return this;
    }

    public InterstitialAd setAppLovinInterstitialId(String appLovinInterstitialId) {
        this.appLovinInterstitialId = appLovinInterstitialId;
        return this;
    }

    public InterstitialAd setAppLovinInterstitialZoneId(String appLovinInterstitialZoneId) {
        this.appLovinInterstitialZoneId = appLovinInterstitialZoneId;
        return this;
    }

    public InterstitialAd setMopubInterstitialId(String mopubInterstitialId) {
        this.mopubInterstitialId = mopubInterstitialId;
        return this;
    }

    public InterstitialAd setIronSourceInterstitialId(String ironSourceInterstitialId) {
        this.ironSourceInterstitialId = ironSourceInterstitialId;
        return this;
    }

    public InterstitialAd setWortiseInterstitialId(String wortiseInterstitialId) {
        this.wortiseInterstitialId = wortiseInterstitialId;
        return this;
    }

    public InterstitialAd setAlienAdsInterstitialId(String alienAdsInterstitialId) {
        this.alienAdsInterstitialId = alienAdsInterstitialId;
        return this;
    }

    public InterstitialAd setPangleInterstitialId(String pangleInterstitialId) {
        this.pangleInterstitialId = pangleInterstitialId;
        return this;
    }

    public InterstitialAd setHuaweiInterstitialId(String huaweiInterstitialId) {
        this.huaweiInterstitialId = huaweiInterstitialId;
        return this;
    }

    public InterstitialAd setYandexInterstitialId(String yandexInterstitialId) {
        this.yandexInterstitialId = yandexInterstitialId;
        return this;
    }

    public InterstitialAd setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public InterstitialAd setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public InterstitialAd setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public InterstitialAd setWithListener(boolean withListener, OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
        this.withListener = withListener;
        this.onInterstitialAdDismissedListener = onInterstitialAdDismissedListener;
        return this;
    }

    private void loadInterstitialAd() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, adMobInterstitialId, Tools.getAdRequest(activity, legacyGDPR), new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                            adMobInterstitialAd = interstitialAd;
                            adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    Log.d(TAG, "The ad failed to show.");
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    adMobInterstitialAd = null;
                                    Log.d(TAG, "The ad was shown.");
                                }
                            });
                            Log.i(TAG, "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.i(TAG, loadAdError.getMessage());
                            adMobInterstitialAd = null;
                            loadBackupInterstitialAd();
                            Log.d(TAG, "Failed load AdMob Interstitial Ad");
                        }
                    });
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    AdManagerInterstitialAd.load(activity, googleAdManagerInterstitialId, Tools.getGoogleAdManagerRequest(), new AdManagerInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                            super.onAdLoaded(adManagerInterstitialAd);
                            adManagerInterstitialAd = interstitialAd;
                            adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent();
                                    adManagerInterstitialAd = null;
                                    Log.d(TAG, "The ad was shown.");
                                }
                            });
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            adManagerInterstitialAd = null;
                            loadBackupInterstitialAd();
                            Log.d(TAG, "Failed load Ad Manager Interstitial Ad");
                        }
                    });
                    break;

                case FAN:
                case FACEBOOK:
                    fanInterstitialAd = new com.facebook.ads.InterstitialAd(activity, fanInterstitialId);
                    com.facebook.ads.InterstitialAdListener adListener = new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                            fanInterstitialAd.loadAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }

                        @Override
                        public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
                            loadBackupInterstitialAd();
                        }

                        @Override
                        public void onAdLoaded(com.facebook.ads.Ad ad) {
                            Log.d(TAG, "FAN Interstitial is loaded");
                        }

                        @Override
                        public void onAdClicked(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(com.facebook.ads.Ad ad) {

                        }
                    };

                    com.facebook.ads.InterstitialAd.InterstitialLoadAdConfig loadAdConfig = fanInterstitialAd.buildLoadAdConfig().withAdListener(adListener).build();
                    fanInterstitialAd.loadAd(loadAdConfig);
                    break;

                case STARTAPP:
                    startAppAd = new StartAppAd(activity);
                    startAppAd.loadAd(new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull Ad ad) {
                            Log.d(TAG, "Startapp Interstitial Ad loaded");
                        }

                        @Override
                        public void onFailedToReceiveAd(@Nullable Ad ad) {
                            Log.d(TAG, "Failed to load Startapp Interstitial Ad");
                            loadBackupInterstitialAd();
                        }
                    });
                    break;

                case UNITY:
                    unityAdsLoadListener = new IUnityAdsLoadListener() {
                        @Override
                        public void onUnityAdsAdLoaded(String placementId) {
                            Log.d(TAG, "unity interstitial ad loaded");
                        }

                        @Override
                        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                            Log.e(TAG, "Unity Ads failed to load ad : " + unityInterstitialId + " : error : " + message);
                            loadBackupInterstitialAd();
                        }
                    };
                    try {
                        UnityAds.load(unityInterstitialId, unityAdsLoadListener);
                    } catch (Exception e) {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                        e.printStackTrace();
                    }
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    maxInterstitialAd = new MaxInterstitialAd(appLovinInterstitialId, activity);
                    maxInterstitialAd.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            retryAttempt = 0;
                            Log.d(TAG, "AppLovin Interstitial Ad loaded...");
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {
                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {
                            maxInterstitialAd.loadAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            retryAttempt++;
                            long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));
                            new Handler().postDelayed(() -> maxInterstitialAd.loadAd(), delayMillis);
                            loadBackupInterstitialAd();
                            Log.d(TAG, "failed to load AppLovin Interstitial");
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {
                            maxInterstitialAd.loadAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }
                    });

                    // Load the first ad
                    maxInterstitialAd.loadAd();
                    break;

                case APPLOVIN_DISCOVERY:
                    AdRequest.Builder builder = new AdRequest.Builder();
                    Bundle interstitialExtras = new Bundle();
                    interstitialExtras.putString("zone_id", appLovinInterstitialZoneId);
                    builder.addCustomEventExtrasBundle(AppLovinCustomEventInterstitial.class, interstitialExtras);
                    AppLovinSdk.getInstance(activity).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
                        @Override
                        public void adReceived(AppLovinAd ad) {
                            appLovinAd = ad;
                        }

                        @Override
                        public void failedToReceiveAd(int errorCode) {
                            loadBackupInterstitialAd();
                        }
                    });
                    appLovinInterstitialAdDialog = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    IronSource.setLevelPlayInterstitialListener(new LevelPlayInterstitialListener() {
                        @Override
                        public void onAdReady(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdReady");
                        }

                        @Override
                        public void onAdLoadFailed(IronSourceError ironSourceError) {
                            Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
                            loadBackupInterstitialAd();
                        }

                        @Override
                        public void onAdOpened(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdOpened");
                        }

                        @Override
                        public void onAdShowSucceeded(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdShowSucceeded");
                        }

                        @Override
                        public void onAdShowFailed(IronSourceError ironSourceError, AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
                            loadBackupInterstitialAd();
                        }

                        @Override
                        public void onAdClicked(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdClicked");
                        }

                        @Override
                        public void onAdClosed(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdClosed");
                            loadInterstitialAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }
                    });
                    IronSource.loadInterstitial();
                    break;

                case WORTISE:
                    wortiseInterstitialAd = new com.wortise.ads.interstitial.InterstitialAd(activity, wortiseInterstitialId);
                    wortiseInterstitialAd.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
                        @Override
                        public void onInterstitialImpression(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                        }

                        @Override
                        public void onInterstitialFailedToShow(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {

                        }

                        @Override
                        public void onInterstitialFailedToLoad(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {
                            loadBackupInterstitialAd();
                            Log.d(TAG, "[Wortise] Failed to load Interstitial Ad");
                        }

                        @Override
                        public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                        }

                        @Override
                        public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                            loadInterstitialAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                            Log.d(TAG, "[Wortise] Interstitial Ad dismissed");
                        }

                        @Override
                        public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                            Log.d(TAG, "[Wortise] Interstitial Ad loaded");
                        }

                        @Override
                        public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                        }
                    });
                    wortiseInterstitialAd.loadAd();
                    break;

                case PANGLE:
                    PAGInterstitialAd.loadAd(pangleInterstitialId, new PAGInterstitialRequest(), new PAGInterstitialAdLoadListener() {
                        @Override
                        public void onAdLoaded(PAGInterstitialAd interstitialAd) {
                            pangleInterstitialAd = interstitialAd;
                            pangleInterstitialAd.setAdInteractionListener(new PAGInterstitialAdInteractionListener() {
                                @Override
                                public void onAdShowed() {

                                }

                                @Override
                                public void onAdClicked() {

                                }

                                @Override
                                public void onAdDismissed() {
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                    Log.d(TAG, "[" + adNetwork + "] " + "Interstitial Ad Dismissed");
                                }
                            });
                            Log.d(TAG, "[" + adNetwork + "] " + "Interstitial Ad Loaded");
                        }

                        @Override
                        public void onError(int code, String message) {
                            loadBackupInterstitialAd();
                            pangleInterstitialAd = null;
                            Log.d(TAG, "[" + adNetwork + "] " + "Failed: " + code + " : " + message);
                        }
                    });
                    break;

                case HUAWEI:
                    huaweiInterstitialAd = new com.huawei.hms.ads.InterstitialAd(activity);
                    huaweiInterstitialAd.setAdId(huaweiInterstitialId);
                    AdParam.Builder AdParamBuilder = new AdParam.Builder();
                    BiddingParam biddingParam = new BiddingParam();
                    AdParamBuilder.addBiddingParamMap(huaweiInterstitialId, biddingParam);
                    AdParamBuilder.setTMax(500);
                    huaweiInterstitialAd.loadAd(AdParamBuilder.build());
                    huaweiInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Called when an ad is loaded successfully.
                            Log.d(TAG, "Huawei Interstitial Ad Loaded");
                        }

                        @Override
                        public void onAdFailed(int errorCode) {
                            // Called when an ad fails to be loaded.
                            huaweiInterstitialAd = null;
                            loadBackupInterstitialAd();
                            Log.d(TAG, "Failed to Load Huawei Interstitial");
                        }

                        @Override
                        public void onAdClosed() {
                            // Called when an ad is closed.
                            loadInterstitialAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                            Log.d(TAG, "Huawei Interstitial Ad Closed");
                        }

                        @Override
                        public void onAdClicked() {
                            // Called when an ad is clicked.
                        }

                        @Override
                        public void onAdLeave() {
                            // Called when an ad leaves an app.
                        }

                        @Override
                        public void onAdOpened() {
                            // Called when an ad is opened.
                        }
                    });
                    break;

                case YANDEX:
                    yandexInterstitialAdLoader = new InterstitialAdLoader(activity);
                    yandexInterstitialAdLoader.setAdLoadListener(new InterstitialAdLoadListener() {
                        @Override
                        public void onAdLoaded(@NonNull com.yandex.mobile.ads.interstitial.InterstitialAd interstitialAd) {
                            yandexInterstitialAd = interstitialAd;
                            Log.d(TAG, "Yandex Interstitial Ad Loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                            Log.d(TAG, "Failed to Load Yandex Interstitial: " + adRequestError);
                            yandexInterstitialAd = null;
                            loadBackupInterstitialAd();
                        }
                    });
                    AdRequestConfiguration adRequestConfiguration = new AdRequestConfiguration.Builder(yandexInterstitialId).build();
                    yandexInterstitialAdLoader.loadAd(adRequestConfiguration);
                    break;

                default:
                    break;
            }
        }
    }

    private void loadBackupInterstitialAd() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, adMobInterstitialId, Tools.getAdRequest(activity, legacyGDPR), new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                            adMobInterstitialAd = interstitialAd;
                            adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    Log.d(TAG, "The ad failed to show.");
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    adMobInterstitialAd = null;
                                    Log.d(TAG, "The ad was shown.");
                                }
                            });
                            Log.i(TAG, "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.i(TAG, loadAdError.getMessage());
                            adMobInterstitialAd = null;
                            Log.d(TAG, "Failed load AdMob Interstitial Ad");
                        }
                    });
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    AdManagerInterstitialAd.load(activity, googleAdManagerInterstitialId, Tools.getGoogleAdManagerRequest(), new AdManagerInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                            super.onAdLoaded(adManagerInterstitialAd);
                            adManagerInterstitialAd = interstitialAd;
                            adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                }

                                @Override
                                public void onAdImpression() {
                                    super.onAdImpression();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent();
                                    adManagerInterstitialAd = null;
                                    Log.d(TAG, "The ad was shown.");
                                }
                            });
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            adManagerInterstitialAd = null;
                            Log.d(TAG, "Failed load Ad Manager Interstitial Ad");
                        }
                    });
                    break;

                case FAN:
                case FACEBOOK:
                    fanInterstitialAd = new com.facebook.ads.InterstitialAd(activity, fanInterstitialId);
                    com.facebook.ads.InterstitialAdListener adListener = new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                            fanInterstitialAd.loadAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }

                        @Override
                        public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(com.facebook.ads.Ad ad) {
                            Log.d(TAG, "FAN Interstitial is loaded");
                        }

                        @Override
                        public void onAdClicked(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(com.facebook.ads.Ad ad) {

                        }
                    };

                    com.facebook.ads.InterstitialAd.InterstitialLoadAdConfig loadAdConfig = fanInterstitialAd.buildLoadAdConfig().withAdListener(adListener).build();
                    fanInterstitialAd.loadAd(loadAdConfig);
                    break;

                case STARTAPP:
                    startAppAd = new StartAppAd(activity);
                    startAppAd.loadAd(new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull Ad ad) {
                            Log.d(TAG, "Startapp Interstitial Ad loaded");
                        }

                        @Override
                        public void onFailedToReceiveAd(@Nullable Ad ad) {
                            Log.d(TAG, "Failed to load Startapp Interstitial Ad");
                        }
                    });
                    Log.d(TAG, "load StartApp as backup Ad");
                    break;

                case UNITY:
                    unityAdsLoadListener = new IUnityAdsLoadListener() {
                        @Override
                        public void onUnityAdsAdLoaded(String placementId) {
                            Log.d(TAG, "unity interstitial ad loaded");
                        }

                        @Override
                        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                            Log.e(TAG, "Unity Ads failed to load ad : " + unityInterstitialId + " : error : " + message);
                        }
                    };
                    try {
                        UnityAds.load(unityInterstitialId, unityAdsLoadListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    maxInterstitialAd = new MaxInterstitialAd(appLovinInterstitialId, activity);
                    maxInterstitialAd.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            retryAttempt = 0;
                            Log.d(TAG, "AppLovin Interstitial Ad loaded...");
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {
                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {
                            maxInterstitialAd.loadAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            retryAttempt++;
                            long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));
                            new Handler().postDelayed(() -> maxInterstitialAd.loadAd(), delayMillis);
                            Log.d(TAG, "failed to load AppLovin Interstitial");
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {
                            maxInterstitialAd.loadAd();
                        }
                    });

                    // Load the first ad
                    maxInterstitialAd.loadAd();
                    break;

                case APPLOVIN_DISCOVERY:
                    AdRequest.Builder builder = new AdRequest.Builder();
                    Bundle interstitialExtras = new Bundle();
                    interstitialExtras.putString("zone_id", appLovinInterstitialZoneId);
                    builder.addCustomEventExtrasBundle(AppLovinCustomEventInterstitial.class, interstitialExtras);
                    AppLovinSdk.getInstance(activity).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
                        @Override
                        public void adReceived(AppLovinAd ad) {
                            appLovinAd = ad;
                        }

                        @Override
                        public void failedToReceiveAd(int errorCode) {
                        }
                    });
                    appLovinInterstitialAdDialog = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    IronSource.setLevelPlayInterstitialListener(new LevelPlayInterstitialListener() {
                        @Override
                        public void onAdReady(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdReady");
                        }

                        @Override
                        public void onAdLoadFailed(IronSourceError ironSourceError) {
                            Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
                        }

                        @Override
                        public void onAdOpened(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdOpened");
                        }

                        @Override
                        public void onAdShowSucceeded(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdShowSucceeded");
                        }

                        @Override
                        public void onAdShowFailed(IronSourceError ironSourceError, AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
                        }

                        @Override
                        public void onAdClicked(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdClicked");
                        }

                        @Override
                        public void onAdClosed(AdInfo adInfo) {
                            Log.d(TAG, "onInterstitialAdClosed");
                            loadInterstitialAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }
                    });
                    IronSource.loadInterstitial();
                    break;

                case WORTISE:
                    wortiseInterstitialAd = new com.wortise.ads.interstitial.InterstitialAd(activity, wortiseInterstitialId);
                    wortiseInterstitialAd.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
                        @Override
                        public void onInterstitialImpression(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                        }

                        @Override
                        public void onInterstitialFailedToShow(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {

                        }

                        @Override
                        public void onInterstitialFailedToLoad(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {
                            Log.d(TAG, "[Wortise] [Backup] Failed to load Interstitial Ad");
                        }

                        @Override
                        public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                        }

                        @Override
                        public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                            loadInterstitialAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                            Log.d(TAG, "[Wortise] [Backup] Interstitial Ad dismissed");
                        }

                        @Override
                        public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                            Log.d(TAG, "[Wortise] [Backup] Interstitial Ad loaded");
                        }

                        @Override
                        public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                        }
                    });
                    wortiseInterstitialAd.loadAd();
                    break;

                case PANGLE:
                    PAGInterstitialAd.loadAd(pangleInterstitialId, new PAGInterstitialRequest(), new PAGInterstitialAdLoadListener() {
                        @Override
                        public void onAdLoaded(PAGInterstitialAd interstitialAd) {
                            pangleInterstitialAd = interstitialAd;
                            pangleInterstitialAd.setAdInteractionListener(new PAGInterstitialAdInteractionListener() {
                                @Override
                                public void onAdShowed() {

                                }

                                @Override
                                public void onAdClicked() {

                                }

                                @Override
                                public void onAdDismissed() {
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                    Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Interstitial Ad Dismissed");
                                }
                            });
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Interstitial Ad Loaded");
                        }

                        @Override
                        public void onError(int code, String message) {
                            pangleInterstitialAd = null;
                            Log.d(TAG, "[" + backupAdNetwork + "] [backup] " + "Failed: " + code + " : " + message);
                        }
                    });
                    break;

                case HUAWEI:
                    huaweiInterstitialAd = new com.huawei.hms.ads.InterstitialAd(activity);
                    huaweiInterstitialAd.setAdId(huaweiInterstitialId);
                    AdParam.Builder AdParamBuilder = new AdParam.Builder();
                    BiddingParam biddingParam = new BiddingParam();
                    AdParamBuilder.addBiddingParamMap(huaweiInterstitialId, biddingParam);
                    AdParamBuilder.setTMax(500);
                    huaweiInterstitialAd.loadAd(AdParamBuilder.build());
                    huaweiInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Called when an ad is loaded successfully.
                            Log.d(TAG, "Huawei Interstitial Ad Loaded");
                        }

                        @Override
                        public void onAdFailed(int errorCode) {
                            // Called when an ad fails to be loaded.
                            huaweiInterstitialAd = null;
                            Log.d(TAG, "Failed to Load Huawei Interstitial");
                        }

                        @Override
                        public void onAdClosed() {
                            // Called when an ad is closed.
                            loadInterstitialAd();
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                            Log.d(TAG, "Huawei Interstitial Ad Closed");
                        }

                        @Override
                        public void onAdClicked() {
                            // Called when an ad is clicked.
                        }

                        @Override
                        public void onAdLeave() {
                            // Called when an ad leaves an app.
                        }

                        @Override
                        public void onAdOpened() {
                            // Called when an ad is opened.
                        }
                    });
                    break;

                case YANDEX:
                    yandexInterstitialAdLoader = new InterstitialAdLoader(activity);
                    yandexInterstitialAdLoader.setAdLoadListener(new InterstitialAdLoadListener() {
                        @Override
                        public void onAdLoaded(@NonNull com.yandex.mobile.ads.interstitial.InterstitialAd interstitialAd) {
                            yandexInterstitialAd = interstitialAd;
                            Log.d(TAG, "[backup] Yandex Interstitial Ad Loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                            Log.d(TAG, "[backup] Failed to Load Yandex Interstitial: " + adRequestError);
                            yandexInterstitialAd = null;
                        }
                    });
                    AdRequestConfiguration adRequestConfiguration = new AdRequestConfiguration.Builder(yandexInterstitialId).build();
                    yandexInterstitialAdLoader.loadAd(adRequestConfiguration);
                    break;

                case NONE:
                    //do nothing
                    break;
            }
        }
    }

    private void showInterstitialAd() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            if (counter == interval) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (adMobInterstitialAd != null) {
                            adMobInterstitialAd.show(activity);
                            Log.d(TAG, "admob interstitial not null");
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "admob interstitial null");
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerInterstitialAd != null) {
                            adManagerInterstitialAd.show(activity);
                            Log.d(TAG, "ad manager interstitial not null");
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "ad manager interstitial null");
                        }
                        break;

                    case FAN:
                    case FACEBOOK:
                        if (fanInterstitialAd != null && fanInterstitialAd.isAdLoaded()) {
                            fanInterstitialAd.show();
                            Log.d(TAG, "fan interstitial not null");
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "fan interstitial null");
                        }
                        break;

                    case STARTAPP:
                        if (startAppAd != null) {
                            //startAppAd.showAd();
                            startAppAd.showAd(new AdDisplayListener() {
                                @Override
                                public void adHidden(Ad ad) {
                                    loadInterstitialAd();
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                }

                                @Override
                                public void adDisplayed(Ad ad) {

                                }

                                @Override
                                public void adClicked(Ad ad) {

                                }

                                @Override
                                public void adNotDisplayed(Ad ad) {
                                    showBackupInterstitialAd();
                                }
                            });
                            Log.d(TAG, "startapp interstitial not null [counter] : " + counter);
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "startapp interstitial null");
                        }
                        break;

                    case UNITY:
                        IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
                            @Override
                            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                                Log.d(TAG, "unity ads show failure");
                                showBackupInterstitialAd();
                            }

                            @Override
                            public void onUnityAdsShowStart(String placementId) {

                            }

                            @Override
                            public void onUnityAdsShowClick(String placementId) {

                            }

                            @Override
                            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

                            }
                        };
                        UnityAds.show(activity, unityInterstitialId, new UnityAdsShowOptions(), showListener);
                        break;

                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (maxInterstitialAd != null && maxInterstitialAd.isReady()) {
                            Log.d(TAG, "ready : " + counter);
                            maxInterstitialAd.showAd();
                            Log.d(TAG, "show ad");
                        } else {
                            showBackupInterstitialAd();
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinInterstitialAdDialog != null) {
                            appLovinInterstitialAdDialog.showAndRender(appLovinAd);
                        }
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        if (IronSource.isInterstitialReady()) {
                            IronSource.showInterstitial(ironSourceInterstitialId);
                        } else {
                            showBackupInterstitialAd();
                        }
                        break;

                    case WORTISE:
                        if (wortiseInterstitialAd != null && wortiseInterstitialAd.isAvailable()) {
                            wortiseInterstitialAd.showAd();
                        } else {
                            showBackupInterstitialAd();
                        }
                        break;

                    case PANGLE:
                        if (pangleInterstitialAd != null) {
                            pangleInterstitialAd.show(activity);
                            Log.d(TAG, "Show Pangle Interstitial Ad");
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "Pangle Interstitial Null");
                        }
                        break;

                    case HUAWEI:
                        if (huaweiInterstitialAd != null) {
                            if (huaweiInterstitialAd.isLoaded()) {
                                huaweiInterstitialAd.show(activity);
                                Log.d(TAG, "Huawei Interstitial show");
                            } else {
                                showBackupInterstitialAd();
                                Log.d(TAG, "Huawei Interstitial is not loaded");
                            }
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "Huawei Interstitial is null");
                        }
                        break;

                    case YANDEX:
                        if (yandexInterstitialAd != null) {
                            yandexInterstitialAd.setAdEventListener(new InterstitialAdEventListener() {
                                @Override
                                public void onAdShown() {
                                }

                                @Override
                                public void onAdFailedToShow(@NonNull final com.yandex.mobile.ads.common.AdError adError) {
                                    showBackupInterstitialAd();
                                }

                                @Override
                                public void onAdDismissed() {
                                    if (yandexInterstitialAd != null) {
                                        yandexInterstitialAd.setAdEventListener(null);
                                        yandexInterstitialAd = null;
                                    }
                                    if (withListener) {
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }
                                    // Now you can preload the next interstitial ad.
                                    loadInterstitialAd();
                                }

                                @Override
                                public void onAdClicked() {
                                }

                                @Override
                                public void onAdImpression(@Nullable final ImpressionData impressionData) {
                                }
                            });
                            yandexInterstitialAd.show(activity);
                        } else {
                            showBackupInterstitialAd();
                            Log.d(TAG, "Huawei Interstitial is null");
                        }
                        break;

                    default:
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                        break;
                }
                counter = 1;
            } else {
                counter++;
            }
            Log.d(TAG, "Current counter : " + counter);
        }
    }

    private void showBackupInterstitialAd() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            Log.d(TAG, "Show Backup Interstitial Ad [" + backupAdNetwork.toUpperCase() + "]");
            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    if (adMobInterstitialAd != null) {
                        adMobInterstitialAd.show(activity);
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (adManagerInterstitialAd != null) {
                        adManagerInterstitialAd.show(activity);
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case FAN:
                case FACEBOOK:
                    if (fanInterstitialAd != null && fanInterstitialAd.isAdLoaded()) {
                        fanInterstitialAd.show();
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case STARTAPP:
                    if (startAppAd != null) {
                        //startAppAd.showAd();
                        startAppAd.showAd(new AdDisplayListener() {
                            @Override
                            public void adHidden(Ad ad) {
                                loadBackupInterstitialAd();
                                if (withListener) {
                                    onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                }
                            }

                            @Override
                            public void adDisplayed(Ad ad) {

                            }

                            @Override
                            public void adClicked(Ad ad) {

                            }

                            @Override
                            public void adNotDisplayed(Ad ad) {
                                if (withListener) {
                                    onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                }
                            }
                        });
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case UNITY:
                    IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
                        @Override
                        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                            Log.d(TAG, "unity ads show failure");
                            if (withListener) {
                                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                            }
                        }

                        @Override
                        public void onUnityAdsShowStart(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowClick(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

                        }
                    };
                    UnityAds.show(activity, unityInterstitialId, new UnityAdsShowOptions(), showListener);
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    if (maxInterstitialAd != null && maxInterstitialAd.isReady()) {
                        maxInterstitialAd.showAd();
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case APPLOVIN_DISCOVERY:
                    if (appLovinInterstitialAdDialog != null) {
                        appLovinInterstitialAdDialog.showAndRender(appLovinAd);
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    if (IronSource.isInterstitialReady()) {
                        IronSource.showInterstitial(ironSourceInterstitialId);
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case WORTISE:
                    if (wortiseInterstitialAd != null && wortiseInterstitialAd.isAvailable()) {
                        wortiseInterstitialAd.showAd();
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case PANGLE:
                    if (pangleInterstitialAd != null) {
                        pangleInterstitialAd.show(activity);
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case HUAWEI:
                    if (huaweiInterstitialAd != null) {
                        if (huaweiInterstitialAd.isLoaded()) {
                            huaweiInterstitialAd.show(activity);
                        }
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case YANDEX:
                    if (yandexInterstitialAd != null) {
                        yandexInterstitialAd.setAdEventListener(new InterstitialAdEventListener() {
                            @Override
                            public void onAdShown() {
                            }

                            @Override
                            public void onAdFailedToShow(@NonNull final com.yandex.mobile.ads.common.AdError adError) {
                                if (withListener) {
                                    onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                }
                            }

                            @Override
                            public void onAdDismissed() {
                                if (yandexInterstitialAd != null) {
                                    yandexInterstitialAd.setAdEventListener(null);
                                    yandexInterstitialAd = null;
                                }
                                if (withListener) {
                                    onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                }
                                loadInterstitialAd();
                            }

                            @Override
                            public void onAdClicked() {
                            }

                            @Override
                            public void onAdImpression(@Nullable final ImpressionData impressionData) {
                            }
                        });
                        yandexInterstitialAd.show(activity);
                    } else {
                        if (withListener) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                    break;

                case NONE:
                    if (withListener) {
                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                    }
                    break;
            }
        }
    }

}
