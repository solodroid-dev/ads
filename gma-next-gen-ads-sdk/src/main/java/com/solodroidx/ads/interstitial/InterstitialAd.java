package com.solodroidx.ads.interstitial;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.AD_STATUS_ON;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroidx.ads.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.NONE;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest;
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdEventCallback;
import com.solodroidx.ads.listener.OnInterstitialAdDismissedListener;

// NEXT-GEN SDK IMPORTS

public class InterstitialAd {

    private static final String TAG = "AdNetwork";
    private final Activity activity;

    // Di Next-Gen SDK, AdMob dan GAM menggunakan instance InterstitialAd yang sama.
    // Kita tidak perlu memisahkan adMobInterstitialAd dan adManagerInterstitialAd.
    private com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd mInterstitialAd;

    private int counter = 1;

    // Parameter Builder
    private String adStatus = "";
    private String adNetwork = "";
    private String backupAdNetwork = "";
    private String adMobInterstitialId = "";
    private String googleAdManagerInterstitialId = "";

    // ID Network Lain
    private String fanInterstitialId = "";
    private String unityInterstitialId = "";
    private String appLovinInterstitialId = "";
    private String appLovinInterstitialZoneId = "";
    private String mopubInterstitialId = "";
    private String ironSourceInterstitialId = "";
    private String wortiseInterstitialId = "";
    private String alienAdsInterstitialId = "";
    private String pangleInterstitialId = "";
    private String huaweiInterstitialId = "";
    private String yandexInterstitialId = "";

    private int placementStatus = 1;
    private int interval = 3;
    private boolean legacyGDPR = false;
    private boolean withListener = false;
    private OnInterstitialAdDismissedListener onInterstitialAdDismissedListener;

    public InterstitialAd(Activity activity) {
        this.activity = activity;
    }

    public InterstitialAd build() {
        loadInterstitialAd();
        return this;
    }

    public void show() {
        showInterstitialAd();
    }

    // --- KUMPULAN SETTER BUILDER (Tidak diubah agar backward compatible) ---
    public InterstitialAd setAdStatus(String adStatus) {
        this.adStatus = adStatus;
        return this;
    }

    public InterstitialAd setAdNetwork(String adNetwork) {
        this.adNetwork = adNetwork;
        return this;
    }

    public InterstitialAd setBackupAdNetwork(String backupAdNetwork) {
        this.backupAdNetwork = backupAdNetwork;
        return this;
    }

    public InterstitialAd setAdMobInterstitialId(String adMobInterstitialId) {
        this.adMobInterstitialId = adMobInterstitialId;
        return this;
    }

    public InterstitialAd setGoogleAdManagerInterstitialId(String googleAdManagerInterstitialId) {
        this.googleAdManagerInterstitialId = googleAdManagerInterstitialId;
        return this;
    }

    public InterstitialAd setFanInterstitialId(String fanInterstitialId) {
        this.fanInterstitialId = fanInterstitialId;
        return this;
    }

    public InterstitialAd setUnityInterstitialId(String unityInterstitialId) {
        this.unityInterstitialId = unityInterstitialId;
        return this;
    }

    public InterstitialAd setAppLovinInterstitialId(String appLovinInterstitialId) {
        this.appLovinInterstitialId = appLovinInterstitialId;
        return this;
    }

    public InterstitialAd setAppLovinInterstitialZoneId(String appLovinInterstitialZoneId) {
        this.appLovinInterstitialZoneId = appLovinInterstitialZoneId;
        return this;
    }

    public InterstitialAd setMopubInterstitialId(String mopubInterstitialId) {
        this.mopubInterstitialId = mopubInterstitialId;
        return this;
    }

    public InterstitialAd setIronSourceInterstitialId(String ironSourceInterstitialId) {
        this.ironSourceInterstitialId = ironSourceInterstitialId;
        return this;
    }

    public InterstitialAd setWortiseInterstitialId(String wortiseInterstitialId) {
        this.wortiseInterstitialId = wortiseInterstitialId;
        return this;
    }

    public InterstitialAd setAlienAdsInterstitialId(String alienAdsInterstitialId) {
        this.alienAdsInterstitialId = alienAdsInterstitialId;
        return this;
    }

    public InterstitialAd setPangleInterstitialId(String pangleInterstitialId) {
        this.pangleInterstitialId = pangleInterstitialId;
        return this;
    }

    public InterstitialAd setHuaweiInterstitialId(String huaweiInterstitialId) {
        this.huaweiInterstitialId = huaweiInterstitialId;
        return this;
    }

    public InterstitialAd setYandexInterstitialId(String yandexInterstitialId) {
        this.yandexInterstitialId = yandexInterstitialId;
        return this;
    }

    public InterstitialAd setPlacementStatus(int placementStatus) {
        this.placementStatus = placementStatus;
        return this;
    }

    public InterstitialAd setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public InterstitialAd setLegacyGDPR(boolean legacyGDPR) {
        this.legacyGDPR = legacyGDPR;
        return this;
    }

    public InterstitialAd setWithListener(boolean withListener, OnInterstitialAdDismissedListener listener) {
        this.withListener = withListener;
        this.onInterstitialAdDismissedListener = listener;
        return this;
    }

    // --- LOGIC UTAMA ---

    private boolean isAdConfigValid() {
        return adStatus.equals(AD_STATUS_ON) && placementStatus != 0;
    }

    private void loadInterstitialAd() {
        if (!isAdConfigValid()) return;
        routeInterstitialRequest(adNetwork, false);
    }

    private void loadBackupInterstitialAd() {
        if (!isAdConfigValid() || backupAdNetwork.equals(NONE)) return;
        routeInterstitialRequest(backupAdNetwork, true);
    }

    /**
     * Memusatkan routing network agar tidak mengulang kode (DRY)
     */
    private void routeInterstitialRequest(String targetNetwork, boolean isBackup) {
        switch (targetNetwork) {
            case ADMOB:
            case FAN_BIDDING_ADMOB:
                loadNextGenInterstitial(adMobInterstitialId, targetNetwork, isBackup);
                break;
            case GOOGLE_AD_MANAGER:
            case FAN_BIDDING_AD_MANAGER:
                loadNextGenInterstitial(googleAdManagerInterstitialId, targetNetwork, isBackup);
                break;
            case NONE:
                Log.d(TAG, "Network Interstitial " + (isBackup ? "Backup" : "Utama") + " diset ke NONE.");
                break;
            // TODO: Tambahkan load method untuk network lain di sini nantinya
        }
    }

    /**
     * Core function untuk memuat Interstitial menggunakan GMA Next-Gen SDK
     */
    private void loadNextGenInterstitial(String adUnitId, String networkName, boolean isBackup) {
        if (adUnitId == null || adUnitId.isEmpty()) {
            Log.e(TAG, "Ad Unit ID kosong untuk: " + networkName);
            if (!isBackup) loadBackupInterstitialAd();
            return;
        }

        AdRequest adRequest = new AdRequest.Builder(adUnitId).build();

        // Panggil class InterstitialAd dari Next-Gen SDK secara spesifik
        // untuk menghindari bentrok dengan class InterstitialAd buatan kita
        com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd.load(
                adRequest,
                new AdLoadCallback<com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd>() {

                    @Override
                    public void onAdLoaded(@NonNull com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, networkName + " Interstitial Loaded (" + (isBackup ? "Backup" : "Utama") + ")");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                        Log.e(TAG, networkName + " Interstitial Failed: " + loadAdError.getMessage());
                        if (!isBackup) {
                            loadBackupInterstitialAd();
                        }
                    }
                });
    }

    /**
     * Logic untuk menampilkan Iklan. Karena Main dan Backup sekarang menggunakan
     * variabel mInterstitialAd yang sama, logic tampilnya bisa digabung total.
     */
    private void showInterstitialAd() {
        if (!isAdConfigValid()) return;

        if (counter >= interval) {
            if (mInterstitialAd != null) {
                // Pasang callback tepat sebelum iklan ditampilkan
                mInterstitialAd.setAdEventCallback(new InterstitialAdEventCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mInterstitialAd = null; // Hapus referensi agar tidak memory leak
                        loadInterstitialAd(); // Muat ulang iklan untuk sesi berikutnya

                        if (withListener && onInterstitialAdDismissedListener != null) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull FullScreenContentError error) {
                        mInterstitialAd = null;
                        Log.e(TAG, "The ad failed to show: " + error.getMessage());

                        if (withListener && onInterstitialAdDismissedListener != null) {
                            onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                        }
                    }
                });

                mInterstitialAd.show(activity);
                Log.d(TAG, "Menampilkan Interstitial Ad");

            } else {
                // Iklan Utama dan Backup gagal dimuat (atau null)
                Log.d(TAG, "Interstitial Ad null (gagal dimuat semua)");
                if (withListener && onInterstitialAdDismissedListener != null) {
                    onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                }
            }
            // Reset counter setelah mencapai interval (baik tampil maupun gagal)
            counter = 1;
        } else {
            counter++;
            Log.d(TAG, "Current counter: " + counter + " / " + interval);
        }
    }
}