package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.YANDEX;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.nativeads.NativeAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAdException;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder;

@SuppressWarnings("deprecation")
public class NativeAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    MaterialCardView nativeAdViewContainer;

    MediaView mediaView;
    TemplateView admobNativeAd;
    LinearLayout admobNativeBackground;

    MediaView adManagerMediaView;
    AdManagerTemplateView adManagerNativeAd;
    LinearLayout adManagerNativeBackground;

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

    public NativeAd(Activity activity) {
        this.activity = activity;
    }

    public NativeAd build() {
        loadNativeAd();
        return this;
    }

    public NativeAd setPadding(int left, int top, int right, int bottom) {
        setNativeAdPadding(left, top, right, bottom);
        return this;
    }

    public NativeAd setMargin(int left, int top, int right, int bottom) {
        setNativeAdMargin(left, top, right, bottom);
        return this;
    }

    public NativeAd setRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setNativeAdCornerRadius();
        return this;
    }

    public NativeAd setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setNativeAdStrokeWidth();
        return this;
    }

    public NativeAd setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setNativeAdStrokeColor();
        return this;
    }

    public NativeAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public NativeAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public NativeAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public NativeAd setAdMobNativeId(String adMobNativeId) {
        this.adMobNativeId = adMobNativeId;
        return this;
    }

    public NativeAd setAdManagerNativeId(String adManagerNativeId) {
        this.adManagerNativeId = adManagerNativeId;
        return this;
    }

    public NativeAd setFanNativeId(String fanNativeId) {
        this.fanNativeId = fanNativeId;
        return this;
    }

    public NativeAd setAppLovinNativeId(String appLovinNativeId) {
        this.appLovinNativeId = appLovinNativeId;
        return this;
    }

    public NativeAd setAppLovinDiscoveryMrecZoneId(String appLovinDiscMrecZoneId) {
        this.appLovinDiscMrecZoneId = appLovinDiscMrecZoneId;
        return this;
    }

    public NativeAd setWortiseNativeId(String wortiseNativeId) {
        this.wortiseNativeId = wortiseNativeId;
        return this;
    }

    public NativeAd setAlienAdsNativeId(String alienAdsNativeId) {
        this.alienAdsNativeId = alienAdsNativeId;
        return this;
    }

    public NativeAd setPangleNativeId(String pangleNativeId) {
        this.pangleNativeId = pangleNativeId;
        return this;
    }

    public NativeAd setHuaweiNativeId(String huaweiNativeId) {
        this.huaweiNativeId = huaweiNativeId;
        return this;
    }

    public NativeAd setYandexNativeId(String yandexNativeId) {
        this.yandexNativeId = yandexNativeId;
        return this;
    }

    public NativeAd setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public NativeAd setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
        return this;
    }

    public NativeAd setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public NativeAd setNativeAdStyle(String nativeAdStyle) {
        this.nativeAdStyle = nativeAdStyle;
        return this;
    }

    public NativeAd setBackgroundColor(int colorLight, int colorDark) {
        this.nativeBackgroundLight = colorLight;
        this.nativeBackgroundDark = colorDark;
        return this;
    }

    private void loadNativeAd() {

        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {

            nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);

            admobNativeAd = activity.findViewById(R.id.admob_native_ad_container);
            mediaView = activity.findViewById(R.id.media_view);
            admobNativeBackground = activity.findViewById(R.id.background);

            adManagerNativeAd = activity.findViewById(R.id.google_ad_manager_native_ad_container);
            adManagerMediaView = activity.findViewById(R.id.ad_manager_media_view);
            adManagerNativeBackground = activity.findViewById(R.id.ad_manager_background);

            yandexNativeAd = activity.findViewById(R.id.yandex_native_ad_container);

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
            }

        }

    }

    private void loadBackupNativeAd() {

        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {

            nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);

            admobNativeAd = activity.findViewById(R.id.admob_native_ad_container);
            mediaView = activity.findViewById(R.id.media_view);
            admobNativeBackground = activity.findViewById(R.id.background);

            adManagerNativeAd = activity.findViewById(R.id.google_ad_manager_native_ad_container);
            adManagerMediaView = activity.findViewById(R.id.ad_manager_media_view);
            adManagerNativeBackground = activity.findViewById(R.id.ad_manager_background);

            yandexNativeAd = activity.findViewById(R.id.yandex_native_ad_container);

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
        nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setRadius(activity.getResources().getDimensionPixelOffset(cornerRadius));
    }

    private void setNativeAdStrokeWidth() {
        nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setStrokeWidth(activity.getResources().getDimensionPixelOffset(strokeWidth));
    }

    private void setNativeAdStrokeColor() {
        nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);
        nativeAdViewContainer.setStrokeColor(ContextCompat.getColor(activity, strokeColor));
    }

    private void setNativeAdPadding(int left, int top, int right, int bottom) {
        nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);
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
        nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);
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

