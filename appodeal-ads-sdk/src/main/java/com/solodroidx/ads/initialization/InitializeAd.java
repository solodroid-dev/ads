package com.solodroidx.ads.initialization;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPODEAL;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeMediaViewContentType;
import com.appodeal.ads.initializing.ApdInitializationCallback;
import com.appodeal.ads.initializing.ApdInitializationError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.solodroidx.ads.listener.OnInitializeSuccessListener;

import java.util.List;
import java.util.Map;

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
    private boolean debug = true;

    public InitializeAd(Activity activity) {
        this.activity = activity;
    }

    public InitializeAd build() {
        initAds();
        initBackupAds();
        return this;
    }

//    public InitializeAd build(OnInitializeSuccessListener onInitializeSuccessListener) {
//        initAds(onInitializeSuccessListener);
//        //initBackupAds();
//        return this;
//    }

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
        this.wortiseAppId = wortiseAppId;
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

                case APPODEAL:
                    if (debug) {
                        Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.verbose);
                        Appodeal.setTesting(true);
                    }
                    //Appodeal.setAutoCache(Appodeal.INTERSTITIAL | Appodeal.BANNER_VIEW | Appodeal.NATIVE | Appodeal.REWARDED_VIDEO, false);
                    Appodeal.setPreferredNativeContentType(NativeMediaViewContentType.Auto);
                    Appodeal.initialize(activity, appodealAppKey,
                            Appodeal.INTERSTITIAL |
                                    Appodeal.BANNER_VIEW |
                                    Appodeal.NATIVE |
                                    Appodeal.REWARDED_VIDEO, errors -> {
                                // Appodeal initialization finished
                                String initResult = (errors == null || errors.isEmpty()) ? "successfully" : "with " + errors.size() + " errors";
                                Toast.makeText(activity, "Appodeal initialized " + initResult, Toast.LENGTH_SHORT).show();
                                if (errors != null) {
                                    for (ApdInitializationError error : errors) {
                                        Log.e(TAG, "onInitializationFinished: ", error);
                                    }
                                }
                            });
                    //Appodeal.cache(activity, Appodeal.NATIVE, 5);
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

                case APPODEAL:
                    if (debug) {
                        Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.verbose);
                        Appodeal.setTesting(true);
                    }
                    Appodeal.initialize(activity, appodealAppKey, Appodeal.INTERSTITIAL | Appodeal.BANNER_VIEW | Appodeal.NATIVE | Appodeal.REWARDED_VIDEO, new ApdInitializationCallback() {
                        @Override
                        public void onInitializationFinished(@Nullable List<ApdInitializationError> errors) {
                            // Appodeal initialization finished
                        }
                    });
                    break;

                case NONE:
                    //do nothing
                    break;
            }
            Log.d(TAG, "[" + backupAdNetwork + "] is selected as Backup Ads");
        }
    }

    private void initAds(OnInitializeSuccessListener onInitializeSuccessListener) {
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

                case APPODEAL:
                    if (debug) {
                        Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.verbose);
                        Appodeal.setTesting(true);
                    }
                    //Appodeal.setAutoCache(Appodeal.NATIVE, false);
                    Appodeal.setPreferredNativeContentType(NativeMediaViewContentType.Auto);
                    Appodeal.initialize(activity, appodealAppKey,
                            Appodeal.INTERSTITIAL |
                                    Appodeal.BANNER_VIEW |
                                    Appodeal.NATIVE |
                                    Appodeal.REWARDED_VIDEO, errors -> {
                                // Appodeal initialization finished
                                String initResult = (errors == null || errors.isEmpty()) ? "successfully" : "with " + errors.size() + " errors";
                                Toast.makeText(activity, "Appodeal initialized " + initResult, Toast.LENGTH_SHORT).show();
                                if (errors != null) {
                                    for (ApdInitializationError error : errors) {
                                        Log.e(TAG, "onInitializationFinished: ", error);
                                    }
                                }
                                onInitializeSuccessListener.onSuccess();
                            });
                    //Appodeal.cache(activity, Appodeal.NATIVE, 5);
                    break;
            }
            Log.d(TAG, "[" + adNetwork + "] is selected as Primary Ads");
        }
    }

}

