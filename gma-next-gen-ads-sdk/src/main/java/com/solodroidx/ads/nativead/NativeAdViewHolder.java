package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.google.android.libraries.ads.mobile.sdk.nativead.MediaView;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoader;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoaderCallback;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdRequest;
import com.google.android.material.card.MaterialCardView;
import com.solodroidx.ads.R;
import com.solodroidx.ads.util.AdManagerTemplateView;
import com.solodroidx.ads.util.NativeTemplateStyle;
import com.solodroidx.ads.util.TemplateView;

// NEXT-GEN SDK IMPORTS

public class NativeAdViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "AdNetwork";
    MaterialCardView nativeAdViewContainer;

    // AdMob
    MediaView mediaView;
    TemplateView admobNativeAd;
    LinearLayout admobNativeBackground;

    // Ad Manager
    MediaView adManagerMediaView;
    AdManagerTemplateView adManagerNativeAd;
    LinearLayout adManagerNativeBackground;

    // Parameter Builder
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

    // Styling Parameters
    private int nativeBackgroundLight = R.color.color_native_background_light;
    private int nativeBackgroundDark = R.color.color_native_background_dark;
    private int cornerRadius = 0;
    private int strokeWidth = 0;
    private int strokeColor = android.R.color.transparent;

    public NativeAdViewHolder(View view) {
        super(view);
        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);

        // AdMob
        admobNativeAd = view.findViewById(R.id.admob_native_ad_container);
        mediaView = view.findViewById(R.id.media_view);
        admobNativeBackground = view.findViewById(R.id.background);

        // Ad Manager
        adManagerNativeAd = view.findViewById(R.id.google_ad_manager_native_ad_container);
        adManagerMediaView = view.findViewById(R.id.ad_manager_media_view);
        adManagerNativeBackground = view.findViewById(R.id.ad_manager_background);
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

    // --- KUMPULAN SETTER BUILDER ---
    public NativeAdViewHolder setPadding(Context context, int left, int top, int right, int bottom) { setNativeAdPadding(context, left, top, right, bottom); return this; }
    public NativeAdViewHolder setMargin(Context context, int left, int top, int right, int bottom) { setNativeAdMargin(context, left, top, right, bottom); return this; }
    public NativeAdViewHolder setRadius(Context context, int cornerRadius) { this.cornerRadius = cornerRadius; setNativeAdCornerRadius(context); return this; }
    public NativeAdViewHolder setStrokeWidth(Context context, int strokeWidth) { this.strokeWidth = strokeWidth; setNativeAdStrokeWidth(context); return this; }
    public NativeAdViewHolder setStrokeColor(Context context, int strokeColor) { this.strokeColor = strokeColor; setNativeAdStrokeColor(context); return this; }
    public NativeAdViewHolder setAdStatus(String adStatus) { this.adStatus = adStatus; return this; }
    public NativeAdViewHolder setAdNetwork(String adNetwork) { this.adNetwork = adNetwork; return this; }
    public NativeAdViewHolder setBackupAdNetwork(String backupAdNetwork) { this.backupAdNetwork = backupAdNetwork; return this; }
    public NativeAdViewHolder setAdMobNativeId(String adMobNativeId) { this.adMobNativeId = adMobNativeId; return this; }
    public NativeAdViewHolder setAdManagerNativeId(String adManagerNativeId) { this.adManagerNativeId = adManagerNativeId; return this; }
    public NativeAdViewHolder setFanNativeId(String fanNativeId) { this.fanNativeId = fanNativeId; return this; }
    public NativeAdViewHolder setAppLovinNativeId(String appLovinNativeId) { this.appLovinNativeId = appLovinNativeId; return this; }
    public NativeAdViewHolder setAppLovinDiscoveryMrecZoneId(String id) { this.appLovinDiscMrecZoneId = id; return this; }
    public NativeAdViewHolder setWortiseNativeId(String wortiseNativeId) { this.wortiseNativeId = wortiseNativeId; return this; }
    public NativeAdViewHolder setPangleNativeId(String pangleNativeId) { this.pangleNativeId = pangleNativeId; return this; }
    public NativeAdViewHolder setHuaweiNativeId(String huaweiNativeId) { this.huaweiNativeId = huaweiNativeId; return this; }
    public NativeAdViewHolder setYandexNativeId(String yandexNativeId) { this.yandexNativeId = yandexNativeId; return this; }
    public NativeAdViewHolder setPlacementStatus(int placementStatus) { this.placementStatus = placementStatus; return this; }
    public NativeAdViewHolder setDarkTheme(boolean darkTheme) { this.darkTheme = darkTheme; return this; }
    public NativeAdViewHolder setNativeAdStyle(String nativeAdStyle) { this.nativeAdStyle = nativeAdStyle; return this; }
    public NativeAdViewHolder setBackgroundColor(int colorLight, int colorDark) { this.nativeBackgroundLight = colorLight; this.nativeBackgroundDark = colorDark; return this; }

    // --- LOGIC UTAMA ---

    private boolean isAdConfigValid() {
        return adStatus.equals(AD_STATUS_ON) && placementStatus != 0;
    }

    private void loadNativeAd(Context context) {
        if (!isAdConfigValid()) return;
        routeNativeRequest(context, adNetwork, false);
    }

    private void loadBackupNativeAd(Context context) {
        if (!isAdConfigValid() || backupAdNetwork.equals(NONE)) {
            if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
            return;
        }
        routeNativeRequest(context, backupAdNetwork, true);
    }

    private void routeNativeRequest(Context context, String targetNetwork, boolean isBackup) {
        switch (targetNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
                loadNextGenNative(context, adMobNativeId, targetNetwork, isBackup, false);
                break;
            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                loadNextGenNative(context, adManagerNativeId, targetNetwork, isBackup, true);
                break;
            case NONE:
                if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
                break;
        }
    }

    private void loadNextGenNative(Context context, String adUnitId, String networkName, boolean isBackup, boolean isAdManager) {
        if (adUnitId == null || adUnitId.isEmpty()) {
            if (!isBackup) loadBackupNativeAd(context);
            return;
        }

        View targetAdView = isAdManager ? adManagerNativeAd : admobNativeAd;
        if (targetAdView != null && targetAdView.getVisibility() == View.VISIBLE) {
            Log.d(TAG, networkName + " Native Ad in ViewHolder is already loaded.");
            return;
        }

        java.util.List<NativeAd.NativeAdType> adTypes = java.util.Arrays.asList(NativeAd.NativeAdType.NATIVE);
        NativeAdRequest adRequest = new NativeAdRequest.Builder(adUnitId, adTypes).build();

        NativeAdLoader.load(adRequest, new NativeAdLoaderCallback() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                // Gunakan Handler MainLooper karena di RecyclerView kita mungkin tidak memegang referensi Activity
                new Handler(Looper.getMainLooper()).post(() -> {
                    applyStylesAndPopulate(context, nativeAd, isAdManager);
                    Log.d(TAG, networkName + " Native Loaded on RecyclerView (" + (isBackup ? "Backup" : "Main") + ")");
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Log.e(TAG, networkName + " Native Failed on RecyclerView: " + e.getMessage());
                    if (targetAdView != null) targetAdView.setVisibility(View.GONE);

                    if (!isBackup) {
                        loadBackupNativeAd(context);
                    } else {
                        if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onAdLoadingCompleted() {}
        });
    }

    private void applyStylesAndPopulate(Context context, NativeAd nativeAd, boolean isAdManager) {
        int bgColor = darkTheme ? nativeBackgroundDark : nativeBackgroundLight;
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context, bgColor));
        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(colorDrawable).build();

        if (isAdManager && adManagerNativeAd != null) {
            adManagerNativeAd.setStyles(styles);
            if (adManagerNativeBackground != null) adManagerNativeBackground.setBackgroundResource(bgColor);
            if (adManagerMediaView != null) adManagerMediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            adManagerNativeAd.setNativeAd(nativeAd);
            adManagerNativeAd.setVisibility(View.VISIBLE);
        } else if (admobNativeAd != null) {
            admobNativeAd.setStyles(styles);
            if (admobNativeBackground != null) admobNativeBackground.setBackgroundResource(bgColor);
            if (mediaView != null) mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            admobNativeAd.setNativeAd(nativeAd);
            admobNativeAd.setVisibility(View.VISIBLE);
        }

        if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.VISIBLE);
    }

    // --- STYLE MODIFIERS ---

    private void setNativeAdCornerRadius(Context context) {
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setRadius(context.getResources().getDimensionPixelOffset(cornerRadius));
        }
    }

    private void setNativeAdStrokeWidth(Context context) {
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setStrokeWidth(context.getResources().getDimensionPixelOffset(strokeWidth));
        }
    }

    private void setNativeAdStrokeColor(Context context) {
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setStrokeColor(ContextCompat.getColor(context, strokeColor));
        }
    }

    private void setNativeAdPadding(Context context, int left, int top, int right, int bottom) {
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setContentPadding(
                    context.getResources().getDimensionPixelSize(left),
                    context.getResources().getDimensionPixelSize(top),
                    context.getResources().getDimensionPixelSize(right),
                    context.getResources().getDimensionPixelSize(bottom)
            );
            int bgColor = darkTheme ? nativeBackgroundDark : nativeBackgroundLight;
            nativeAdViewContainer.setCardBackgroundColor(ContextCompat.getColor(context, bgColor));
        }
    }

    private void setNativeAdMargin(Context context, int left, int top, int right, int bottom) {
        if (nativeAdViewContainer != null) {
            setMargins(context, nativeAdViewContainer, left, top, right, bottom);
        }
    }

    private void setMargins(Context context, View view, int left, int top, int right, int bottom) {
        if (view != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
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
}