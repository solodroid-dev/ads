package com.solodroidx.ads.initialization;

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
import android.util.Log;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkInitializationConfiguration;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.ironsource.mediationsdk.IronSource;
import com.solodroidx.ads.helper.AudienceNetworkInitializeHelper;
import com.solodroidx.ads.util.Tools;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;

import java.util.Map;

import kotlin.Unit;

@SuppressWarnings("deprecation")
public class InitializeAd {

    private static final String TAG = "AdNetwork";
    Activity activity;
    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobAppId = "";
    private String startappAppId = "0";
    private String unityGameId = "";
    private String appLovinSdkKey = "";
    private String mopubBannerId = "";
    private String ironSourceAppKey = "";
    private String wortiseAppId = "";
    private String pangleAppId = "";
    private String appodealAppKey = "";;
    private String authorizationKey = "";
    private boolean debug = true;

    public InitializeAd(Activity activity) {
        this.activity = activity;
    }

    public InitializeAd build() {
        initAds();
        initBackupAds();
        return this;
    }

    public InitializeAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public InitializeAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public InitializeAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public InitializeAd setAdMobAppId(String adMobAppId) {
        this.adMobAppId = adMobAppId;
        return this;
    }

    public InitializeAd setStartappAppId(String startappAppId) {
        this.startappAppId = startappAppId;
        return this;
    }

    public InitializeAd setUnityGameId(String unityGameId) {
        this.unityGameId = unityGameId;
        return this;
    }

    public InitializeAd setAppLovinSdkKey(String appLovinSdkKey) {
        this.appLovinSdkKey = appLovinSdkKey;
        return this;
    }

    public InitializeAd setMopubBannerId(String mopubBannerId) {
        this.mopubBannerId = mopubBannerId;
        return this;
    }

    public InitializeAd setIronSourceAppKey(String ironSourceAppKey) {
        this.ironSourceAppKey = ironSourceAppKey;
        return this;
    }

    public InitializeAd setWortiseAppId(String wortiseAppId) {
        return this;
    }

    public InitializeAd setWortiseAppId(String wortiseAppId, String authorizationKey) {
        this.authorizationKey = authorizationKey;
        if (this.authorizationKey.equals(Tools.decode("VkZkME5sWnJTakpsVm05NVdWWlZlVkpXVGtKYVJFcFNXa1JKTldWWFVraGlTSEJoVlhwR2IxZHJhRTVrUjAxNVZXNUtOZz09"))) {
            this.wortiseAppId = wortiseAppId;
        }
        return this;
    }

    public InitializeAd setPangleAppId(String pangleAppId) {
        this.pangleAppId = pangleAppId;
        return this;
    }

    public InitializeAd setAppodealAppKey(String appodealAppKey) {
        this.appodealAppKey = appodealAppKey;
        return this;
    }

    public InitializeAd setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    private void initAds() {
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (adNetwork) {
                case ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_ADMOB:
                case FAN_BIDDING_AD_MANAGER:
                    MobileAds.initialize(activity, initializationStatus -> {
                        Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                        for (String adapterClass : statusMap.keySet()) {
                            AdapterStatus adapterStatus = statusMap.get(adapterClass);
                            assert adapterStatus != null;
                            Log.d(TAG, String.format("Adapter name: %s, Description: %s, Latency: %d", adapterClass, adapterStatus.getDescription(), adapterStatus.getLatency()));
                        }
                    });
                    AudienceNetworkInitializeHelper.initializeAd(activity, debug);
                    break;

                case FAN:
                case FACEBOOK:
                    AudienceNetworkInitializeHelper.initializeAd(activity, debug);
                    break;

                case STARTAPP:
                    StartAppSDK.init(activity, startappAppId, false);
                    StartAppSDK.setTestAdsEnabled(debug);
                    StartAppAd.disableSplash();
                    StartAppSDK.setUserConsent(activity, "pas", System.currentTimeMillis(), true);
                    break;

                case UNITY:
                    UnityAds.initialize(activity, unityGameId, debug, new IUnityAdsInitializationListener() {
                        @Override
                        public void onInitializationComplete() {
                            Log.d(TAG, "Unity is successfully initialized. with ID : " + unityGameId);
                        }

                        @Override
                        public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                            Log.d(TAG, "Unity Failed to Initialize : " + message);
                        }
                    });
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    AppLovinSdkInitializationConfiguration initConfigAppLovinMax = AppLovinSdkInitializationConfiguration.builder(appLovinSdkKey, activity)
                            .setMediationProvider(AppLovinMediationProvider.MAX)
                            .build();
                    AppLovinSdk.getInstance(activity).initialize(initConfigAppLovinMax, sdkConfig -> {

                    });
                    AudienceNetworkInitializeHelper.initialize(activity);
                    break;

                case APPLOVIN_DISCOVERY:
                    AppLovinSdkInitializationConfiguration initConfigAppLovinDiscovery = AppLovinSdkInitializationConfiguration.builder(appLovinSdkKey, activity).build();
                    AppLovinSdk.getInstance(activity).initialize(initConfigAppLovinDiscovery, sdkConfig -> {
                    });
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    String advertisingId = IronSource.getAdvertiserId(activity);
                    IronSource.setUserId(advertisingId);
                    IronSource.init(activity, ironSourceAppKey, () -> {
                        Log.d(TAG, "[" + adNetwork + "] initialize complete");
                    });
                    break;
            }
            Log.d(TAG, "[" + adNetwork + "] is selected as Primary Ads");
        }
    }

    private void initBackupAds() {
        if (adStatus.equals(AD_STATUS_ON)) {
            switch (backupAdNetwork) {
                case ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_ADMOB:
                case FAN_BIDDING_AD_MANAGER:
                    MobileAds.initialize(activity, initializationStatus -> {
                        Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                        for (String adapterClass : statusMap.keySet()) {
                            AdapterStatus adapterStatus = statusMap.get(adapterClass);
                            assert adapterStatus != null;
                            Log.d(TAG, String.format("Adapter name: %s, Description: %s, Latency: %d", adapterClass, adapterStatus.getDescription(), adapterStatus.getLatency()));
                        }
                    });
                    AudienceNetworkInitializeHelper.initialize(activity);
                    break;

                case FAN:
                case FACEBOOK:
                    AudienceNetworkInitializeHelper.initializeAd(activity, debug);
                    break;

                case STARTAPP:
                    StartAppSDK.init(activity, startappAppId, false);
                    StartAppSDK.setTestAdsEnabled(debug);
                    StartAppAd.disableSplash();
                    StartAppSDK.setUserConsent(activity, "pas", System.currentTimeMillis(), true);
                    break;

                case UNITY:
                    UnityAds.initialize(activity, unityGameId, debug, new IUnityAdsInitializationListener() {
                        @Override
                        public void onInitializationComplete() {
                            Log.d(TAG, "Unity is successfully initialized. with ID : " + unityGameId);
                        }

                        @Override
                        public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                            Log.d(TAG, "Unity Failed to Initialize : " + message);
                        }
                    });
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    AppLovinSdkInitializationConfiguration initConfigAppLovinMax = AppLovinSdkInitializationConfiguration.builder(appLovinSdkKey, activity)
                            .setMediationProvider(AppLovinMediationProvider.MAX)
                            .build();
                    AppLovinSdk.getInstance(activity).initialize(initConfigAppLovinMax, sdkConfig -> {

                    });
                    AudienceNetworkInitializeHelper.initialize(activity);
                    break;

                case APPLOVIN_DISCOVERY:
                    AppLovinSdkInitializationConfiguration initConfigAppLovinDiscovery = AppLovinSdkInitializationConfiguration.builder(appLovinSdkKey, activity).build();
                    AppLovinSdk.getInstance(activity).initialize(initConfigAppLovinDiscovery, sdkConfig -> {
                    });
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    String advertisingId = IronSource.getAdvertiserId(activity);
                    IronSource.setUserId(advertisingId);
                    IronSource.init(activity, ironSourceAppKey, () -> {
                        Log.d(TAG, "[" + adNetwork + "] initialize complete");
                    });
                    break;

                case NONE:
                    //do nothing
                    break;
            }
            Log.d(TAG, "[" + backupAdNetwork + "] is selected as Backup Ads");
        }
    }

}

