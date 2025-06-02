package com.solodroidx.ads.nativead;

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
import static com.solodroidx.ads.util.Constant.HUAWEI;
import static com.solodroidx.ads.util.Constant.PANGLE;
import static com.solodroidx.ads.util.Constant.STARTAPP;
import static com.solodroidx.ads.util.Constant.WORTISE;
import static com.solodroidx.ads.util.Constant.YANDEX;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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
import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGImageItem;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGMediaView;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAd;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdLoadListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeRequest;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.material.card.MaterialCardView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BiddingParam;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;
import com.solodroidx.ads.R;
import com.solodroidx.ads.helper.AppLovinCustomEventBanner;
import com.solodroidx.ads.util.AdManagerTemplateView;
import com.solodroidx.ads.util.Constant;
import com.solodroidx.ads.util.NativeTemplateStyle;
import com.solodroidx.ads.util.TemplateView;
import com.solodroidx.ads.util.Tools;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.wortise.ads.RevenueData;
import com.wortise.ads.natives.GoogleNativeAd;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.nativeads.NativeAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAdException;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class NativeAdView {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    View view;

    MaterialCardView nativeAdViewContainer;

    MediaView mediaView;
    TemplateView admobNativeAd;
    LinearLayout admobNativeBackground;

    MediaView adManagerMediaView;
    AdManagerTemplateView adManagerNativeAd;
    LinearLayout adManagerNativeBackground;

    com.facebook.ads.NativeAd fanNativeAd;
    NativeAdLayout fanNativeAdLayout;

    View startappNativeAd;
    ImageView startappNativeImage;
    ImageView startappNativeIcon;
    TextView startappNativeTitle;
    TextView startappNativeDescription;
    Button startappNativeButton;
    LinearLayout startappNativeBackground;

    FrameLayout applovinNativeAd;
    MaxNativeAdLoader nativeAdLoader;
    MaxAd nativeAd;
    LinearLayout appLovinDiscoveryMrecAd;
    private AppLovinAdView appLovinAdView;

    private GoogleNativeAd mGoogleNativeAd;
    FrameLayout wortiseNativeAd;

    FrameLayout pangleNativeAd;

    FrameLayout huaweiNativeAd;

    FrameLayout yandexNativeAd;
    com.yandex.mobile.ads.nativeads.NativeAdLoader yandexNativeAdLoader;

    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobNativeId = "";
    private String adManagerNativeId = "";
    private String fanNativeId = "";
    private String appLovinNativeId = "";
    private String appLovinDiscMrecZoneId = "";
    private String wortiseNativeId = "";
    private String alienAdsNativeId = "";
    private String pangleNativeId = "";
    private String huaweiNativeId = "";
    private String yandexNativeId = "";
    private int placementStatus = 1;
    private boolean darkTheme = false;
    private boolean legacyGDPR = false;

    private String nativeAdStyle = "";
    private int nativeBackgroundLight = R.color.color_native_background_light;
    private int nativeBackgroundDark = R.color.color_native_background_dark;
    private int cornerRadius = 0;
    private int strokeWidth = 0;
    private int strokeColor = android.R.color.transparent;

    public NativeAdView(Activity activity) {
        this.activity = activity;
    }

    public NativeAdView build() {
        loadNativeAd();
        return this;
    }

    public NativeAdView setPadding(int left, int top, int right, int bottom) {
        setNativeAdPadding(left, top, right, bottom);
        return this;
    }

    public NativeAdView setMargin(int left, int top, int right, int bottom) {
        setNativeAdMargin(left, top, right, bottom);
        return this;
    }

    public NativeAdView setRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setNativeAdCornerRadius();
        return this;
    }

    public NativeAdView setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setNativeAdStrokeWidth();
        return this;
    }

    public NativeAdView setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setNativeAdStrokeColor();
        return this;
    }

    public NativeAdView setView(View view) {
        this.view = view;
        return this;
    }

    public NativeAdView setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public NativeAdView setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public NativeAdView setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public NativeAdView setAdMobNativeId(String adMobNativeId) {
        this.adMobNativeId = adMobNativeId;
        return this;
    }

    public NativeAdView setAdManagerNativeId(String adManagerNativeId) {
        this.adManagerNativeId = adManagerNativeId;
        return this;
    }

    public NativeAdView setFanNativeId(String fanNativeId) {
        this.fanNativeId = fanNativeId;
        return this;
    }

    public NativeAdView setAppLovinNativeId(String appLovinNativeId) {
        this.appLovinNativeId = appLovinNativeId;
        return this;
    }

    public NativeAdView setAppLovinDiscoveryMrecZoneId(String appLovinDiscMrecZoneId) {
        this.appLovinDiscMrecZoneId = appLovinDiscMrecZoneId;
        return this;
    }

    public NativeAdView setWortiseNativeId(String wortiseNativeId) {
        this.wortiseNativeId = wortiseNativeId;
        return this;
    }

    public NativeAdView setAlienAdsNativeId(String alienAdsNativeId) {
        this.alienAdsNativeId = alienAdsNativeId;
        return this;
    }

    public NativeAdView setPangleNativeId(String pangleNativeId) {
        this.pangleNativeId = pangleNativeId;
        return this;
    }

    public NativeAdView setHuaweiNativeId(String huaweiNativeId) {
        this.huaweiNativeId = huaweiNativeId;
        return this;
    }

    public NativeAdView setYandexNativeId(String yandexNativeId) {
        this.yandexNativeId = yandexNativeId;
        return this;
    }

    public NativeAdView setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public NativeAdView setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
        return this;
    }

    public NativeAdView setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public NativeAdView setNativeAdStyle(String nativeAdStyle) {
        this.nativeAdStyle = nativeAdStyle;
        return this;
    }

    public NativeAdView setBackgroundColor(int colorLight, int colorDark) {
        this.nativeBackgroundLight = colorLight;
        this.nativeBackgroundDark = colorDark;
        return this;
    }

    public void loadNativeAd() {

        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {

            nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);

            admobNativeAd = view.findViewById(R.id.admob_native_ad_container);
            mediaView = view.findViewById(R.id.media_view);
            admobNativeBackground = view.findViewById(R.id.background);

            adManagerNativeAd = view.findViewById(R.id.google_ad_manager_native_ad_container);
            adManagerMediaView = view.findViewById(R.id.ad_manager_media_view);
            adManagerNativeBackground = view.findViewById(R.id.ad_manager_background);

            fanNativeAdLayout = view.findViewById(R.id.fan_native_ad_container);

            startappNativeAd = view.findViewById(R.id.startapp_native_ad_container);
            startappNativeImage = view.findViewById(R.id.startapp_native_image);
            startappNativeIcon = view.findViewById(R.id.startapp_native_icon);
            startappNativeTitle = view.findViewById(R.id.startapp_native_title);
            startappNativeDescription = view.findViewById(R.id.startapp_native_description);
            startappNativeButton = view.findViewById(R.id.startapp_native_button);
            startappNativeButton.setOnClickListener(v -> startappNativeAd.performClick());
            startappNativeBackground = view.findViewById(R.id.startapp_native_background);

            applovinNativeAd = view.findViewById(R.id.applovin_native_ad_container);
            appLovinDiscoveryMrecAd = view.findViewById(R.id.applovin_discovery_mrec_ad_container);

            wortiseNativeAd = view.findViewById(R.id.wortise_native_ad_container);

            pangleNativeAd = view.findViewById(R.id.pangle_native_ad_container);

            huaweiNativeAd = view.findViewById(R.id.huawei_native_ad_container);

            yandexNativeAd = view.findViewById(R.id.yandex_native_ad_container);

            switch (adNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    if (admobNativeAd.getVisibility() != View.VISIBLE) {
                        AdLoader adLoader = new AdLoader.Builder(activity, adMobNativeId)
                                .forNativeAd(NativeAd -> {
                                    if (darkTheme) {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundDark));
                                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                        admobNativeAd.setStyles(styles);
                                        admobNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                    } else {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundLight));
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
                                        loadBackupNativeAd();
                                    }
                                })
                                .build();
                        adLoader.loadAd(Tools.getAdRequest(activity, legacyGDPR));
                    } else {
                        Log.d(TAG, "AdMob Native Ad has been loaded");
                    }
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (adManagerNativeAd.getVisibility() != View.VISIBLE) {
                        AdLoader adLoader = new AdLoader.Builder(activity, adManagerNativeId)
                                .forNativeAd(NativeAd -> {
                                    if (darkTheme) {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundDark));
                                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                        adManagerNativeAd.setStyles(styles);
                                        adManagerNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                    } else {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundLight));
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
                                        loadBackupNativeAd();
                                    }
                                })
                                .build();
                        adLoader.loadAd(Tools.getGoogleAdManagerRequest());
                    } else {
                        Log.d(TAG, "Ad Manager Native Ad has been loaded");
                    }
                    break;

                case FAN:
                case FACEBOOK:
                    fanNativeAd = new com.facebook.ads.NativeAd(activity, fanNativeId);
                    NativeAdListener nativeAdListener = new NativeAdListener() {
                        @Override
                        public void onMediaDownloaded(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onError(com.facebook.ads.Ad ad, AdError adError) {
                            loadBackupNativeAd();
                        }

                        @Override
                        public void onAdLoaded(com.facebook.ads.Ad ad) {
                            // Race condition, load() called again before last ad was displayed
                            fanNativeAdLayout.setVisibility(View.VISIBLE);
                            nativeAdViewContainer.setVisibility(View.VISIBLE);
                            if (fanNativeAd != ad) {
                                return;
                            }
                            // Inflate Native Ad into Container
                            //inflateAd(nativeAd);
                            fanNativeAd.unregisterView();
                            // Add the Ad view into the ad container.
                            LayoutInflater inflater = LayoutInflater.from(activity);
                            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                            LinearLayout nativeAdView;

                            switch (nativeAdStyle) {
                                case Constant.STYLE_NEWS:
                                case Constant.STYLE_MEDIUM:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_news_template_view, fanNativeAdLayout, false);
                                    break;
                                case Constant.STYLE_VIDEO_SMALL:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_video_small_template_view, fanNativeAdLayout, false);
                                    break;
                                case Constant.STYLE_VIDEO_LARGE:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_video_large_template_view, fanNativeAdLayout, false);
                                    break;
                                case Constant.STYLE_RADIO:
                                case Constant.STYLE_SMALL:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_radio_template_view, fanNativeAdLayout, false);
                                    break;
                                default:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_medium_template_view, fanNativeAdLayout, false);
                                    break;
                            }
                            fanNativeAdLayout.addView(nativeAdView);

                            // Add the AdOptionsView
                            LinearLayout adChoicesContainer = nativeAdView.findViewById(R.id.ad_choices_container);
                            AdOptionsView adOptionsView = new AdOptionsView(activity, fanNativeAd, fanNativeAdLayout);
                            adChoicesContainer.removeAllViews();
                            adChoicesContainer.addView(adOptionsView, 0);

                            // Create native UI using the ad metadata.
                            TextView nativeAdTitle = nativeAdView.findViewById(R.id.native_ad_title);
                            com.facebook.ads.MediaView nativeAdMedia = nativeAdView.findViewById(R.id.native_ad_media);
                            com.facebook.ads.MediaView nativeAdIcon = nativeAdView.findViewById(R.id.native_ad_icon);
                            TextView nativeAdSocialContext = nativeAdView.findViewById(R.id.native_ad_social_context);
                            TextView nativeAdBody = nativeAdView.findViewById(R.id.native_ad_body);
                            TextView sponsoredLabel = nativeAdView.findViewById(R.id.native_ad_sponsored_label);
                            Button nativeAdCallToAction = nativeAdView.findViewById(R.id.native_ad_call_to_action);
                            LinearLayout fanNativeBackground = nativeAdView.findViewById(R.id.ad_unit);

                            if (darkTheme) {
                                nativeAdTitle.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_primary_text_color));
                                nativeAdSocialContext.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_primary_text_color));
                                sponsoredLabel.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_secondary_text_color));
                                nativeAdBody.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_secondary_text_color));
                                fanNativeBackground.setBackgroundResource(nativeBackgroundDark);
                            } else {
                                fanNativeBackground.setBackgroundResource(nativeBackgroundLight);
                            }

                            // Set the Text.
                            nativeAdTitle.setText(fanNativeAd.getAdvertiserName());
                            nativeAdBody.setText(fanNativeAd.getAdBodyText());
                            nativeAdSocialContext.setText(fanNativeAd.getAdSocialContext());
                            nativeAdCallToAction.setVisibility(fanNativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                            nativeAdCallToAction.setText(fanNativeAd.getAdCallToAction());
                            sponsoredLabel.setText(fanNativeAd.getSponsoredTranslation());

                            // Create a list of clickable views
                            List<View> clickableViews = new ArrayList<>();
                            clickableViews.add(nativeAdTitle);
                            clickableViews.add(sponsoredLabel);
                            clickableViews.add(nativeAdIcon);
                            clickableViews.add(nativeAdMedia);
                            clickableViews.add(nativeAdBody);
                            clickableViews.add(nativeAdSocialContext);
                            clickableViews.add(nativeAdCallToAction);

                            // Register the Title and CTA button to listen for clicks.
                            fanNativeAd.registerViewForInteraction(nativeAdView, nativeAdIcon, nativeAdMedia, clickableViews);

                        }

                        @Override
                        public void onAdClicked(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(com.facebook.ads.Ad ad) {

                        }
                    };

                    com.facebook.ads.NativeAd.NativeLoadAdConfig loadAdConfig = fanNativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build();
                    fanNativeAd.loadAd(loadAdConfig);
                    break;

                case STARTAPP:
                    if (startappNativeAd.getVisibility() != View.VISIBLE) {
                        StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
                        NativeAdPreferences nativePrefs = new NativeAdPreferences()
                                .setAdsNumber(3)
                                .setAutoBitmapDownload(true)
                                .setPrimaryImageSize(Constant.STARTAPP_IMAGE_MEDIUM);
                        AdEventListener adListener = new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad arg0) {
                                Log.d(TAG, "StartApp Native Ad loaded");
                                startappNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                //noinspection rawtypes
                                ArrayList ads = startAppNativeAd.getNativeAds(); // get NativeAds list

                                // Print all ads details to log
                                for (Object ad : ads) {
                                    Log.d(TAG, "StartApp Native Ad " + ad.toString());
                                }

                                NativeAdDetails ad = (NativeAdDetails) ads.get(0);
                                if (ad != null) {
                                    startappNativeImage.setImageBitmap(ad.getImageBitmap());
                                    startappNativeIcon.setImageBitmap(ad.getSecondaryImageBitmap());
                                    startappNativeTitle.setText(ad.getTitle());
                                    startappNativeDescription.setText(ad.getDescription());
                                    startappNativeButton.setText(ad.isApp() ? "Install" : "Open");
                                    ad.registerViewForInteraction(startappNativeAd);
                                }

                                if (darkTheme) {
                                    startappNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                } else {
                                    startappNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                }

                            }

                            @Override
                            public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad arg0) {
                                loadBackupNativeAd();
                                Log.d(TAG, "StartApp Native Ad failed loaded");
                            }
                        };
                        //noinspection deprecation
                        startAppNativeAd.loadAd(nativePrefs, adListener);
                    } else {
                        Log.d(TAG, "StartApp Native Ad has been loaded");
                    }
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    if (applovinNativeAd.getVisibility() != View.VISIBLE) {
                        nativeAdLoader = new MaxNativeAdLoader(appLovinNativeId, activity);
                        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                            @Override
                            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, @NonNull final MaxAd ad) {
                                // Clean up any pre-existing native ad to prevent memory leaks.
                                if (nativeAd != null) {
                                    nativeAdLoader.destroy(nativeAd);
                                }

                                // Save ad for cleanup.
                                nativeAd = ad;

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
                            }

                            @Override
                            public void onNativeAdLoadFailed(@NonNull final String adUnitId, @NonNull final MaxError error) {
                                // We recommend retrying with exponentially higher delays up to a maximum delay
                                loadBackupNativeAd();
                            }

                            @Override
                            public void onNativeAdClicked(@NonNull final MaxAd ad) {
                                // Optional click callback
                            }
                        });
                        if (darkTheme) {
                            nativeAdLoader.loadAd(createNativeAdViewDark(nativeAdStyle));
                        } else {
                            nativeAdLoader.loadAd(createNativeAdView(nativeAdStyle));
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
                        this.appLovinAdView = new AppLovinAdView(adSize, activity);
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
                                loadBackupNativeAd();
                            }
                        });
                        appLovinDiscoveryMrecAd.addView(this.appLovinAdView);
                        int padding = activity.getResources().getDimensionPixelOffset(R.dimen.gnt_default_margin);
                        appLovinDiscoveryMrecAd.setPadding(0, padding, 0, padding);
                        this.appLovinAdView.loadNextAd();
                    } else {
                        Log.d(TAG, "AppLovin Discovery Mrec Ad has been loaded");
                    }
                    break;

                case WORTISE:
                    if (wortiseNativeAd.getVisibility() != View.VISIBLE) {
                        mGoogleNativeAd = new GoogleNativeAd(activity, wortiseNativeId, new GoogleNativeAd.Listener() {
                            @Override
                            public void onNativeRevenuePaid(@NonNull GoogleNativeAd googleNativeAd, @NonNull RevenueData revenueData) {

                            }

                            @Override
                            public void onNativeFailedToLoad(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                                loadBackupNativeAd();
                                Log.d(TAG, "Wortise Native Ad failed loaded");
                            }

                            @Override
                            public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {

                            }

                            @Override
                            public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {

                            }

                            @SuppressLint("InflateParams")
                            @Override
                            public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                                com.google.android.gms.ads.nativead.NativeAdView adView;
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_news_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_SMALL:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_video_small_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_LARGE:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_video_large_template_view, null);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_radio_template_view, null);
                                        break;
                                    default:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_medium_template_view, null);
                                        break;
                                }
                                populateWortiseNativeAdView(nativeAd, adView);
                                wortiseNativeAd.removeAllViews();
                                wortiseNativeAd.addView(adView);
                                wortiseNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                Log.d(TAG, "Wortise Native Ad loaded");
                            }
                        });
                        mGoogleNativeAd.load();
                    } else {
                        Log.d(TAG, "Wortise Native Ad has been loaded");
                    }
                    break;

                case PANGLE:
                    if (pangleNativeAd.getVisibility() != View.VISIBLE) {
                        PAGNativeRequest request = new PAGNativeRequest();
                        PAGNativeAd.loadAd(pangleNativeId, request, new PAGNativeAdLoadListener() {
                            @Override
                            public void onError(int code, String message) {
                                loadBackupNativeAd();
                                Log.d(TAG, "Pangle Native Ad Error");
                            }

                            @SuppressLint("InflateParams")
                            @Override
                            public void onAdLoaded(PAGNativeAd pagNativeAd) {
                                Log.d(TAG, "Pangle Native Ad Loaded");
                                View adView;
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_news_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_SMALL:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_video_small_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_LARGE:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_video_large_template_view, null);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_radio_template_view, null);
                                        break;
                                    default:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_medium_template_view, null);
                                        break;
                                }
                                populatePangleNativeAdView(adView, pagNativeAd);
                                pangleNativeAd.removeAllViews();
                                pangleNativeAd.addView(adView);
                                pangleNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        Log.d(TAG, "Pangle Native Ad has been loaded");
                    }
                    break;

                case HUAWEI:
                    if (huaweiNativeAd.getVisibility() != View.VISIBLE) {
                        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(activity, huaweiNativeId);
                        @SuppressLint("InflateParams") NativeAdLoader nativeAdLoader = builder.setNativeAdLoadedListener(nativeAd -> {
                                    NativeView nativeView;
                                    switch (nativeAdStyle) {
                                        case Constant.STYLE_NEWS:
                                        case Constant.STYLE_MEDIUM:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_news_template_view, null);
                                            break;
                                        case Constant.STYLE_VIDEO_SMALL:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_video_small_template_view, null);
                                            break;
                                        case Constant.STYLE_VIDEO_LARGE:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_video_large_template_view, null);
                                            break;
                                        case Constant.STYLE_RADIO:
                                        case Constant.STYLE_SMALL:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_radio_template_view, null);
                                            break;
                                        default:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_medium_template_view, null);
                                            break;
                                    }
                                    populateHuaweiNativeAdView(nativeAd, nativeView);
                                    huaweiNativeAd.removeAllViews();
                                    huaweiNativeAd.addView(nativeView);
                                    huaweiNativeAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    Log.d(TAG, adNetwork + " Native Ad Loaded");
                                })
                                .setAdListener(new com.huawei.hms.ads.AdListener() {
                                    @Override
                                    public void onAdFailed(int errorCode) {
                                        loadBackupNativeAd();
                                        Log.d(TAG, adNetwork + " Failed to Load Native Ad: " + errorCode);
                                    }
                                }).build();
                        AdParam.Builder adParamBuilder = new AdParam.Builder();
                        BiddingParam biddingParam = new BiddingParam();
                        adParamBuilder.addBiddingParamMap(huaweiNativeId, biddingParam);
                        adParamBuilder.setTMax(500);
                        nativeAdLoader.loadAds(new AdParam.Builder().build(), 5);
                    } else {
                        Log.d(TAG, "Huawei Native Ad has been loaded");
                    }
                    break;

                case YANDEX:
                    if (yandexNativeAd.getVisibility() != View.VISIBLE) {
                        yandexNativeAdLoader = new com.yandex.mobile.ads.nativeads.NativeAdLoader(activity);
                        yandexNativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
                            @Override
                            public void onAdLoaded(@NonNull final com.yandex.mobile.ads.nativeads.NativeAd nativeAd) {
                                com.yandex.mobile.ads.nativeads.NativeAdView adView;
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_news_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_SMALL:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_video_small_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_LARGE:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_video_large_template_view, null);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_radio_template_view, null);
                                        break;
                                    default:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_medium_template_view, null);
                                        break;
                                }
                                populateYandexNativeAdView(adView, nativeAd);
                                yandexNativeAd.removeAllViews();
                                yandexNativeAd.addView(adView);
                                yandexNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                Log.d(TAG, "Yandex Native Ad loaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull final AdRequestError error) {
                                loadBackupNativeAd();
                                Log.d(TAG, "Failed to load Yandex Native Ad: " + error.getDescription());
                            }
                        });
                        yandexNativeAdLoader.loadAd(new NativeAdRequestConfiguration.Builder(yandexNativeId).build());
                    } else {
                        Log.d(TAG, "Yandex Native Ad has been loaded");
                    }
                    break;

                default:
                    break;

            }

        }

    }

    public void loadBackupNativeAd() {

        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {

            nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);

            admobNativeAd = view.findViewById(R.id.admob_native_ad_container);
            mediaView = view.findViewById(R.id.media_view);
            admobNativeBackground = view.findViewById(R.id.background);

            adManagerNativeAd = view.findViewById(R.id.google_ad_manager_native_ad_container);
            adManagerMediaView = view.findViewById(R.id.ad_manager_media_view);
            adManagerNativeBackground = view.findViewById(R.id.ad_manager_background);

            fanNativeAdLayout = view.findViewById(R.id.fan_native_ad_container);

            startappNativeAd = view.findViewById(R.id.startapp_native_ad_container);
            startappNativeImage = view.findViewById(R.id.startapp_native_image);
            startappNativeIcon = view.findViewById(R.id.startapp_native_icon);
            startappNativeTitle = view.findViewById(R.id.startapp_native_title);
            startappNativeDescription = view.findViewById(R.id.startapp_native_description);
            startappNativeButton = view.findViewById(R.id.startapp_native_button);
            startappNativeButton.setOnClickListener(v -> startappNativeAd.performClick());
            startappNativeBackground = view.findViewById(R.id.startapp_native_background);

            applovinNativeAd = view.findViewById(R.id.applovin_native_ad_container);
            appLovinDiscoveryMrecAd = view.findViewById(R.id.applovin_discovery_mrec_ad_container);

            wortiseNativeAd = view.findViewById(R.id.wortise_native_ad_container);

            pangleNativeAd = view.findViewById(R.id.pangle_native_ad_container);

            huaweiNativeAd = view.findViewById(R.id.huawei_native_ad_container);

            yandexNativeAd = view.findViewById(R.id.yandex_native_ad_container);

            switch (backupAdNetwork) {
                case ADMOB:
                case FAN_BIDDING_ADMOB:
                    if (admobNativeAd.getVisibility() != View.VISIBLE) {
                        AdLoader adLoader = new AdLoader.Builder(activity, adMobNativeId)
                                .forNativeAd(NativeAd -> {
                                    if (darkTheme) {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundDark));
                                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                        admobNativeAd.setStyles(styles);
                                        admobNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                    } else {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundLight));
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
                        adLoader.loadAd(Tools.getAdRequest(activity, legacyGDPR));
                    } else {
                        Log.d(TAG, "AdMob Native Ad has been loaded");
                    }
                    break;

                case GOOGLE_AD_MANAGER:
                case FAN_BIDDING_AD_MANAGER:
                    if (adManagerNativeAd.getVisibility() != View.VISIBLE) {
                        AdLoader adLoader = new AdLoader.Builder(activity, adManagerNativeId)
                                .forNativeAd(NativeAd -> {
                                    if (darkTheme) {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundDark));
                                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();
                                        adManagerNativeAd.setStyles(styles);
                                        adManagerNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                    } else {
                                        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, nativeBackgroundLight));
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

                case STARTAPP:
                    if (startappNativeAd.getVisibility() != View.VISIBLE) {
                        StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
                        NativeAdPreferences nativePrefs = new NativeAdPreferences()
                                .setAdsNumber(3)
                                .setAutoBitmapDownload(true)
                                .setPrimaryImageSize(Constant.STARTAPP_IMAGE_MEDIUM);
                        AdEventListener adListener = new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad arg0) {
                                Log.d(TAG, "StartApp Native Ad loaded");
                                startappNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                //noinspection rawtypes
                                ArrayList ads = startAppNativeAd.getNativeAds(); // get NativeAds list

                                // Print all ads details to log
                                for (Object ad : ads) {
                                    Log.d(TAG, "StartApp Native Ad " + ad.toString());
                                }

                                NativeAdDetails ad = (NativeAdDetails) ads.get(0);
                                if (ad != null) {
                                    startappNativeImage.setImageBitmap(ad.getImageBitmap());
                                    startappNativeTitle.setText(ad.getTitle());
                                    startappNativeDescription.setText(ad.getDescription());
                                    startappNativeButton.setText(ad.isApp() ? "Install" : "Open");
                                    ad.registerViewForInteraction(startappNativeAd);
                                }

                                if (darkTheme) {
                                    startappNativeBackground.setBackgroundResource(nativeBackgroundDark);
                                } else {
                                    startappNativeBackground.setBackgroundResource(nativeBackgroundLight);
                                }

                            }

                            @Override
                            public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad arg0) {
                                startappNativeAd.setVisibility(View.GONE);
                                nativeAdViewContainer.setVisibility(View.GONE);
                                Log.d(TAG, "StartApp Native Ad failed loaded");
                            }
                        };
                        //noinspection deprecation
                        startAppNativeAd.loadAd(nativePrefs, adListener);
                    } else {
                        Log.d(TAG, "StartApp Native Ad has been loaded");
                    }
                    break;

                case FAN:
                case FACEBOOK:
                    fanNativeAd = new com.facebook.ads.NativeAd(activity, fanNativeId);
                    NativeAdListener nativeAdListener = new NativeAdListener() {
                        @Override
                        public void onMediaDownloaded(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onError(com.facebook.ads.Ad ad, AdError adError) {

                        }

                        @Override
                        public void onAdLoaded(com.facebook.ads.Ad ad) {
                            // Race condition, load() called again before last ad was displayed
                            fanNativeAdLayout.setVisibility(View.VISIBLE);
                            nativeAdViewContainer.setVisibility(View.VISIBLE);
                            if (fanNativeAd != ad) {
                                return;
                            }
                            // Inflate Native Ad into Container
                            //inflateAd(nativeAd);
                            fanNativeAd.unregisterView();
                            // Add the Ad view into the ad container.
                            LayoutInflater inflater = LayoutInflater.from(activity);
                            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                            LinearLayout nativeAdView;

                            switch (nativeAdStyle) {
                                case Constant.STYLE_NEWS:
                                case Constant.STYLE_MEDIUM:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_news_template_view, fanNativeAdLayout, false);
                                    break;
                                case Constant.STYLE_VIDEO_SMALL:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_video_small_template_view, fanNativeAdLayout, false);
                                    break;
                                case Constant.STYLE_VIDEO_LARGE:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_video_large_template_view, fanNativeAdLayout, false);
                                    break;
                                case Constant.STYLE_RADIO:
                                case Constant.STYLE_SMALL:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_radio_template_view, fanNativeAdLayout, false);
                                    break;
                                default:
                                    nativeAdView = (LinearLayout) inflater.inflate(R.layout.gnt_fan_medium_template_view, fanNativeAdLayout, false);
                                    break;
                            }
                            fanNativeAdLayout.addView(nativeAdView);

                            // Add the AdOptionsView
                            LinearLayout adChoicesContainer = nativeAdView.findViewById(R.id.ad_choices_container);
                            AdOptionsView adOptionsView = new AdOptionsView(activity, fanNativeAd, fanNativeAdLayout);
                            adChoicesContainer.removeAllViews();
                            adChoicesContainer.addView(adOptionsView, 0);

                            // Create native UI using the ad metadata.
                            TextView nativeAdTitle = nativeAdView.findViewById(R.id.native_ad_title);
                            com.facebook.ads.MediaView nativeAdMedia = nativeAdView.findViewById(R.id.native_ad_media);
                            com.facebook.ads.MediaView nativeAdIcon = nativeAdView.findViewById(R.id.native_ad_icon);
                            TextView nativeAdSocialContext = nativeAdView.findViewById(R.id.native_ad_social_context);
                            TextView nativeAdBody = nativeAdView.findViewById(R.id.native_ad_body);
                            TextView sponsoredLabel = nativeAdView.findViewById(R.id.native_ad_sponsored_label);
                            Button nativeAdCallToAction = nativeAdView.findViewById(R.id.native_ad_call_to_action);
                            LinearLayout fanNativeBackground = nativeAdView.findViewById(R.id.ad_unit);

                            if (darkTheme) {
                                nativeAdTitle.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_primary_text_color));
                                nativeAdSocialContext.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_primary_text_color));
                                sponsoredLabel.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_secondary_text_color));
                                nativeAdBody.setTextColor(ContextCompat.getColor(activity, R.color.applovin_dark_secondary_text_color));
                                fanNativeBackground.setBackgroundResource(nativeBackgroundDark);
                            } else {
                                fanNativeBackground.setBackgroundResource(nativeBackgroundLight);
                            }

                            // Set the Text.
                            nativeAdTitle.setText(fanNativeAd.getAdvertiserName());
                            nativeAdBody.setText(fanNativeAd.getAdBodyText());
                            nativeAdSocialContext.setText(fanNativeAd.getAdSocialContext());
                            nativeAdCallToAction.setVisibility(fanNativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                            nativeAdCallToAction.setText(fanNativeAd.getAdCallToAction());
                            sponsoredLabel.setText(fanNativeAd.getSponsoredTranslation());

                            // Create a list of clickable views
                            List<View> clickableViews = new ArrayList<>();
                            clickableViews.add(nativeAdTitle);
                            clickableViews.add(sponsoredLabel);
                            clickableViews.add(nativeAdIcon);
                            clickableViews.add(nativeAdMedia);
                            clickableViews.add(nativeAdBody);
                            clickableViews.add(nativeAdSocialContext);
                            clickableViews.add(nativeAdCallToAction);

                            // Register the Title and CTA button to listen for clicks.
                            fanNativeAd.registerViewForInteraction(nativeAdView, nativeAdIcon, nativeAdMedia, clickableViews);

                        }

                        @Override
                        public void onAdClicked(com.facebook.ads.Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(com.facebook.ads.Ad ad) {

                        }
                    };

                    com.facebook.ads.NativeAd.NativeLoadAdConfig loadAdConfig = fanNativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build();
                    fanNativeAd.loadAd(loadAdConfig);
                    break;

                case APPLOVIN:
                case APPLOVIN_MAX:
                case FAN_BIDDING_APPLOVIN_MAX:
                    if (applovinNativeAd.getVisibility() != View.VISIBLE) {
                        nativeAdLoader = new MaxNativeAdLoader(appLovinNativeId, activity);
                        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                            @Override
                            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, @NonNull final MaxAd ad) {
                                // Clean up any pre-existing native ad to prevent memory leaks.
                                if (nativeAd != null) {
                                    nativeAdLoader.destroy(nativeAd);
                                }

                                // Save ad for cleanup.
                                nativeAd = ad;

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
                            }

                            @Override
                            public void onNativeAdLoadFailed(@NonNull final String adUnitId, @NonNull final MaxError error) {
                                // We recommend retrying with exponentially higher delays up to a maximum delay
                            }

                            @Override
                            public void onNativeAdClicked(@NonNull final MaxAd ad) {
                                // Optional click callback
                            }
                        });
                        if (darkTheme) {
                            nativeAdLoader.loadAd(createNativeAdViewDark(nativeAdStyle));
                        } else {
                            nativeAdLoader.loadAd(createNativeAdView(nativeAdStyle));
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
                        this.appLovinAdView = new AppLovinAdView(adSize, activity);
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
                        int padding = activity.getResources().getDimensionPixelOffset(R.dimen.gnt_default_margin);
                        appLovinDiscoveryMrecAd.setPadding(0, padding, 0, padding);
                        this.appLovinAdView.loadNextAd();
                    } else {
                        Log.d(TAG, "AppLovin Discovery Mrec Ad has been loaded");
                    }
                    break;

                case WORTISE:
                    if (wortiseNativeAd.getVisibility() != View.VISIBLE) {
                        mGoogleNativeAd = new GoogleNativeAd(activity, wortiseNativeId, new GoogleNativeAd.Listener() {
                            @Override
                            public void onNativeRevenuePaid(@NonNull GoogleNativeAd googleNativeAd, @NonNull RevenueData revenueData) {

                            }

                            @Override
                            public void onNativeFailedToLoad(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                                Log.d(TAG, "[Backup] Wortise Native Ad failed loaded");
                            }

                            @Override
                            public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {

                            }

                            @Override
                            public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {

                            }

                            @SuppressLint("InflateParams")
                            @Override
                            public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                                com.google.android.gms.ads.nativead.NativeAdView adView;
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_news_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_SMALL:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_video_small_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_LARGE:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_video_large_template_view, null);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_radio_template_view, null);
                                        break;
                                    default:
                                        adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_wortise_medium_template_view, null);
                                        break;
                                }
                                populateWortiseNativeAdView(nativeAd, adView);
                                wortiseNativeAd.removeAllViews();
                                wortiseNativeAd.addView(adView);
                                wortiseNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                Log.d(TAG, "[Backup] Wortise Native Ad loaded");
                            }
                        });
                        mGoogleNativeAd.load();
                    } else {
                        Log.d(TAG, "[Backup] Wortise Native Ad has been loaded");
                    }
                    break;

                case PANGLE:
                    if (pangleNativeAd.getVisibility() != View.VISIBLE) {
                        PAGNativeRequest request = new PAGNativeRequest();
                        PAGNativeAd.loadAd(pangleNativeId, request, new PAGNativeAdLoadListener() {
                            @Override
                            public void onError(int code, String message) {
                                pangleNativeAd.setVisibility(View.GONE);
                                nativeAdViewContainer.setVisibility(View.GONE);
                                Log.d(TAG, "[Backup] Pangle Native Ad Error");
                            }

                            @SuppressLint("InflateParams")
                            @Override
                            public void onAdLoaded(PAGNativeAd pagNativeAd) {
                                Log.d(TAG, "[Backup] Pangle Native Ad Loaded");
                                View adView;
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_news_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_SMALL:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_video_small_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_LARGE:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_video_large_template_view, null);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_radio_template_view, null);
                                        break;
                                    default:
                                        adView = activity.getLayoutInflater().inflate(R.layout.gnt_pangle_medium_template_view, null);
                                        break;
                                }
                                populatePangleNativeAdView(adView, pagNativeAd);
                                pangleNativeAd.removeAllViews();
                                pangleNativeAd.addView(adView);
                                pangleNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        Log.d(TAG, "[Backup] Pangle Native Ad has been loaded");
                    }
                    break;

                case HUAWEI:
                    if (huaweiNativeAd.getVisibility() != View.VISIBLE) {
                        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(activity, huaweiNativeId);
                        @SuppressLint("InflateParams") NativeAdLoader nativeAdLoader = builder.setNativeAdLoadedListener(nativeAd -> {
                                    NativeView nativeView;
                                    switch (nativeAdStyle) {
                                        case Constant.STYLE_NEWS:
                                        case Constant.STYLE_MEDIUM:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_news_template_view, null);
                                            break;
                                        case Constant.STYLE_VIDEO_SMALL:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_video_small_template_view, null);
                                            break;
                                        case Constant.STYLE_VIDEO_LARGE:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_video_large_template_view, null);
                                            break;
                                        case Constant.STYLE_RADIO:
                                        case Constant.STYLE_SMALL:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_radio_template_view, null);
                                            break;
                                        default:
                                            nativeView = (NativeView) activity.getLayoutInflater().inflate(R.layout.gnt_huawei_medium_template_view, null);
                                            break;
                                    }
                                    populateHuaweiNativeAdView(nativeAd, nativeView);
                                    huaweiNativeAd.removeAllViews();
                                    huaweiNativeAd.addView(nativeView);
                                    huaweiNativeAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    Log.d(TAG, adNetwork + " [Backup] Native Ad Loaded");
                                })
                                .setAdListener(new com.huawei.hms.ads.AdListener() {
                                    @Override
                                    public void onAdFailed(int errorCode) {
                                        huaweiNativeAd.setVisibility(View.GONE);
                                        nativeAdViewContainer.setVisibility(View.GONE);
                                        Log.d(TAG, adNetwork + " [Backup] Failed to Load Native Ad: " + errorCode);
                                    }
                                }).build();
                        AdParam.Builder adParamBuilder = new AdParam.Builder();
                        BiddingParam biddingParam = new BiddingParam();
                        adParamBuilder.addBiddingParamMap(huaweiNativeId, biddingParam);
                        adParamBuilder.setTMax(500);
                        nativeAdLoader.loadAds(new AdParam.Builder().build(), 5);
                    } else {
                        Log.d(TAG, "[Backup] Huawei Native Ad has been loaded");
                    }
                    break;

                case YANDEX:
                    if (yandexNativeAd.getVisibility() != View.VISIBLE) {
                        yandexNativeAdLoader = new com.yandex.mobile.ads.nativeads.NativeAdLoader(activity);
                        yandexNativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
                            @Override
                            public void onAdLoaded(@NonNull final com.yandex.mobile.ads.nativeads.NativeAd nativeAd) {
                                com.yandex.mobile.ads.nativeads.NativeAdView adView;
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_news_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_SMALL:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_video_small_template_view, null);
                                        break;
                                    case Constant.STYLE_VIDEO_LARGE:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_video_large_template_view, null);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_radio_template_view, null);
                                        break;
                                    default:
                                        adView = (com.yandex.mobile.ads.nativeads.NativeAdView) activity.getLayoutInflater().inflate(R.layout.gnt_yandex_medium_template_view, null);
                                        break;
                                }
                                populateYandexNativeAdView(adView, nativeAd);
                                yandexNativeAd.removeAllViews();
                                yandexNativeAd.addView(adView);
                                yandexNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                Log.d(TAG, "Yandex Native Ad loaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull final AdRequestError error) {
                                Log.d(TAG, "Failed to load Yandex Native Ad: " + error.getDescription());
                            }
                        });
                        yandexNativeAdLoader.loadAd(new NativeAdRequestConfiguration.Builder(yandexNativeId).build());
                    } else {
                        Log.d(TAG, "Yandex Native Ad has been loaded");
                    }
                    break;

                default:
                    nativeAdViewContainer.setVisibility(View.GONE);
                    break;
            }

        }

    }

    private void setNativeAdCornerRadius() {
        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setRadius(activity.getResources().getDimensionPixelOffset(cornerRadius));
    }

    private void setNativeAdStrokeWidth() {
        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setStrokeWidth(activity.getResources().getDimensionPixelOffset(strokeWidth));
    }

    private void setNativeAdStrokeColor() {
        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setStrokeColor(ContextCompat.getColor(activity, strokeColor));
    }

    private void setNativeAdPadding(int left, int top, int right, int bottom) {
        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setContentPadding(
                activity.getResources().getDimensionPixelSize(left),
                activity.getResources().getDimensionPixelSize(top),
                activity.getResources().getDimensionPixelSize(right),
                activity.getResources().getDimensionPixelSize(bottom)
        );
        if (darkTheme) {
            nativeAdViewContainer.setCardBackgroundColor(ContextCompat.getColor(activity, nativeBackgroundDark));
        } else {
            nativeAdViewContainer.setCardBackgroundColor(ContextCompat.getColor(activity, nativeBackgroundLight));
        }
    }

    private void setNativeAdMargin(int left, int top, int right, int bottom) {
        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);
        setMargins(nativeAdViewContainer, left, top, right, bottom);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(
                    activity.getResources().getDimensionPixelSize(left),
                    activity.getResources().getDimensionPixelSize(top),
                    activity.getResources().getDimensionPixelSize(right),
                    activity.getResources().getDimensionPixelSize(bottom)
            );
            view.requestLayout();
        }
    }

    public MaxNativeAdView createNativeAdView(String nativeAdStyle) {
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
        return new MaxNativeAdView(binder, activity);
    }

    public MaxNativeAdView createNativeAdViewDark(String nativeAdStyle) {
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
        return new MaxNativeAdView(binder, activity);
    }

    @SuppressWarnings("ConstantConditions")
    public void populateWortiseNativeAdView(com.google.android.gms.ads.nativead.NativeAd nativeAd, com.google.android.gms.ads.nativead.NativeAdView nativeAdView) {

        if (darkTheme) {
            nativeAdViewContainer.setBackgroundColor(ContextCompat.getColor(activity, nativeBackgroundDark));
            nativeAdView.findViewById(R.id.background).setBackgroundResource(nativeBackgroundDark);
        } else {
            nativeAdViewContainer.setBackgroundColor(ContextCompat.getColor(activity, nativeBackgroundLight));
            nativeAdView.findViewById(R.id.background).setBackgroundResource(nativeBackgroundLight);
        }

        nativeAdView.setMediaView(nativeAdView.findViewById(R.id.media_view));
        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.primary));
        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.body));
        nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.cta));
        nativeAdView.setIconView(nativeAdView.findViewById(R.id.icon));

        ((TextView) nativeAdView.getHeadlineView()).setText(nativeAd.getHeadline());
        nativeAdView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (nativeAd.getBody() == null) {
            nativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            nativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) nativeAdView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            nativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            nativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) nativeAdView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            nativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) nativeAdView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            nativeAdView.getIconView().setVisibility(View.VISIBLE);
        }

        nativeAdView.setNativeAd(nativeAd);
    }

    private void populatePangleNativeAdView(View view, PAGNativeAd pagNativeAd) {
        LinearLayout pangleNativeBackground = view.findViewById(R.id.pangle_native_background);
        if (darkTheme) {
            pangleNativeBackground.setBackgroundResource(nativeBackgroundDark);
        } else {
            pangleNativeBackground.setBackgroundResource(nativeBackgroundLight);
        }
        TextView mTitle = view.findViewById(R.id.pangle_native_title);
        TextView mDescription = view.findViewById(R.id.pangle_native_description);
        ImageView mIcon = view.findViewById(R.id.pangle_native_icon);
        ImageView mDislike = view.findViewById(R.id.ad_dislike);
        Button mCreativeButton = view.findViewById(R.id.pangle_native_button);
        FrameLayout mImageOrVideoView = view.findViewById(R.id.pangle_native_image);

        mTitle.setText(pagNativeAd.getNativeAdData().getTitle());

        mDescription.setText(pagNativeAd.getNativeAdData().getDescription());

        PAGImageItem icon = pagNativeAd.getNativeAdData().getIcon();
        if (icon != null && icon.getImageUrl() != null) {
            Glide.with(activity).load(icon.getImageUrl()).into(mIcon);
        }

        PAGMediaView video = pagNativeAd.getNativeAdData().getMediaView();
        mImageOrVideoView.addView(video);

        mCreativeButton.setText(TextUtils.isEmpty(pagNativeAd.getNativeAdData().getButtonText()) ? "Download" : pagNativeAd.getNativeAdData().getButtonText());
        List<View> creativeViewList = new ArrayList<>();
        creativeViewList.add(mCreativeButton);
        creativeViewList.add(mIcon);
        creativeViewList.add(mTitle);
        creativeViewList.add(mDescription);
        creativeViewList.add(mImageOrVideoView);

        pagNativeAd.registerViewForInteraction((ViewGroup) view, creativeViewList, null, mDislike, new PAGNativeAdInteractionListener() {
            @Override
            public void onAdShowed() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdDismissed() {

            }
        });
    }

    private void populateHuaweiNativeAdView(com.huawei.hms.ads.nativead.NativeAd nativeAd, NativeView nativeView) {

        LinearLayout huaweiNativeBackground = nativeView.findViewById(R.id.huawei_native_background);
        if (darkTheme) {
            huaweiNativeBackground.setBackgroundResource(nativeBackgroundDark);
        } else {
            huaweiNativeBackground.setBackgroundResource(nativeBackgroundLight);
        }

        // Register and populate the title view.
        nativeView.setTitleView(nativeView.findViewById(R.id.huawei_native_title));
        ((TextView) nativeView.getTitleView()).setText(nativeAd.getTitle());
        // Register and populate the multimedia view.
        nativeView.setMediaView(nativeView.findViewById(R.id.huawei_native_media_view));
        nativeView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        // Register and populate other asset views.
        nativeView.setAdSourceView(nativeView.findViewById(R.id.huawei_native_description));

        nativeView.setCallToActionView(nativeView.findViewById(R.id.huawei_native_button));
        if (null != nativeAd.getAdSource()) {
            ((TextView) nativeView.getAdSourceView()).setText(nativeAd.getAdSource());
        }
        nativeView.getAdSourceView().setVisibility(null != nativeAd.getAdSource() ? View.VISIBLE : View.INVISIBLE);
        if (null != nativeAd.getCallToAction()) {
            ((Button) nativeView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        nativeView.getCallToActionView().setVisibility(null != nativeAd.getCallToAction() ? View.VISIBLE : View.INVISIBLE);

        // Register the native ad object.
        nativeView.setNativeAd(nativeAd);
    }

    private void populateYandexNativeAdView(com.yandex.mobile.ads.nativeads.NativeAdView nativeAdView, com.yandex.mobile.ads.nativeads.NativeAd nativeAd) {
        LinearLayout yandexNativeBackground = nativeAdView.findViewById(R.id.yandex_native_background);
        if (darkTheme) {
            yandexNativeBackground.setBackgroundResource(nativeBackgroundDark);
        } else {
            yandexNativeBackground.setBackgroundResource(nativeBackgroundLight);
        }
        TextView title = nativeAdView.findViewById(R.id.yandex_native_title);
        TextView body = nativeAdView.findViewById(R.id.yandex_native_description);
        ImageView icon = nativeAdView.findViewById(R.id.yandex_native_icon);
        Button button = nativeAdView.findViewById(R.id.yandex_native_button);
        com.yandex.mobile.ads.nativeads.MediaView mediaView = nativeAdView.findViewById(R.id.yandex_media_view);

        TextView sponsored = nativeAdView.findViewById(R.id.yandex_sponsored);
        TextView domain = nativeAdView.findViewById(R.id.yandex_domain);
        TextView price = nativeAdView.findViewById(R.id.yandex_price);
        TextView disclaimer = nativeAdView.findViewById(R.id.yandex_disclaimer);
        ImageView feedback = nativeAdView.findViewById(R.id.yandex_feedback);
        ImageView favicon = nativeAdView.findViewById(R.id.yandex_favicon);

        NativeAdViewBinder.Builder nativeAdViewBinder = new NativeAdViewBinder.Builder(nativeAdView);
        nativeAdViewBinder.setMediaView(mediaView);
        nativeAdViewBinder.setBodyView(body);
        nativeAdViewBinder.setCallToActionView(button);
        nativeAdViewBinder.setDomainView(domain);
        nativeAdViewBinder.setFaviconView(favicon);
        nativeAdViewBinder.setFeedbackView(feedback);
        nativeAdViewBinder.setIconView(icon);
        nativeAdViewBinder.setMediaView(mediaView);
        nativeAdViewBinder.setPriceView(price);
        nativeAdViewBinder.setSponsoredView(sponsored);
        nativeAdViewBinder.setTitleView(title);
        nativeAdViewBinder.setWarningView(disclaimer);
        try {
            nativeAd.bindNativeAd(nativeAdViewBinder.build());
            nativeAd.setNativeAdEventListener(new NativeAdEventListener() {
                @Override
                public void onAdClicked() {

                }

                @Override
                public void onLeftApplication() {

                }

                @Override
                public void onReturnedToApplication() {

                }

                @Override
                public void onImpression(@Nullable ImpressionData impressionData) {

                }
            });
        } catch (NativeAdException e) {
            throw new RuntimeException(e);
        }

    }

}

