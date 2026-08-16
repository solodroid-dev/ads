package com.solodroidx.ads.nativead;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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

public class NativeAdView {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    private View view; // View root dari Fragment atau ViewHolder

    // View Containers
    private MaterialCardView nativeAdViewContainer;
    private MediaView mediaView;
    private TemplateView admobNativeAd;
    private LinearLayout admobNativeBackground;
    private MediaView adManagerMediaView;
    private AdManagerTemplateView adManagerNativeAd;
    private LinearLayout adManagerNativeBackground;

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
    private String alienAdsNativeId = "";
    private String pangleNativeId = "";
    private String huaweiNativeId = "";
    private String yandexNativeId = "";
    private int placementStatus = 1;
    private boolean darkTheme = false;
    private boolean legacyGDPR = false;

    // Styling Parameters
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

    // --- KUMPULAN SETTER BUILDER ---
    public NativeAdView setView(View view) { this.view = view; return this; }
    public NativeAdView setPadding(int left, int top, int right, int bottom) { setNativeAdPadding(left, top, right, bottom); return this; }
    public NativeAdView setMargin(int left, int top, int right, int bottom) { setNativeAdMargin(left, top, right, bottom); return this; }
    public NativeAdView setRadius(int cornerRadius) { this.cornerRadius = cornerRadius; setNativeAdCornerRadius(); return this; }
    public NativeAdView setStrokeWidth(int strokeWidth) { this.strokeWidth = strokeWidth; setNativeAdStrokeWidth(); return this; }
    public NativeAdView setStrokeColor(int strokeColor) { this.strokeColor = strokeColor; setNativeAdStrokeColor(); return this; }
    public NativeAdView setAdStatus(String adStatus) { this.adStatus = adStatus; return this; }
    public NativeAdView setAdNetwork(String adNetwork) { this.adNetwork = adNetwork; return this; }
    public NativeAdView setBackupAdNetwork(String backupAdNetwork) { this.backupAdNetwork = backupAdNetwork; return this; }
    public NativeAdView setAdMobNativeId(String adMobNativeId) { this.adMobNativeId = adMobNativeId; return this; }
    public NativeAdView setAdManagerNativeId(String adManagerNativeId) { this.adManagerNativeId = adManagerNativeId; return this; }
    public NativeAdView setFanNativeId(String fanNativeId) { this.fanNativeId = fanNativeId; return this; }
    public NativeAdView setAppLovinNativeId(String appLovinNativeId) { this.appLovinNativeId = appLovinNativeId; return this; }
    public NativeAdView setAppLovinDiscoveryMrecZoneId(String id) { this.appLovinDiscMrecZoneId = id; return this; }
    public NativeAdView setWortiseNativeId(String wortiseNativeId) { this.wortiseNativeId = wortiseNativeId; return this; }
    public NativeAdView setAlienAdsNativeId(String alienAdsNativeId) { this.alienAdsNativeId = alienAdsNativeId; return this; }
    public NativeAdView setPangleNativeId(String pangleNativeId) { this.pangleNativeId = pangleNativeId; return this; }
    public NativeAdView setHuaweiNativeId(String huaweiNativeId) { this.huaweiNativeId = huaweiNativeId; return this; }
    public NativeAdView setYandexNativeId(String yandexNativeId) { this.yandexNativeId = yandexNativeId; return this; }
    public NativeAdView setPlacementStatus(int placementStatus) { this.placementStatus = placementStatus; return this; }
    public NativeAdView setDarkTheme(boolean darkTheme) { this.darkTheme = darkTheme; return this; }
    public NativeAdView setLegacyGDPR(boolean legacyGDPR) { this.legacyGDPR = legacyGDPR; return this; }
    public NativeAdView setNativeAdStyle(String nativeAdStyle) { this.nativeAdStyle = nativeAdStyle; return this; }
    public NativeAdView setBackgroundColor(int colorLight, int colorDark) { this.nativeBackgroundLight = colorLight; this.nativeBackgroundDark = colorDark; return this; }

    // --- LOGIC UTAMA ---

    private void initViews() {
        if (view == null) {
            Log.e(TAG, "View object is null! Pastikan setView(view) dipanggil sebelum build() atau set padding/margin.");
            return;
        }

        if (nativeAdViewContainer == null) {
            nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);
            admobNativeAd = view.findViewById(R.id.admob_native_ad_container);
            mediaView = view.findViewById(R.id.media_view);
            admobNativeBackground = view.findViewById(R.id.background);

            adManagerNativeAd = view.findViewById(R.id.google_ad_manager_native_ad_container);
            adManagerMediaView = view.findViewById(R.id.ad_manager_media_view);
            adManagerNativeBackground = view.findViewById(R.id.ad_manager_background);
        }
    }

    private boolean isAdConfigValid() {
        return adStatus.equals(AD_STATUS_ON) && placementStatus != 0;
    }

    public void loadNativeAd() {
        if (!isAdConfigValid()) return;
        initViews();
        if (view == null) return;
        routeNativeRequest(adNetwork, false);
    }

    public void loadBackupNativeAd() {
        if (!isAdConfigValid() || backupAdNetwork.equals(NONE)) {
            if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
            return;
        }
        initViews();
        if (view == null) return;
        routeNativeRequest(backupAdNetwork, true);
    }

    private void routeNativeRequest(String targetNetwork, boolean isBackup) {
        switch (targetNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
                loadNextGenNative(adMobNativeId, targetNetwork, isBackup, false);
                break;
            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                loadNextGenNative(adManagerNativeId, targetNetwork, isBackup, true);
                break;
            case NONE:
                if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Core function untuk memuat Native Ads menggunakan GMA Next-Gen SDK
     */
    private void loadNextGenNative(String adUnitId, String networkName, boolean isBackup, boolean isAdManager) {
        if (adUnitId == null || adUnitId.isEmpty()) {
            if (!isBackup) loadBackupNativeAd();
            return;
        }

        View targetAdView = isAdManager ? adManagerNativeAd : admobNativeAd;
        if (targetAdView != null && targetAdView.getVisibility() == View.VISIBLE) {
            Log.d(TAG, networkName + " Native Ad is already loaded and visible.");
            return;
        }

        java.util.List<NativeAd.NativeAdType> adTypes = java.util.Arrays.asList(NativeAd.NativeAdType.NATIVE);
        NativeAdRequest adRequest = new NativeAdRequest.Builder(adUnitId, adTypes).build();

        NativeAdLoader.load(adRequest, new NativeAdLoaderCallback() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                activity.runOnUiThread(() -> {
                    applyStylesAndPopulate(nativeAd, isAdManager);
                    Log.d(TAG, networkName + " Native Loaded (" + (isBackup ? "Backup" : "Main") + ")");
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError e) {
                activity.runOnUiThread(() -> {
                    Log.e(TAG, networkName + " Native Failed: " + e.getMessage());
                    if (targetAdView != null) targetAdView.setVisibility(View.GONE);

                    if (!isBackup) {
                        loadBackupNativeAd();
                    } else {
                        if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onAdLoadingCompleted() {}
        });
    }

    private void applyStylesAndPopulate(NativeAd nativeAd, boolean isAdManager) {
        int bgColor = darkTheme ? nativeBackgroundDark : nativeBackgroundLight;
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, bgColor));
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

    private void setNativeAdCornerRadius() {
        initViews();
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setRadius(activity.getResources().getDimensionPixelOffset(cornerRadius));
        }
    }

    private void setNativeAdStrokeWidth() {
        initViews();
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setStrokeWidth(activity.getResources().getDimensionPixelOffset(strokeWidth));
        }
    }

    private void setNativeAdStrokeColor() {
        initViews();
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setStrokeColor(ContextCompat.getColor(activity, strokeColor));
        }
    }

    private void setNativeAdPadding(int left, int top, int right, int bottom) {
        initViews();
        if (nativeAdViewContainer != null) {
            nativeAdViewContainer.setContentPadding(
                    activity.getResources().getDimensionPixelSize(left),
                    activity.getResources().getDimensionPixelSize(top),
                    activity.getResources().getDimensionPixelSize(right),
                    activity.getResources().getDimensionPixelSize(bottom)
            );
            int bgColor = darkTheme ? nativeBackgroundDark : nativeBackgroundLight;
            nativeAdViewContainer.setCardBackgroundColor(ContextCompat.getColor(activity, bgColor));
        }
    }

    private void setNativeAdMargin(int left, int top, int right, int bottom) {
        initViews();
        if (nativeAdViewContainer != null) {
            setMargins(nativeAdViewContainer, left, top, right, bottom);
        }
    }

    private void setMargins(View targetView, int left, int top, int right, int bottom) {
        if (targetView != null && targetView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) targetView.getLayoutParams();
            p.setMargins(
                    activity.getResources().getDimensionPixelSize(left),
                    activity.getResources().getDimensionPixelSize(top),
                    activity.getResources().getDimensionPixelSize(right),
                    activity.getResources().getDimensionPixelSize(bottom)
            );
            targetView.requestLayout();
        }
    }

}