package com.solodroidx.ads.nativead;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.solodroidx.ads.R;

@SuppressWarnings("deprecation")
public class NativeAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    MaterialCardView nativeAdViewContainer;

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

    }

    private void loadBackupNativeAd() {

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

}
