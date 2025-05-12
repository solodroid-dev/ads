package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.APPODEAL;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.ads.nativead.NativeAdView;
import com.appodeal.ads.nativead.NativeAdViewAppWall;
import com.appodeal.ads.nativead.NativeAdViewContentStream;
import com.appodeal.ads.nativead.NativeAdViewNewsFeed;
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

import java.util.List;

@SuppressWarnings("deprecation")
public class NativeAd {

    private static final String TAG = "AdNetworkNative";
    private final Activity activity;
    MaterialCardView nativeAdViewContainer;

    MediaView mediaView;
    TemplateView admobNativeAd;
    LinearLayout admobNativeBackground;

    MediaView adManagerMediaView;
    AdManagerTemplateView adManagerNativeAd;
    LinearLayout adManagerNativeBackground;

    RelativeLayout appodealNativeAd;
    NativeAdViewNewsFeed nativeAdViewNewsFeed;
    NativeAdViewAppWall nativeAdViewAppWall;
    NativeAdViewContentStream nativeAdViewContentStream;
//    boolean isNativeAdLoaded = false;

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

            appodealNativeAd = activity.findViewById(R.id.appodeal_native_ad_container);
            nativeAdViewNewsFeed = activity.findViewById(R.id.appodeal_native_news_feed);
            nativeAdViewAppWall = activity.findViewById(R.id.appodeal_native_app_wall);
            nativeAdViewContentStream = activity.findViewById(R.id.appodeal_native_content_stream);

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

                case APPODEAL:
                    if (appodealNativeAd.getVisibility() != View.VISIBLE) {
                        new Handler(Looper.getMainLooper()).postDelayed(this::showNativeAdIfAvailable, 5000);
                        switch (nativeAdStyle) {
                            case Constant.STYLE_NEWS:
                            case Constant.STYLE_MEDIUM:
                            case Constant.STYLE_VIDEO_SMALL:
                                appodealNativeAd.addView(new NativeAdViewAppWall(activity));
                                break;
                            case Constant.STYLE_RADIO:
                            case Constant.STYLE_SMALL:
                                appodealNativeAd.addView(new NativeAdViewNewsFeed(activity));
                                break;
                            default:
                                appodealNativeAd.addView(new NativeAdViewContentStream(activity));
                                break;
                        }
                        Appodeal.setNativeCallbacks(nativeCallbacks);
                    } else {
                        Log.d(TAG, "Appodeal Native Ad has been loaded");
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

            appodealNativeAd = activity.findViewById(R.id.appodeal_native_ad_container);
            nativeAdViewNewsFeed = activity.findViewById(R.id.appodeal_native_news_feed);
            nativeAdViewAppWall = activity.findViewById(R.id.appodeal_native_app_wall);
            nativeAdViewContentStream = activity.findViewById(R.id.appodeal_native_content_stream);

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

                case APPODEAL:
                    if (appodealNativeAd.getVisibility() != View.VISIBLE) {
                        switch (nativeAdStyle) {
                            case Constant.STYLE_NEWS:
                            case Constant.STYLE_MEDIUM:
                            case Constant.STYLE_VIDEO_SMALL:
                                appodealNativeAd.addView(new NativeAdViewAppWall(activity));
                                break;
                            case Constant.STYLE_RADIO:
                            case Constant.STYLE_SMALL:
                                appodealNativeAd.addView(new NativeAdViewNewsFeed(activity));
                                break;
                            default:
                                appodealNativeAd.addView(new NativeAdViewContentStream(activity));
                                break;
                        }
                        Appodeal.setNativeCallbacks(new NativeCallbacks() {
                            @Override
                            public void onNativeLoaded() {
                                switch (nativeAdStyle) {
                                    case Constant.STYLE_NEWS:
                                    case Constant.STYLE_MEDIUM:
                                    case Constant.STYLE_VIDEO_SMALL:
                                        nativeAdViewAppWall.registerView(Appodeal.getNativeAds(1).get(0));
                                        nativeAdViewAppWall.setVisibility(View.VISIBLE);
                                        break;
                                    case Constant.STYLE_RADIO:
                                    case Constant.STYLE_SMALL:
                                        nativeAdViewNewsFeed.registerView(Appodeal.getNativeAds(1).get(0));
                                        nativeAdViewNewsFeed.setVisibility(View.VISIBLE);
                                        break;
                                    default:
                                        nativeAdViewContentStream.registerView(Appodeal.getNativeAds(1).get(0));
                                        nativeAdViewContentStream.setVisibility(View.VISIBLE);
                                        break;
                                }
                                appodealNativeAd.setVisibility(View.VISIBLE);
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                                Log.d(TAG, "Appodeal Native Ad loaded");
                            }

                            @Override
                            public void onNativeFailedToLoad() {
                                Log.d(TAG, "Appodeal Native Ad failed");
                            }

                            @Override
                            public void onNativeShowFailed(com.appodeal.ads.NativeAd nativeAd) {

                            }

                            @Override
                            public void onNativeShown(com.appodeal.ads.NativeAd nativeAd) {

                            }

                            @Override
                            public void onNativeClicked(com.appodeal.ads.NativeAd nativeAd) {

                            }

                            @Override
                            public void onNativeExpired() {

                            }
                        });
                    } else {
                        Log.d(TAG, "Appodeal Native Ad has been loaded");
                    }
                    break;

                default:
                    nativeAdViewContainer.setVisibility(View.GONE);
                    break;
            }

        }

    }

    public void destroyNativeAd() {
//        if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
//            if (adNetwork.equals(APPODEAL)) {
//                switch (nativeAdStyle) {
//                    case Constant.STYLE_NEWS:
//                    case Constant.STYLE_MEDIUM:
//                    case Constant.STYLE_VIDEO_SMALL:
//                        appodealNativeAd.removeAllViews();
//                        nativeAdViewAppWall.destroy();
//                        nativeAdViewAppWall.unregisterView();
//                        Log.d(TAG, "Destroy Appodeal Native Ad Medium");
//                        break;
//                    case Constant.STYLE_RADIO:
//                    case Constant.STYLE_SMALL:
//                        appodealNativeAd.removeAllViews();
//                        nativeAdViewNewsFeed.destroy();
//                        nativeAdViewNewsFeed.unregisterView();
//                        Log.d(TAG, "Destroy Appodeal Native Ad Small");
//                        break;
//                    default:
//                        appodealNativeAd.removeAllViews();
//                        nativeAdViewContentStream.destroy();
//                        nativeAdViewContentStream.unregisterView();
//                        Appodeal.setNativeCallbacks(null);
//                        Log.d(TAG, "Destroy Appodeal Native Ad Default");
//                        break;
//                }
//            }
//        }
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

    NativeCallbacks nativeCallbacks = new NativeCallbacks() {
        @Override
        public void onNativeLoaded() {
//            if (!isNativeAdLoaded) {
                showNativeAdIfAvailable();
//            }
        }

        @Override
        public void onNativeFailedToLoad() {
            Log.d(TAG, "Appodeal onNativeFailedToLoad");
        }

        @Override
        public void onNativeShown(com.appodeal.ads.NativeAd nativeAd) {
            Log.d(TAG, "Appodeal onNativeShown");
        }

        @Override
        public void onNativeShowFailed(com.appodeal.ads.NativeAd nativeAd) {
            Log.d(TAG, "Appodeal onNativeShowFailed");
        }

        @Override
        public void onNativeClicked(com.appodeal.ads.NativeAd nativeAd) {
            Log.d(TAG, "Appodeal onNativeClicked");
        }

        @Override
        public void onNativeExpired() {
            Log.d(TAG, "Appodeal onNativeExpired");
        }
    };

    public void showNativeAdIfAvailable() {
        int nativeAmount = Appodeal.getAvailableNativeAdsCount();
        if (nativeAmount > 0) {
            List<com.appodeal.ads.NativeAd> nativeAds = Appodeal.getNativeAds(nativeAmount);
            if (nativeAds.get(0).canShow(activity, "default")) {
                switch (nativeAdStyle) {
                    case Constant.STYLE_NEWS:
                    case Constant.STYLE_MEDIUM:
                    case Constant.STYLE_VIDEO_SMALL:
                        nativeAdViewAppWall.registerView(nativeAds.get(0));
                        nativeAdViewAppWall.setVisibility(View.VISIBLE);
                        break;
                    case Constant.STYLE_RADIO:
                    case Constant.STYLE_SMALL:
                        nativeAdViewNewsFeed.registerView(nativeAds.get(0));
                        nativeAdViewNewsFeed.setVisibility(View.VISIBLE);
                        break;
                    default:
                        nativeAdViewContentStream.registerView(nativeAds.get(0));
                        nativeAdViewContentStream.setVisibility(View.VISIBLE);
                        break;
                }
                appodealNativeAd.setVisibility(View.VISIBLE);
                nativeAdViewContainer.setVisibility(View.VISIBLE);
//                isNativeAdLoaded = true;
            } else {
                Log.d(TAG, "Cannot show Native");
            }
        } else {
            Log.d(TAG, "NativeAd is not available");
        }
    }

}

