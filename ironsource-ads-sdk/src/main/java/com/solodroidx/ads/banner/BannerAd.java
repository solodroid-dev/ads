package com.solodroidx.ads.banner;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_IRONSOURCE;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.IRONSOURCE;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.LevelPlayBannerListener;
import com.solodroidx.ads.R;
import com.solodroidx.ads.util.Tools;

@SuppressWarnings("deprecation")
public class BannerAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    private AdView adView;
    private AdManagerAdView adManagerAdView;
    FrameLayout ironSourceBannerView;
    private IronSourceBannerLayout ironSourceBannerLayout;

    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobBannerId = "";
    private String googleAdManagerBannerId = "";
    private String fanBannerId = "";
    private String unityBannerId = "";
    private String appLovinBannerId = "";
    private String appLovinBannerZoneId = "";
    private String ironSourceBannerId = "";
    private String wortiseBannerId = "";
    private String alienAdsBannerId = "";
    private String pangleBannerId = "";
    private String huaweiBannerId = "";
    private String yandexBannerId = "";
    private int placementStatus = 1;
    private boolean darkTheme = false;
    private boolean legacyGDPR = false;
    private boolean collapsibleBanner = false;

    public BannerAd(Activity activity) {
        this.activity = activity;
    }

    public BannerAd build() {
        loadBannerAd();
        return this;
    }

    public BannerAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public BannerAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public BannerAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public BannerAd setAdMobBannerId(String adMobBannerId) {
        this.adMobBannerId = adMobBannerId;
        return this;
    }

    public BannerAd setGoogleAdManagerBannerId(String googleAdManagerBannerId) {
        this.googleAdManagerBannerId = googleAdManagerBannerId;
        return this;
    }

    public BannerAd setFanBannerId(String fanBannerId) {
        this.fanBannerId = fanBannerId;
        return this;
    }

    public BannerAd setUnityBannerId(String unityBannerId) {
        this.unityBannerId = unityBannerId;
        return this;
    }

    public BannerAd setAppLovinBannerId(String appLovinBannerId) {
        this.appLovinBannerId = appLovinBannerId;
        return this;
    }

    public BannerAd setAppLovinBannerZoneId(String appLovinBannerZoneId) {
        this.appLovinBannerZoneId = appLovinBannerZoneId;
        return this;
    }

    public BannerAd setIronSourceBannerId(String ironSourceBannerId) {
        this.ironSourceBannerId = ironSourceBannerId;
        return this;
    }

    public BannerAd setWortiseBannerId(String wortiseBannerId) {
        this.wortiseBannerId = wortiseBannerId;
        return this;
    }

    public BannerAd setAlienAdsBannerId(String alienAdsBannerId) {
        this.alienAdsBannerId = alienAdsBannerId;
        return this;
    }

    public BannerAd setPangleBannerId(String pangleBannerId) {
        this.pangleBannerId = pangleBannerId;
        return this;
    }

    public BannerAd setHuaweiBannerId(String huaweiBannerId) {
        this.huaweiBannerId = huaweiBannerId;
        return this;
    }

    public BannerAd setYandexBannerId(String yandexBannerId) {
        this.yandexBannerId = yandexBannerId;
        return this;
    }

    public BannerAd setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public BannerAd setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
        return this;
    }

    public BannerAd setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public BannerAd setIsCollapsibleBanner(boolean collapsibleBanner) {
        this.collapsibleBanner = collapsibleBanner;
        return this;
    }

    public void loadBannerAd() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    FrameLayout adContainerView = activity.findViewById(R.id.admob_banner_view_container);
                    adContainerView.post(() -> {
                        adView = new AdView(activity);
                        adView.setAdUnitId(adMobBannerId);
                        adContainerView.removeAllViews();
                        adContainerView.addView(adView);
                        adView.setAdSize(Tools.getAdSize(activity));
                        adView.loadAd(Tools.getAdRequest(collapsibleBanner));
                        adView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                adContainerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                adContainerView.setVisibility(View.GONE);
                                loadBackupBannerAd();
                            }
                        });
                    });
                    Log.d(TAG, adNetwork + " Banner Ad unit Id : " + adMobBannerId);
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    FrameLayout googleAdContainerView = activity.findViewById(R.id.google_ad_banner_view_container);
                    googleAdContainerView.post(() -> {
                        adManagerAdView = new AdManagerAdView(activity);
                        adManagerAdView.setAdUnitId(googleAdManagerBannerId);
                        googleAdContainerView.removeAllViews();
                        googleAdContainerView.addView(adManagerAdView);
                        adManagerAdView.setAdSize(Tools.getAdSize(activity));
                        adManagerAdView.loadAd(Tools.getGoogleAdManagerRequest());
                        adManagerAdView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                googleAdContainerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                googleAdContainerView.setVisibility(View.GONE);
                                loadBackupBannerAd();
                            }
                        });
                    });
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    ironSourceBannerView = activity.findViewById(R.id.ironsource_banner_view_container);
                    ISBannerSize size = ISBannerSize.BANNER;
                    ironSourceBannerLayout = IronSource.createBanner(activity, size);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                    ironSourceBannerView.addView(ironSourceBannerLayout, 0, layoutParams);
                    if (ironSourceBannerLayout != null) {
                        ironSourceBannerLayout.setLevelPlayBannerListener(new LevelPlayBannerListener() {
                            @Override
                            public void onAdLoaded(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdLoaded");
                                ironSourceBannerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdLoadFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onBannerAdLoadFailed" + " " + ironSourceError.getErrorMessage());
                                loadBackupBannerAd();
                            }

                            @Override
                            public void onAdClicked(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdClicked");
                            }

                            @Override
                            public void onAdLeftApplication(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdLeftApplication");
                            }

                            @Override
                            public void onAdScreenPresented(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdScreenPresented");
                            }

                            @Override
                            public void onAdScreenDismissed(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdScreenDismissed");
                            }
                        });
                        IronSource.loadBanner(ironSourceBannerLayout, ironSourceBannerId);
                    } else {
                        Log.d(TAG, "IronSource.createBanner returned null");
                    }
                    break;
            }
            Log.d(TAG, "Banner Ad is enabled");
        } else {
            Log.d(TAG, "Banner Ad is disabled");
        }
    }

    public void loadBackupBannerAd() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    FrameLayout adContainerView = activity.findViewById(R.id.admob_banner_view_container);
                    adContainerView.post(() -> {
                        adView = new AdView(activity);
                        adView.setAdUnitId(adMobBannerId);
                        adContainerView.removeAllViews();
                        adContainerView.addView(adView);
                        adView.setAdSize(Tools.getAdSize(activity));
                        adView.loadAd(Tools.getAdRequest(collapsibleBanner));
                        adView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                adContainerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                adContainerView.setVisibility(View.GONE);
                            }
                        });
                    });
                    Log.d(TAG, adNetwork + " Banner Ad unit Id : " + adMobBannerId);
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    FrameLayout googleAdContainerView = activity.findViewById(R.id.google_ad_banner_view_container);
                    googleAdContainerView.post(() -> {
                        adManagerAdView = new AdManagerAdView(activity);
                        adManagerAdView.setAdUnitId(googleAdManagerBannerId);
                        googleAdContainerView.removeAllViews();
                        googleAdContainerView.addView(adManagerAdView);
                        adManagerAdView.setAdSize(Tools.getAdSize(activity));
                        adManagerAdView.loadAd(Tools.getGoogleAdManagerRequest());
                        adManagerAdView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                googleAdContainerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                googleAdContainerView.setVisibility(View.GONE);
                            }
                        });
                    });
                    break;

                case IRONSOURCE:
                case FAN_BIDDING_IRONSOURCE:
                    ironSourceBannerView = activity.findViewById(R.id.ironsource_banner_view_container);
                    ISBannerSize size = ISBannerSize.BANNER;
                    ironSourceBannerLayout = IronSource.createBanner(activity, size);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                    ironSourceBannerView.addView(ironSourceBannerLayout, 0, layoutParams);
                    if (ironSourceBannerLayout != null) {
                        ironSourceBannerLayout.setLevelPlayBannerListener(new LevelPlayBannerListener() {
                            @Override
                            public void onAdLoaded(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdLoaded");
                                ironSourceBannerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdLoadFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onBannerAdLoadFailed" + " " + ironSourceError.getErrorMessage());
                            }

                            @Override
                            public void onAdClicked(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdClicked");
                            }

                            @Override
                            public void onAdLeftApplication(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdLeftApplication");
                            }

                            @Override
                            public void onAdScreenPresented(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdScreenPresented");
                            }

                            @Override
                            public void onAdScreenDismissed(AdInfo adInfo) {
                                Log.d(TAG, "onBannerAdScreenDismissed");
                            }
                        });
                        IronSource.loadBanner(ironSourceBannerLayout, ironSourceBannerId);
                    } else {
                        Log.d(TAG, "IronSource.createBanner returned null");
                    }
                    break;

                case NONE:
                    //do nothing
                    break;
            }
            Log.d(TAG, "Banner Ad is enabled");
        } else {
            Log.d(TAG, "Banner Ad is disabled");
        }
    }

    public void destroyAndDetachBanner() {
        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
            if (adNetwork.equals(IRONSOURCE) || backupAdNetwork.equals(IRONSOURCE)) {
                if (ironSourceBannerView != null) {
                    Log.d(TAG, "ironSource banner is not null, ready to destroy");
                    IronSource.destroyBanner(ironSourceBannerLayout);
                    ironSourceBannerView.removeView(ironSourceBannerLayout);
                } else {
                    Log.d(TAG, "ironSource banner is null");
                }
            }
        }
    }

}