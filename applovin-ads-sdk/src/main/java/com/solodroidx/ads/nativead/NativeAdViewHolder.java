package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPLOVIN;
import static com.solodroidx.ads.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroidx.ads.util.Constant.APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.applovin.adview.AppLovinAdView;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.material.card.MaterialCardView;
import com.solodroidx.ads.R;
import com.solodroidx.ads.helper.AppLovinCustomEventBanner;
import com.solodroidx.ads.util.AdManagerTemplateView;
import com.solodroidx.ads.util.Constant;
import com.solodroidx.ads.util.NativeTemplateStyle;
import com.solodroidx.ads.util.TemplateView;
import com.solodroidx.ads.util.Tools;

public class NativeAdViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "AdNetwork";
    MaterialCardView nativeAdViewContainer;

    //AdMob
    MediaView mediaView;
    TemplateView admobNativeAd;
    LinearLayout admobNativeBackground;

    //Ad Manager
    MediaView adManagerMediaView;
    AdManagerTemplateView adManagerNativeAd;
    LinearLayout adManagerNativeBackground;

    //AppLovin
    MaxAd maxAd;
    FrameLayout applovinNativeAd;
    MaxNativeAdLoader nativeAdLoader;

    LinearLayout appLovinDiscoveryMrecAd;
    AppLovinAdView appLovinAdView;

    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobNativeId = "";
    private String adManagerNativeId = "";
    private String fanNativeId = "";
    private String appLovinNativeId = "";
    private String appLovinDiscMrecZoneId = "";
    private String wortiseNativeId = "";
    private String pangleNativeId = "";
    private String huaweiNativeId = "";
    private String yandexNativeId = "";
    private int placementStatus = 1;
    private boolean darkTheme = false;
    private String nativeAdStyle = "";
    private boolean legacyGDPR = false;

    private int nativeBackgroundLight = R.color.color_native_background_light;
    private int nativeBackgroundDark = R.color.color_native_background_dark;
    private int cornerRadius = 0;
    private int strokeWidth = 0;
    private int strokeColor = android.R.color.transparent;

    public NativeAdViewHolder(View view) {
        super(view);

        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);

        //AdMob
        admobNativeAd = view.findViewById(R.id.admob_native_ad_container);
        mediaView = view.findViewById(R.id.media_view);
        admobNativeBackground = view.findViewById(R.id.background);

        //Ad Manager
        adManagerNativeAd = view.findViewById(R.id.google_ad_manager_native_ad_container);
        adManagerMediaView = view.findViewById(R.id.ad_manager_media_view);
        adManagerNativeBackground = view.findViewById(R.id.ad_manager_background);


        //AppLovin
        applovinNativeAd = view.findViewById(R.id.applovin_native_ad_container);
        appLovinDiscoveryMrecAd = view.findViewById(R.id.applovin_discovery_mrec_ad_container);

    }

    public static View setLayoutInflater(ViewGroup viewGroup, String nativeAdStyle) {
        View view;
        switch (nativeAdStyle) {
            case "news":
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_native_ad_news, viewGroup, false);
                break;
            case "radio":
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_native_ad_radio, viewGroup, false);
                break;
            case "video_small":
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_native_ad_video_small, viewGroup, false);
                break;
            case "video_large":
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_native_ad_video_large, viewGroup, false);
                break;
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_native_ad_medium, viewGroup, false);
                break;
        }
        return view;
    }

    public NativeAdViewHolder buildNativeAd(Context context) {
        loadNativeAd(context);
        return this;
    }

    public NativeAdViewHolder setPadding(Context context, int left, int top, int right, int bottom) {
        setNativeAdPadding(context, left, top, right, bottom);
        return this;
    }

    public NativeAdViewHolder setMargin(Context context, int left, int top, int right, int bottom) {
        setNativeAdMargin(context, left, top, right, bottom);
        return this;
    }

    public NativeAdViewHolder setRadius(Context context, int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setNativeAdCornerRadius(context);
        return this;
    }

    public NativeAdViewHolder setStrokeWidth(Context context, int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setNativeAdStrokeWidth(context);
        return this;
    }

    public NativeAdViewHolder setStrokeColor(Context context, int strokeColor) {
        this.strokeColor = strokeColor;
        setNativeAdStrokeColor(context);
        return this;
    }

    public NativeAdViewHolder setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public NativeAdViewHolder setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public NativeAdViewHolder setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public NativeAdViewHolder setAdMobNativeId(String adMobNativeId) {
        this.adMobNativeId = adMobNativeId;
        return this;
    }

    public NativeAdViewHolder setAdManagerNativeId(String adManagerNativeId) {
        this.adManagerNativeId = adManagerNativeId;
        return this;
    }

    public NativeAdViewHolder setFanNativeId(String fanNativeId) {
        this.fanNativeId = fanNativeId;
        return this;
    }

    public NativeAdViewHolder setAppLovinNativeId(String appLovinNativeId) {
        this.appLovinNativeId = appLovinNativeId;
        return this;
    }

    public NativeAdViewHolder setAppLovinDiscoveryMrecZoneId(String appLovinDiscMrecZoneId) {
        this.appLovinDiscMrecZoneId = appLovinDiscMrecZoneId;
        return this;
    }

    public NativeAdViewHolder setWortiseNativeId(String wortiseNativeId) {
        this.wortiseNativeId = wortiseNativeId;
        return this;
    }

    public NativeAdViewHolder setPangleNativeId(String pangleNativeId) {
        this.pangleNativeId = pangleNativeId;
        return this;
    }

    public NativeAdViewHolder setHuaweiNativeId(String huaweiNativeId) {
        this.huaweiNativeId = huaweiNativeId;
        return this;
    }

    public NativeAdViewHolder setYandexNativeId(String yandexNativeId) {
        this.yandexNativeId = yandexNativeId;
        return this;
    }

    public NativeAdViewHolder setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public NativeAdViewHolder setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
        return this;
    }

    public NativeAdViewHolder setNativeAdStyle(String nativeAdStyle) {
        this.nativeAdStyle = nativeAdStyle;
        return this;
    }

    public NativeAdViewHolder setBackgroundColor(int colorLight, int colorDark) {
        this.nativeBackgroundLight = colorLight;
        this.nativeBackgroundDark = colorDark;
        return this;
    }

    private void loadNativeAd(Context context) {
        if (adStatus.equals(AD_STATUS_ON)) {
            if (placementStatus != 0) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (admobNativeAd.getVisibility() != View.VISIBLE) {
                            AdLoader adLoader = new AdLoader.Builder(context, adMobNativeId)
                                    .forNativeAd(NativeAd -> {
                                        if (darkTheme) {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundDark));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admobNativeAd.setStyles(styles);
                                            admobNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                        } else {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundLight));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admobNativeAd.setStyles(styles);
                                            admobNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                        }
                                        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                        admobNativeAd.setNativeAd(NativeAd);
                                        admobNativeAd.setVisibility(View.VISIBLE);
                                        nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    })
                                    .withAdListener(new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                            loadBackupNativeAd(context);
                                        }
                                    })
                                    .build();
                            adLoader.loadAd(Tools.getAdRequest((Activity) context, legacyGDPR));
                        } else {
                            Log.d(TAG, "AdMob native ads has been loaded");
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerNativeAd.getVisibility() != View.VISIBLE) {
                            AdLoader adLoader = new AdLoader.Builder(context, adManagerNativeId)
                                    .forNativeAd(NativeAd -> {
                                        if (darkTheme) {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundDark));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            adManagerNativeAd.setStyles(styles);
                                            adManagerNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                        } else {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundLight));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            adManagerNativeAd.setStyles(styles);
                                            adManagerNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                        }
                                        adManagerMediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                        adManagerNativeAd.setNativeAd(NativeAd);
                                        adManagerNativeAd.setVisibility(View.VISIBLE);
                                        nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    })
                                    .withAdListener(new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                            loadBackupNativeAd(context);
                                        }
                                    })
                                    .build();
                            adLoader.loadAd(Tools.getGoogleAdManagerRequest());
                        } else {
                            Log.d(TAG, "Ad Manager Native Ad has been loaded");
                        }
                        break;

                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (applovinNativeAd.getVisibility() != View.VISIBLE) {
                            nativeAdLoader = new MaxNativeAdLoader(appLovinNativeId, context);
                            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                @Override
                                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, @NonNull final MaxAd ad) {
                                    // Clean up any pre-existing native ad to prevent memory leaks.
                                    if (maxAd != null) {
                                        nativeAdLoader.destroy(maxAd);
                                    }

                                    // Save ad for cleanup.
                                    maxAd = ad;

                                    // Add ad view to view.
                                    applovinNativeAd.removeAllViews();
                                    applovinNativeAd.addView(nativeAdView);
                                    applovinNativeAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);

                                    LinearLayout applovinNativeBackground = nativeAdView.findViewById(R.id.applovin_native_background);
                                    if (darkTheme) {
                                        applovinNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                    } else {
                                        applovinNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                    }

                                    Log.d(TAG, "Max Native Ad loaded successfully");
                                }

                                @Override
                                public void onNativeAdLoadFailed(@NonNull final String adUnitId, @NonNull final MaxError error) {
                                    // We recommend retrying with exponentially higher delays up to a maximum delay
                                    loadBackupNativeAd(context);
                                    Log.d(TAG, "failed to load Max Native Ad with message : " + error.getMessage() + " and error code : " + error.getCode());
                                }

                                @Override
                                public void onNativeAdClicked(@NonNull final MaxAd ad) {
                                    // Optional click callback
                                }
                            });
                            if (darkTheme) {
                                nativeAdLoader.loadAd(createNativeAdViewDark(context));
                            } else {
                                nativeAdLoader.loadAd(createNativeAdView(context));
                            }
                        } else {
                            Log.d(TAG, "AppLovin Native Ad has been loaded");
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinDiscoveryMrecAd.getVisibility() != View.VISIBLE) {
                            AdRequest.Builder builder = new AdRequest.Builder();
                            Bundle bannerExtras = new Bundle();
                            bannerExtras.putString("zone_id", appLovinDiscMrecZoneId);
                            builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                            AppLovinAdSize adSize = AppLovinAdSize.MREC;
                            this.appLovinAdView = new AppLovinAdView(adSize, context);
                            this.appLovinAdView.setAdLoadListener(new AppLovinAdLoadListener() {
                                @Override
                                public void adReceived(AppLovinAd ad) {
                                    appLovinDiscoveryMrecAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void failedToReceiveAd(int errorCode) {
                                    appLovinDiscoveryMrecAd.setVisibility(View.GONE);
                                    nativeAdViewContainer.setVisibility(View.GONE);
                                    loadBackupNativeAd(context);
                                }
                            });
                            appLovinDiscoveryMrecAd.addView(this.appLovinAdView);
                            int padding = context.getResources().getDimensionPixelOffset(R.dimen.gnt_default_margin);
                            appLovinDiscoveryMrecAd.setPadding(0, padding, 0, padding);
                            if (darkTheme) {
                                appLovinDiscoveryMrecAd.setBackgroundResource(nativeBackgroundDark);
                            } else {
                                appLovinDiscoveryMrecAd.setBackgroundResource(nativeBackgroundLight);
                            }
                            this.appLovinAdView.loadNextAd();
                        } else {
                            Log.d(TAG, "AppLovin Discovery Mrec Ad has been loaded");
                        }
                        break;

                    default:
                        break;

                }
            }
        }
    }

    private void loadBackupNativeAd(Context context) {
        if (adStatus.equals(AD_STATUS_ON)) {
            if (placementStatus != 0) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (admobNativeAd.getVisibility() != View.VISIBLE) {
                            AdLoader adLoader = new AdLoader.Builder(context, adMobNativeId)
                                    .forNativeAd(NativeAd -> {
                                        if (darkTheme) {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundDark));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admobNativeAd.setStyles(styles);
                                            admobNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                        } else {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundLight));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            admobNativeAd.setStyles(styles);
                                            admobNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                        }
                                        mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                        admobNativeAd.setNativeAd(NativeAd);
                                        admobNativeAd.setVisibility(View.VISIBLE);
                                        nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    })
                                    .withAdListener(new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                            admobNativeAd.setVisibility(View.GONE);
                                            nativeAdViewContainer.setVisibility(View.GONE);
                                        }
                                    })
                                    .build();
                            adLoader.loadAd(Tools.getAdRequest((Activity) context, legacyGDPR));
                        } else {
                            Log.d(TAG, "AdMob native ads has been loaded");
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerNativeAd.getVisibility() != View.VISIBLE) {
                            AdLoader adLoader = new AdLoader.Builder(context, adManagerNativeId)
                                    .forNativeAd(NativeAd -> {
                                        if (darkTheme) {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundDark));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            adManagerNativeAd.setStyles(styles);
                                            adManagerNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                        } else {
                                            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, nativeBackgroundLight));
                                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                            adManagerNativeAd.setStyles(styles);
                                            adManagerNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                        }
                                        adManagerMediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                        adManagerNativeAd.setNativeAd(NativeAd);
                                        adManagerNativeAd.setVisibility(View.VISIBLE);
                                        nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    })
                                    .withAdListener(new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                            adManagerNativeAd.setVisibility(View.GONE);
                                            nativeAdViewContainer.setVisibility(View.GONE);
                                        }
                                    })
                                    .build();
                            adLoader.loadAd(Tools.getGoogleAdManagerRequest());
                        } else {
                            Log.d(TAG, "Ad Manager Native Ad has been loaded");
                        }
                        break;

                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (applovinNativeAd.getVisibility() != View.VISIBLE) {
                            nativeAdLoader = new MaxNativeAdLoader(appLovinNativeId, context);
                            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                @Override
                                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, @NonNull final MaxAd ad) {
                                    // Clean up any pre-existing native ad to prevent memory leaks.
                                    if (maxAd != null) {
                                        nativeAdLoader.destroy(maxAd);
                                    }

                                    // Save ad for cleanup.
                                    maxAd = ad;

                                    // Add ad view to view.
                                    applovinNativeAd.removeAllViews();
                                    applovinNativeAd.addView(nativeAdView);
                                    applovinNativeAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);

                                    LinearLayout applovinNativeBackground = nativeAdView.findViewById(R.id.applovin_native_background);
                                    if (darkTheme) {
                                        applovinNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                    } else {
                                        applovinNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                    }

                                    Log.d(TAG, "Max Native Ad loaded successfully");
                                }

                                @Override
                                public void onNativeAdLoadFailed(@NonNull final String adUnitId, @NonNull final MaxError error) {
                                    // We recommend retrying with exponentially higher delays up to a maximum delay
                                    Log.d(TAG, "failed to load Max Native Ad with message : " + error.getMessage() + " and error code : " + error.getCode());
                                }

                                @Override
                                public void onNativeAdClicked(@NonNull final MaxAd ad) {
                                    // Optional click callback
                                }
                            });
                            if (darkTheme) {
                                nativeAdLoader.loadAd(createNativeAdViewDark(context));
                            } else {
                                nativeAdLoader.loadAd(createNativeAdView(context));
                            }
                        } else {
                            Log.d(TAG, "AppLovin Native Ad has been loaded");
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinDiscoveryMrecAd.getVisibility() != View.VISIBLE) {
                            AdRequest.Builder builder = new AdRequest.Builder();
                            Bundle bannerExtras = new Bundle();
                            bannerExtras.putString("zone_id", appLovinDiscMrecZoneId);
                            builder.addCustomEventExtrasBundle(AppLovinCustomEventBanner.class, bannerExtras);

                            AppLovinAdSize adSize = AppLovinAdSize.MREC;
                            this.appLovinAdView = new AppLovinAdView(adSize, context);
                            this.appLovinAdView.setAdLoadListener(new AppLovinAdLoadListener() {
                                @Override
                                public void adReceived(AppLovinAd ad) {
                                    appLovinDiscoveryMrecAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void failedToReceiveAd(int errorCode) {
                                    appLovinDiscoveryMrecAd.setVisibility(View.GONE);
                                    nativeAdViewContainer.setVisibility(View.GONE);
                                }
                            });
                            appLovinDiscoveryMrecAd.addView(this.appLovinAdView);
                            int padding = context.getResources().getDimensionPixelOffset(R.dimen.gnt_default_margin);
                            appLovinDiscoveryMrecAd.setPadding(0, padding, 0, padding);
                            if (darkTheme) {
                                appLovinDiscoveryMrecAd.setBackgroundResource(nativeBackgroundDark);
                            } else {
                                appLovinDiscoveryMrecAd.setBackgroundResource(nativeBackgroundLight);
                            }
                            this.appLovinAdView.loadNextAd();
                        } else {
                            Log.d(TAG, "AppLovin Discovery Mrec Ad has been loaded");
                        }
                        break;

                    default:
                        nativeAdViewContainer.setVisibility(View.GONE);
                        break;

                }
            }
        }
    }

    private void setNativeAdCornerRadius(Context context) {
        nativeAdViewContainer.setRadius(context.getResources().getDimensionPixelOffset(cornerRadius));
    }

    private void setNativeAdStrokeWidth(Context context) {
        nativeAdViewContainer.setStrokeWidth(context.getResources().getDimensionPixelOffset(strokeWidth));
    }

    private void setNativeAdStrokeColor(Context context) {
        nativeAdViewContainer.setStrokeColor(ContextCompat.getColor(context, strokeColor));
    }

    private void setNativeAdPadding(Context context, int left, int top, int right, int bottom) {
        nativeAdViewContainer.setContentPadding(
                context.getResources().getDimensionPixelSize(left),
                context.getResources().getDimensionPixelSize(top),
                context.getResources().getDimensionPixelSize(right),
                context.getResources().getDimensionPixelSize(bottom)
        );
        if (darkTheme) {
            nativeAdViewContainer.setCardBackgroundColor(ContextCompat.getColor(context, nativeBackgroundDark));
        } else {
            nativeAdViewContainer.setCardBackgroundColor(ContextCompat.getColor(context, nativeBackgroundLight));
        }
    }

    private void setNativeAdMargin(Context context, int left, int top, int right, int bottom) {
        setMargins(context, nativeAdViewContainer, left, top, right, bottom);
    }

    private void setMargins(Context context, View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(
                    context.getResources().getDimensionPixelSize(left),
                    context.getResources().getDimensionPixelSize(top),
                    context.getResources().getDimensionPixelSize(right),
                    context.getResources().getDimensionPixelSize(bottom)
            );
            view.requestLayout();
        }
    }

    public MaxNativeAdView createNativeAdView(Context context) {
        MaxNativeAdViewBinder binder;
        switch (nativeAdStyle) {
            case Constant.STYLE_NEWS:
            case Constant.STYLE_MEDIUM:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_news_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            case Constant.STYLE_RADIO:
            case Constant.STYLE_SMALL:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_radio_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            case Constant.STYLE_VIDEO_LARGE:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_video_large_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            case Constant.STYLE_VIDEO_SMALL:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_video_small_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            default:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_medium_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
        }
        return new MaxNativeAdView(binder, context);

    }

    public MaxNativeAdView createNativeAdViewDark(Context context) {
        MaxNativeAdViewBinder binder;
        switch (nativeAdStyle) {
            case Constant.STYLE_NEWS:
            case Constant.STYLE_MEDIUM:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_dark_news_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            case Constant.STYLE_RADIO:
            case Constant.STYLE_SMALL:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_dark_radio_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            case Constant.STYLE_VIDEO_LARGE:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_dark_video_large_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            case Constant.STYLE_VIDEO_SMALL:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_dark_video_small_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
            default:
                binder = new MaxNativeAdViewBinder.Builder(R.layout.gnt_applovin_dark_medium_template_view)
                        .setTitleTextViewId(R.id.title_text_view)
                        .setBodyTextViewId(R.id.body_text_view)
                        .setAdvertiserTextViewId(R.id.advertiser_textView)
                        .setIconImageViewId(R.id.icon_image_view)
                        .setMediaContentViewGroupId(R.id.media_view_container)
                        .setOptionsContentViewGroupId(R.id.ad_options_view)
                        .setCallToActionButtonId(R.id.cta_button)
                        .build();
                break;
        }
        return new MaxNativeAdView(binder, context);
    }

}
