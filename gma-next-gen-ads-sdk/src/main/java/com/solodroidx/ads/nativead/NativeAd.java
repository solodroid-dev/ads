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
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoader;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoaderCallback;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdRequest;
import com.google.android.material.card.MaterialCardView;
import com.solodroidx.ads.R;
import com.solodroidx.ads.util.AdManagerTemplateView;
import com.solodroidx.ads.util.NativeTemplateStyle;
import com.solodroidx.ads.util.TemplateView;

// NEXT-GEN SDK IMPORTS

public class NativeAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;

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

    public NativeAd(Activity activity) {
        this.activity = activity;
    }

    public NativeAd build() {
        loadNativeAd();
        return this;
    }

    // --- KUMPULAN SETTER BUILDER (Dipertahankan untuk kompatibilitas penuh) ---
    public NativeAd setPadding(int left, int top, int right, int bottom) { setNativeAdPadding(left, top, right, bottom); return this; }
    public NativeAd setMargin(int left, int top, int right, int bottom) { setNativeAdMargin(left, top, right, bottom); return this; }
    public NativeAd setRadius(int cornerRadius) { this.cornerRadius = cornerRadius; setNativeAdCornerRadius(); return this; }
    public NativeAd setStrokeWidth(int strokeWidth) { this.strokeWidth = strokeWidth; setNativeAdStrokeWidth(); return this; }
    public NativeAd setStrokeColor(int strokeColor) { this.strokeColor = strokeColor; setNativeAdStrokeColor(); return this; }
    public NativeAd setAdStatus(String adStatus) { this.adStatus = adStatus; return this; }
    public NativeAd setAdNetwork(String adNetwork) { this.adNetwork = adNetwork; return this; }
    public NativeAd setBackupAdNetwork(String backupAdNetwork) { this.backupAdNetwork = backupAdNetwork; return this; }
    public NativeAd setAdMobNativeId(String adMobNativeId) { this.adMobNativeId = adMobNativeId; return this; }
    public NativeAd setAdManagerNativeId(String adManagerNativeId) { this.adManagerNativeId = adManagerNativeId; return this; }
    public NativeAd setFanNativeId(String fanNativeId) { this.fanNativeId = fanNativeId; return this; }
    public NativeAd setAppLovinNativeId(String appLovinNativeId) { this.appLovinNativeId = appLovinNativeId; return this; }
    public NativeAd setAppLovinDiscoveryMrecZoneId(String id) { this.appLovinDiscMrecZoneId = id; return this; }
    public NativeAd setWortiseNativeId(String wortiseNativeId) { this.wortiseNativeId = wortiseNativeId; return this; }
    public NativeAd setAlienAdsNativeId(String alienAdsNativeId) { this.alienAdsNativeId = alienAdsNativeId; return this; }
    public NativeAd setPangleNativeId(String pangleNativeId) { this.pangleNativeId = pangleNativeId; return this; }
    public NativeAd setHuaweiNativeId(String huaweiNativeId) { this.huaweiNativeId = huaweiNativeId; return this; }
    public NativeAd setYandexNativeId(String yandexNativeId) { this.yandexNativeId = yandexNativeId; return this; }
    public NativeAd setPlacementStatus(int placementStatus) { this.placementStatus = placementStatus; return this; }
    public NativeAd setDarkTheme(boolean darkTheme) { this.darkTheme = darkTheme; return this; }
    public NativeAd setLegacyGDPR(boolean legacyGDPR) { this.legacyGDPR = legacyGDPR; return this; }
    public NativeAd setNativeAdStyle(String nativeAdStyle) { this.nativeAdStyle = nativeAdStyle; return this; }
    public NativeAd setBackgroundColor(int colorLight, int colorDark) { this.nativeBackgroundLight = colorLight; this.nativeBackgroundDark = colorDark; return this; }

    // --- LOGIC UTAMA ---

    private void initViews() {
        if (nativeAdViewContainer == null) {
            nativeAdViewContainer = activity.findViewById(R.id.native_ad_view_container);
            admobNativeAd = activity.findViewById(R.id.admob_native_ad_container);
            mediaView = activity.findViewById(R.id.media_view);
            admobNativeBackground = activity.findViewById(R.id.background);

            adManagerNativeAd = activity.findViewById(R.id.google_ad_manager_native_ad_container);
            adManagerMediaView = activity.findViewById(R.id.ad_manager_media_view);
            adManagerNativeBackground = activity.findViewById(R.id.ad_manager_background);
        }
    }

    private boolean isAdConfigValid() {
        return adStatus.equals(AD_STATUS_ON) && placementStatus != 0;
    }

    private void loadNativeAd() {
        if (!isAdConfigValid()) return;
        initViews();
        routeNativeRequest(adNetwork, false);
    }

    private void loadBackupNativeAd() {
        if (!isAdConfigValid() || backupAdNetwork.equals(NONE)) {
            if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
            return;
        }
        initViews();
        routeNativeRequest(backupAdNetwork, true);
    }

    /**
     * Memusatkan routing network
     */
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
     * @param isAdManager Digunakan untuk menentukan View Layout mana yang harus diubah (AdMob vs AdManager)
     */
    private void loadNextGenNative(String adUnitId, String networkName, boolean isBackup, boolean isAdManager) {
        if (adUnitId == null || adUnitId.isEmpty()) {
            if (!isBackup) loadBackupNativeAd();
            return;
        }

        // Cek apakah iklan sudah di-load dan terlihat untuk mencegah load ulang berlebih
        View targetAdView = isAdManager ? adManagerNativeAd : admobNativeAd;
        if (targetAdView != null && targetAdView.getVisibility() == View.VISIBLE) {
            Log.d(TAG, networkName + " Native Ad is already loaded and visible.");
            return;
        }

        java.util.List<com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd.NativeAdType> adTypes = java.util.Arrays.asList(com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd.NativeAdType.NATIVE);
        NativeAdRequest adRequest = new NativeAdRequest.Builder(adUnitId, adTypes).build();

        NativeAdLoader.load(adRequest, new NativeAdLoaderCallback() {
            @Override
            public void onNativeAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd nativeAd) {
                // Next-Gen callback kadang berjalan di background thread
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
                        // Jika backup juga gagal, sembunyikan container
                        if (nativeAdViewContainer != null) nativeAdViewContainer.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onAdLoadingCompleted() {}
        });
    }

    /**
     * Mengatur tema dan menempelkan NativeAd object ke TemplateView
     */
    private void applyStylesAndPopulate(com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd nativeAd, boolean isAdManager) {
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