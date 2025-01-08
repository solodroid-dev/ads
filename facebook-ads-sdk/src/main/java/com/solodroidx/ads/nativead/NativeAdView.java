package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FACEBOOK;
import static com.solodroidx.ads.util.Constant.FAN;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.material.card.MaterialCardView;
import com.solodroidx.ads.R;
import com.solodroidx.ads.util.AdManagerTemplateView;
import com.solodroidx.ads.util.Constant;
import com.solodroidx.ads.util.NativeTemplateStyle;
import com.solodroidx.ads.util.TemplateView;
import com.solodroidx.ads.util.Tools;

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

}

