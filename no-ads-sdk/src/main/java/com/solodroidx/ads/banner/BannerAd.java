package com.solodroidx.ads.banner;

import android.app.Activity;

@SuppressWarnings("deprecation")
public class BannerAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;

    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobBannerId = "";
    private String googleAdManagerBannerId = "";
    private String fanBannerId = "";
    private String unityBannerId = "";
    private String appLovinBannerId = "";
    private String appLovinBannerZoneId = "";
    private String ironSourceBannerId = "";
    private String wortiseBannerId = "";
    private String alienAdsBannerId = "";
    private String pangleBannerId = "";
    private String huaweiBannerId = "";
    private String yandexBannerId = "";
    private int placementStatus = 1;
    private boolean darkTheme = false;
    private boolean legacyGDPR = false;
    private boolean collapsibleBanner = false;

    public BannerAd(Activity activity) {
        this.activity = activity;
    }

    public BannerAd build() {
        loadBannerAd();
        return this;
    }

    public BannerAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public BannerAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public BannerAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public BannerAd setAdMobBannerId(String adMobBannerId) {
        this.adMobBannerId = adMobBannerId;
        return this;
    }

    public BannerAd setGoogleAdManagerBannerId(String googleAdManagerBannerId) {
        this.googleAdManagerBannerId = googleAdManagerBannerId;
        return this;
    }

    public BannerAd setFanBannerId(String fanBannerId) {
        this.fanBannerId = fanBannerId;
        return this;
    }

    public BannerAd setUnityBannerId(String unityBannerId) {
        this.unityBannerId = unityBannerId;
        return this;
    }

    public BannerAd setAppLovinBannerId(String appLovinBannerId) {
        this.appLovinBannerId = appLovinBannerId;
        return this;
    }

    public BannerAd setAppLovinBannerZoneId(String appLovinBannerZoneId) {
        this.appLovinBannerZoneId = appLovinBannerZoneId;
        return this;
    }

    public BannerAd setIronSourceBannerId(String ironSourceBannerId) {
        this.ironSourceBannerId = ironSourceBannerId;
        return this;
    }

    public BannerAd setWortiseBannerId(String wortiseBannerId) {
        this.wortiseBannerId = wortiseBannerId;
        return this;
    }

    public BannerAd setAlienAdsBannerId(String alienAdsBannerId) {
        this.alienAdsBannerId = alienAdsBannerId;
        return this;
    }

    public BannerAd setPangleBannerId(String pangleBannerId) {
        this.pangleBannerId = pangleBannerId;
        return this;
    }

    public BannerAd setHuaweiBannerId(String huaweiBannerId) {
        this.huaweiBannerId = huaweiBannerId;
        return this;
    }

    public BannerAd setYandexBannerId(String yandexBannerId) {
        this.yandexBannerId = yandexBannerId;
        return this;
    }

    public BannerAd setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public BannerAd setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
        return this;
    }

    public BannerAd setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public BannerAd setIsCollapsibleBanner(boolean collapsibleBanner) {
        this.collapsibleBanner = collapsibleBanner;
        return this;
    }

    public void loadBannerAd() {

    }

    public void loadBackupBannerAd() {

    }

    public void destroyAndDetachBanner() {

    }

}
