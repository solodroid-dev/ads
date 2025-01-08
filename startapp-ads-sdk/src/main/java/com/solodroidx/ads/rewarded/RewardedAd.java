package com.solodroidx.ads.rewarded;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.STARTAPP;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.solodroidx.ads.listener.OnRewardedAdCompleteListener;
import com.solodroidx.ads.listener.OnRewardedAdDismissedListener;
import com.solodroidx.ads.listener.OnRewardedAdErrorListener;
import com.solodroidx.ads.util.Tools;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

@SuppressWarnings("deprecation")
public class RewardedAd {

    private static final String TAG = "SoloRewarded";
    private final Activity activity;
    private com.google.android.gms.ads.rewarded.RewardedAd adMobRewardedAd;
    private com.google.android.gms.ads.rewarded.RewardedAd adManagerRewardedAd;
    private StartAppAd startAppAd;
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

    public RewardedAd build(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        loadRewardedAd(onComplete, onDismiss);
        return this;
    }

    public RewardedAd show(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
        showRewardedAd(onComplete, onDismiss, onError);
        return this;
    }

    public RewardedAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public RewardedAd setMainAds(String mainAds) {
        this.mainAds = mainAds;
        return this;
    }

    public RewardedAd setBackupAds(String backupAds) {
        this.backupAds = backupAds;
        return this;
    }

    public RewardedAd setAdMobRewardedId(String adMobRewardedId) {
        this.adMobRewardedId = adMobRewardedId;
        return this;
    }

    public RewardedAd setAdManagerRewardedId(String adManagerRewardedId) {
        this.adManagerRewardedId = adManagerRewardedId;
        return this;
    }

    public RewardedAd setFanRewardedId(String fanRewardedId) {
        this.fanRewardedId = fanRewardedId;
        return this;
    }

    public RewardedAd setUnityRewardedId(String unityRewardedId) {
        this.unityRewardedId = unityRewardedId;
        return this;
    }

    public RewardedAd setApplovinMaxRewardedId(String applovinMaxRewardedId) {
        this.applovinMaxRewardedId = applovinMaxRewardedId;
        return this;
    }

    public RewardedAd setApplovinDiscRewardedZoneId(String applovinDiscRewardedZoneId) {
        this.applovinDiscRewardedZoneId = applovinDiscRewardedZoneId;
        return this;
    }

    public RewardedAd setIronSourceRewardedId(String ironSourceRewardedId) {
        this.ironSourceRewardedId = ironSourceRewardedId;
        return this;
    }

    public RewardedAd setWortiseRewardedId(String wortiseRewardedId) {
        this.wortiseRewardedId = wortiseRewardedId;
        return this;
    }

    public RewardedAd setAlienAdsRewardedId(String alienAdsRewardedId) {
        this.alienAdsRewardedId = alienAdsRewardedId;
        return this;
    }

    public RewardedAd setPangleRewardedId(String pangleRewardedId) {
        this.pangleRewardedId = pangleRewardedId;
        return this;
    }

    public RewardedAd setHuaweiRewardedId(String huaweiRewardedId) {
        this.huaweiRewardedId = huaweiRewardedId;
        return this;
    }

    public RewardedAd setYandexRewardedId(String yandexRewardedId) {
        this.yandexRewardedId = yandexRewardedId;
        return this;
    }

    public RewardedAd setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public RewardedAd setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public void loadRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (mainAds) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    com.google.android.gms.ads.rewarded.RewardedAd.load(activity, adMobRewardedId, Tools.getAdRequest(activity, legacyGDPR), new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.rewarded.RewardedAd ad) {
                            adMobRewardedAd = ad;
                            adMobRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    adMobRewardedAd = null;
                                    loadRewardedAd(onComplete, onDismiss);
                                    onDismiss.onRewardedAdDismissed();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    adMobRewardedAd = null;
                                }
                            });
                            Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            adMobRewardedAd = null;
                            loadRewardedBackupAd(onComplete, onDismiss);
                            Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                        }
                    });
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    com.google.android.gms.ads.rewarded.RewardedAd.load(activity, adManagerRewardedId, Tools.getGoogleAdManagerRequest(), new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.rewarded.RewardedAd ad) {
                            adManagerRewardedAd = ad;
                            adManagerRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    adManagerRewardedAd = null;
                                    loadRewardedAd(onComplete, onDismiss);
                                    onDismiss.onRewardedAdDismissed();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    adManagerRewardedAd = null;
                                }
                            });
                            Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            adManagerRewardedAd = null;
                            loadRewardedBackupAd(onComplete, onDismiss);
                            Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                        }
                    });
                    break;

                case STARTAPP:
                    startAppAd = new StartAppAd(activity);
                    startAppAd.setVideoListener(() -> {
                        onComplete.onRewardedAdComplete();
                        Log.d(TAG, "[" + mainAds + "] " + "rewarded ad complete");
                    });
                    startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
                            Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                        }

                        @Override
                        public void onFailedToReceiveAd(@Nullable com.startapp.sdk.adsbase.Ad ad) {
                            loadRewardedBackupAd(onComplete, onDismiss);
                            Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad, try to load backup ad: " + backupAds);

                        }
                    });
                    break;
            }
        }
    }

    public void loadRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (backupAds) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    com.google.android.gms.ads.rewarded.RewardedAd.load(activity, adMobRewardedId, Tools.getAdRequest(activity, legacyGDPR), new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.rewarded.RewardedAd ad) {
                            adMobRewardedAd = ad;
                            adMobRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    adMobRewardedAd = null;
                                    loadRewardedAd(onComplete, onDismiss);
                                    onDismiss.onRewardedAdDismissed();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    adMobRewardedAd = null;
                                }
                            });
                            Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            adMobRewardedAd = null;
                            Log.d(TAG, "[" + backupAds + "] [backup] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                        }
                    });
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    com.google.android.gms.ads.rewarded.RewardedAd.load(activity, adManagerRewardedId, Tools.getGoogleAdManagerRequest(), new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull com.google.android.gms.ads.rewarded.RewardedAd ad) {
                            adManagerRewardedAd = ad;
                            adManagerRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    adManagerRewardedAd = null;
                                    loadRewardedAd(onComplete, onDismiss);
                                    onDismiss.onRewardedAdDismissed();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    adManagerRewardedAd = null;
                                }
                            });
                            Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            adManagerRewardedAd = null;
                            Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + loadAdError.getMessage() + ", try to load backup ad: " + backupAds);
                        }
                    });
                    break;

                case STARTAPP:
                    startAppAd = new StartAppAd(activity);
                    startAppAd.setVideoListener(() -> {
                        onComplete.onRewardedAdComplete();
                        Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad complete");
                    });
                    startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
                            Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad loaded");
                        }

                        @Override
                        public void onFailedToReceiveAd(@Nullable com.startapp.sdk.adsbase.Ad ad) {
                            Log.d(TAG, "[" + backupAds + "] [backup] " + "failed to load rewarded ad, try to load backup ad: " + backupAds);
                        }
                    });
                    break;
            }
        }
    }

    public void showRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (mainAds) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    if (adMobRewardedAd != null) {
                        adMobRewardedAd.show(activity, rewardItem -> {
                            onComplete.onRewardedAdComplete();
                            Log.d(TAG, "The user earned the reward.");
                        });
                    } else {
                        showRewardedBackupAd(onComplete, onDismiss, onError);
                    }
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (adManagerRewardedAd != null) {
                        adManagerRewardedAd.show(activity, rewardItem -> {
                            onComplete.onRewardedAdComplete();
                            Log.d(TAG, "The user earned the reward.");
                        });
                    } else {
                        showRewardedBackupAd(onComplete, onDismiss, onError);
                    }
                    break;

                case STARTAPP:
                    if (startAppAd != null) {
                        startAppAd.showAd(new AdDisplayListener() {
                            @Override
                            public void adHidden(Ad ad) {
                                loadRewardedAd(onComplete, onDismiss);
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad closed");
                            }

                            @Override
                            public void adDisplayed(Ad ad) {

                            }

                            @Override
                            public void adClicked(Ad ad) {

                            }

                            @Override
                            public void adNotDisplayed(Ad ad) {

                            }
                        });
                    } else {
                        showRewardedBackupAd(onComplete, onDismiss, onError);
                    }
                    break;

                default:
                    onError.onRewardedAdError();
                    break;
            }
        }

    }

    public void showRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (backupAds) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    if (adMobRewardedAd != null) {
                        adMobRewardedAd.show(activity, rewardItem -> {
                            onComplete.onRewardedAdComplete();
                            Log.d(TAG, "The user earned the reward.");
                        });
                    } else {
                        onError.onRewardedAdError();
                    }
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (adManagerRewardedAd != null) {
                        adManagerRewardedAd.show(activity, rewardItem -> {
                            onComplete.onRewardedAdComplete();
                            Log.d(TAG, "The user earned the reward.");
                        });
                    } else {
                        onError.onRewardedAdError();
                    }
                    break;

                case STARTAPP:
                    if (startAppAd != null) {
                        startAppAd.showAd(new AdDisplayListener() {
                            @Override
                            public void adHidden(Ad ad) {
                                loadRewardedBackupAd(onComplete, onDismiss);
                                Log.d(TAG, "[" + backupAds + "] [backup] " + "rewarded ad closed");
                            }

                            @Override
                            public void adDisplayed(Ad ad) {

                            }

                            @Override
                            public void adClicked(Ad ad) {

                            }

                            @Override
                            public void adNotDisplayed(Ad ad) {

                            }
                        });
                    } else {
                        onError.onRewardedAdError();
                    }
                    break;

                default:
                    onError.onRewardedAdError();
                    break;
            }
        }

    }

    public void destroyRewardedAd() {

    }

}


