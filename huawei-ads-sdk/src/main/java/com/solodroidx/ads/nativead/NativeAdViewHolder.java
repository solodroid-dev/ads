package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.HUAWEI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.material.card.MaterialCardView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BiddingParam;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;
import com.solodroidx.ads.R;
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

    //Huawei
    FrameLayout huaweiNativeAd;

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

        //Huawei
        huaweiNativeAd = view.findViewById(R.id.huawei_native_ad_container);

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

                    case HUAWEI:
                        if (huaweiNativeAd.getVisibility() != View.VISIBLE) {
                            NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, huaweiNativeId);
                            @SuppressLint("InflateParams") NativeAdLoader nativeAdLoader = builder.setNativeAdLoadedListener(nativeAd -> {
                                        NativeView nativeView;
                                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        switch (nativeAdStyle) {
                                            case Constant.STYLE_NEWS:
                                            case Constant.STYLE_MEDIUM:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_news_template_view, null);
                                                break;
                                            case Constant.STYLE_VIDEO_SMALL:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_video_small_template_view, null);
                                                break;
                                            case Constant.STYLE_VIDEO_LARGE:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_video_large_template_view, null);
                                                break;
                                            case Constant.STYLE_RADIO:
                                            case Constant.STYLE_SMALL:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_radio_template_view, null);
                                                break;
                                            default:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_medium_template_view, null);
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
                                            loadBackupNativeAd(context);
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

                    case HUAWEI:
                        if (huaweiNativeAd.getVisibility() != View.VISIBLE) {
                            NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, huaweiNativeId);
                            @SuppressLint("InflateParams") NativeAdLoader nativeAdLoader = builder.setNativeAdLoadedListener(nativeAd -> {
                                        NativeView nativeView;
                                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        switch (nativeAdStyle) {
                                            case Constant.STYLE_NEWS:
                                            case Constant.STYLE_MEDIUM:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_news_template_view, null);
                                                break;
                                            case Constant.STYLE_VIDEO_SMALL:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_video_small_template_view, null);
                                                break;
                                            case Constant.STYLE_VIDEO_LARGE:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_video_large_template_view, null);
                                                break;
                                            case Constant.STYLE_RADIO:
                                            case Constant.STYLE_SMALL:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_radio_template_view, null);
                                                break;
                                            default:
                                                nativeView = (NativeView) inflater.inflate(R.layout.gnt_huawei_medium_template_view, null);
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

}