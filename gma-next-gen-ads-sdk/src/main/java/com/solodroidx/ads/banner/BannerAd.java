package com.solodroidx.ads.banner;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.banner.AdSize;
import com.google.android.libraries.ads.mobile.sdk.banner.AdView;
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;

import com.solodroidx.ads.R;

public class BannerAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;
    private AdView adView; // Menggunakan AdView dari Next-Gen SDK

    // Parameter Builder
    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobBannerId = "";
    private String googleAdManagerBannerId = "";

    // ID Network Lain (Bisa dipertahankan jika nanti Anda buat Provider terpisah untuk tiap network)
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

    // --- KUMPULAN SETTER BUILDER (Tetap dipertahankan agar tidak merusak kode di Activity/Fragment) ---
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

    // --- LOGIC UTAMA ---

    public void loadBannerAd() {
        if (!isAdConfigValid()) return;

        Log.d(TAG, "Mencoba memuat Banner Utama: " + adNetwork);
        routeBannerRequest(adNetwork, false);
    }

    private void loadBackupBannerAd() {
        if (!isAdConfigValid() || backupAdNetwork.equals(NONE)) return;

        Log.d(TAG, "Mencoba memuat Banner Backup: " + backupAdNetwork);
        routeBannerRequest(backupAdNetwork, true);
    }

    private boolean isAdConfigValid() {
        if (!adStatus.equals(AD_STATUS_ON) || placementStatus == 0) {
            Log.d(TAG, "Banner Ad dinonaktifkan atau placement status 0");
            return false;
        }
        return true;
    }

    /**
     * Memusatkan routing (pilihan network) agar tidak terjadi pengulangan kode (DRY Principle)
     */
    private void routeBannerRequest(String targetNetwork, boolean isBackup) {
        switch (targetNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
                FrameLayout admobContainer = activity.findViewById(R.id.admob_banner_view_container);
                loadNextGenBanner(admobContainer, adMobBannerId, targetNetwork, isBackup);
                break;

            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                FrameLayout managerContainer = activity.findViewById(R.id.google_ad_banner_view_container);
                // Di Next-Gen SDK, core AdView bisa digunakan untuk GAM juga,
                // tinggal masukkan GAM Unit ID yang sesuai format.
                loadNextGenBanner(managerContainer, googleAdManagerBannerId, targetNetwork, isBackup);
                break;

            case NONE:
                Log.d(TAG, "Network " + (isBackup ? "Backup" : "Utama") + " diset ke NONE.");
                break;

            // TODO: Tambahkan case untuk network lain (AppLovin, Unity) di sini nantinya
            // dengan memanggil fungsi load terpisah, misal: loadAppLovinBanner(...)
        }
    }

    /**
     * Core function untuk memuat iklan menggunakan GMA Next-Gen SDK
     */
    private void loadNextGenBanner(FrameLayout containerView, String adUnitId, String networkName, boolean isBackup) {
        if (containerView == null || adUnitId == null || adUnitId.isEmpty()) {
            Log.e(TAG, "Container View null atau Ad Unit ID kosong untuk: " + networkName);
            if (!isBackup) loadBackupBannerAd();
            return;
        }

        containerView.post(() -> {
            adView = new AdView(activity);

            // Catatan: Jika butuh collapsible, Next-Gen biasanya passing via Bundle di Request Builder
            BannerAdRequest.Builder requestBuilder = new BannerAdRequest.Builder(adUnitId, getAdaptiveAdSize());
            if (collapsibleBanner) {
                // Di Next-Gen SDK, parameter collapsible mungkin berbeda, biasanya di addNetworkExtrasBundle
                // requestBuilder.addNetworkExtrasBundle(AdMobAdapter.class, buildCollapsibleBundle());
            }

            BannerAdRequest adRequest = requestBuilder.build();

            containerView.removeAllViews();
            containerView.addView(adView);

            // Perhatikan penggunaan fully qualified name agar tidak bentrok dengan class BannerAd buatan kita
            adView.loadAd(adRequest, new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.banner.BannerAd>() {
                @Override
                public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.banner.BannerAd ad) {
                    Log.d(TAG, networkName + " Banner Loaded (" + (isBackup ? "Backup" : "Utama") + ")");
                    containerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    Log.e(TAG, networkName + " Banner Failed: " + adError.getMessage());
                    containerView.setVisibility(View.GONE);
                    if (!isBackup) {
                        loadBackupBannerAd(); // Picu backup jika ini adalah pemuatan utama
                    }
                }
            });
        });
    }

    /**
     * Kalkulasi Adaptive Banner Size internal (Menghilangkan ketergantungan dari class Tools lama)
     */
    private AdSize getAdaptiveAdSize() {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float adWidthPixels = displayMetrics.widthPixels;
        float density = displayMetrics.density;
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public void destroyAndDetachBanner() {
        if (adView != null) {
            // Karena kita menggunakan adView tunggal untuk AdMob dan GAM, kita hanya perlu destroy satu.
            adView.destroy();
            adView = null;
        }

        FrameLayout admobContainer = activity.findViewById(R.id.admob_banner_view_container);
        FrameLayout managerContainer = activity.findViewById(R.id.google_ad_banner_view_container);

        if (admobContainer != null) admobContainer.removeAllViews();
        if (managerContainer != null) managerContainer.removeAllViews();

        Log.d(TAG, "Banner views detached and destroyed.");
    }
}