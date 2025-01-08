package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.PANGLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGImageItem;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGMediaView;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAd;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdLoadListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeRequest;
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

    FrameLayout pangleNativeAd;

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

            pangleNativeAd = view.findViewById(R.id.pangle_native_ad_container);

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

            pangleNativeAd = view.findViewById(R.id.pangle_native_ad_container);

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

}

