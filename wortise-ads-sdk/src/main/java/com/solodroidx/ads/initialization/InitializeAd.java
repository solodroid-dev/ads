package com.solodroidx.ads.initialization;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;
import static com.solodroidx.ads.util.Constant.WORTISE;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.solodroidx.ads.util.Tools;
import com.wortise.ads.AdSettings;
import com.wortise.ads.WortiseSdk;

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
    private String appodealAppKey = "";
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
                    break;

                case WORTISE:
                    WortiseSdk.initialize(activity, wortiseAppId, () -> {
                        // This listener will be invoked when the initialization finishes
                        return Unit.INSTANCE;
                    });
                    AdSettings.setTestEnabled(debug);
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
                    break;

                case WORTISE:
                    WortiseSdk.initialize(activity, wortiseAppId, () -> {
                        // This listener will be invoked when the initialization finishes
                        return Unit.INSTANCE;
                    });
                    AdSettings.setTestEnabled(debug);
                    break;

                case NONE:
                    //do nothing
                    break;
            }
            Log.d(TAG, "[" + backupAdNetwork + "] is selected as Backup Ads");
        }
    }

}

