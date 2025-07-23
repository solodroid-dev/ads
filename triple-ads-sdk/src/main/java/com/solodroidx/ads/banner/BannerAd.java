package com.solodroidx.ads.banner;

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
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.applovin.adview.AppLovinAdView;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdkUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.solodroidx.ads.R;
import com.solodroidx.ads.helper.AppLovinCustomEventBanner;
import com.solodroidx.ads.util.Tools;

@SuppressWarnings("deprecation")
public class BannerAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    private AdView adView;
    private AdManagerAdView adManagerAdView;
    private com.facebook.ads.AdView fanAdView;
    private AppLovinAdView appLovinAdView;

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

                case FAN:
                case FACEBOOK:
                    fanAdView = new com.facebook.ads.AdView(activity, fanBannerId, AdSize.BANNER_HEIGHT_50);
                    RelativeLayout fanAdViewContainer = activity.findViewById(R.id.fan_banner_view_container);
                    fanAdViewContainer.addView(fanAdView);
                    com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                        @Override
                        public void onError(Ad ad, com.facebook.ads.AdError adError) {
                            fanAdViewContainer.setVisibility(View.GONE);
                            loadBackupBannerAd();
                            Log.d(TAG, "Error load FAN : " + adError.getErrorMessage());
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            fanAdViewContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    };
                    com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = fanAdView.buildLoadAdConfig().withAdListener(adListener).build();
                    fanAdView.loadAd(loadAdConfig);
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    RelativeLayout appLovinAdView = activity.findViewById(R.id.applovin_banner_view_container);
                    MaxAdView maxAdView = new MaxAdView(appLovinBannerId, activity);
                    maxAdView.setListener(new MaxAdViewAdListener() {
                        @Override
                        public void onAdExpanded(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdCollapsed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            appLovinAdView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            appLovinAdView.setVisibility(View.GONE);
                            loadBackupBannerAd();
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {

                        }
                    });

                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.applovin_banner_height);
                    maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
                    if (darkTheme) {
                        maxAdView.setBackgroundColor(activity.getResources().getColor(R.color.color_native_background_dark));
                    } else {
                        maxAdView.setBackgroundColor(activity.getResources().getColor(R.color.color_native_background_light));
                    }
                    appLovinAdView.addView(maxAdView);
                    maxAdView.loadAd();
                    Log.d(TAG, adNetwork + " Banner Ad unit Id : " + appLovinBannerId);
                    break;

                case APPLOVIN_DISCOVERY:
                    RelativeLayout appLovinDiscoveryAdView = activity.findViewById(R.id.applovin_discovery_banner_view_container);
                    AdRequest.Builder builder = new AdRequest.Builder();
                    Bundle bannerExtras = new Bundle();
                    bannerExtras.putString("zone_id", appLovinBannerZoneId);
                    builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                    boolean isTablet2 = AppLovinSdkUtils.isTablet(activity);
                    AppLovinAdSize adSize = isTablet2 ? AppLovinAdSize.LEADER : AppLovinAdSize.BANNER;
                    this.appLovinAdView = new AppLovinAdView(adSize, activity);
                    this.appLovinAdView.setAdLoadListener(new AppLovinAdLoadListener() {
                        @Override
                        public void adReceived(AppLovinAd ad) {
                            appLovinDiscoveryAdView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void failedToReceiveAd(int errorCode) {
                            appLovinDiscoveryAdView.setVisibility(View.GONE);
                            loadBackupBannerAd();
                        }
                    });
                    appLovinDiscoveryAdView.addView(this.appLovinAdView);
                    this.appLovinAdView.loadNextAd();
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

                case FAN:
                case FACEBOOK:
                    fanAdView = new com.facebook.ads.AdView(activity, fanBannerId, AdSize.BANNER_HEIGHT_50);
                    RelativeLayout fanAdViewContainer = activity.findViewById(R.id.fan_banner_view_container);
                    fanAdViewContainer.addView(fanAdView);
                    com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                        @Override
                        public void onError(Ad ad, com.facebook.ads.AdError adError) {
                            fanAdViewContainer.setVisibility(View.GONE);
                            Log.d(TAG, "Error load FAN : " + adError.getErrorMessage());
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            fanAdViewContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    };
                    com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = fanAdView.buildLoadAdConfig().withAdListener(adListener).build();
                    fanAdView.loadAd(loadAdConfig);
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    RelativeLayout appLovinAdView = activity.findViewById(R.id.applovin_banner_view_container);
                    MaxAdView maxAdView = new MaxAdView(appLovinBannerId, activity);
                    maxAdView.setListener(new MaxAdViewAdListener() {
                        @Override
                        public void onAdExpanded(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdCollapsed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoaded(@NonNull MaxAd ad) {
                            appLovinAdView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAdDisplayed(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdClicked(@NonNull MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(@NonNull String adUnitId, @NonNull MaxError error) {
                            appLovinAdView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull MaxAd ad, @NonNull MaxError error) {

                        }
                    });

                    int width = ViewGroup.LayoutParams.MATCH_PARENT;
                    int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.applovin_banner_height);
                    maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
                    if (darkTheme) {
                        maxAdView.setBackgroundColor(activity.getResources().getColor(R.color.color_native_background_dark));
                    } else {
                        maxAdView.setBackgroundColor(activity.getResources().getColor(R.color.color_native_background_light));
                    }
                    appLovinAdView.addView(maxAdView);
                    maxAdView.loadAd();
                    Log.d(TAG, adNetwork + " Banner Ad unit Id : " + appLovinBannerId);
                    break;

                case APPLOVIN_DISCOVERY:
                    RelativeLayout appLovinDiscoveryAdView = activity.findViewById(R.id.applovin_discovery_banner_view_container);
                    AdRequest.Builder builder = new AdRequest.Builder();
                    Bundle bannerExtras = new Bundle();
                    bannerExtras.putString("zone_id", appLovinBannerZoneId);
                    builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                    boolean isTablet2 = AppLovinSdkUtils.isTablet(activity);
                    AppLovinAdSize adSize = isTablet2 ? AppLovinAdSize.LEADER : AppLovinAdSize.BANNER;
                    this.appLovinAdView = new AppLovinAdView(adSize, activity);
                    this.appLovinAdView.setAdLoadListener(new AppLovinAdLoadListener() {
                        @Override
                        public void adReceived(AppLovinAd ad) {
                            appLovinDiscoveryAdView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void failedToReceiveAd(int errorCode) {
                            appLovinDiscoveryAdView.setVisibility(View.GONE);
                        }
                    });
                    appLovinDiscoveryAdView.addView(this.appLovinAdView);
                    this.appLovinAdView.loadNextAd();
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

    }

}
