package com.solodroidx.ads.gdpr;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GDPR {

    public static final String TAG = "GDPR";
    private final Activity activity;
    private ConsentInformation consentInformation;

    public GDPR(Activity activity) {
        this.activity = activity;
        this.consentInformation = UserMessagingPlatform.getConsentInformation(activity);
    }

    /**
     * Memperbarui status persetujuan (Consent) secara standar
     */
    public void updateGDPRConsentStatus() {
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();

        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                () -> {
                    // API UMP terbaru: Memuat dan langsung menampilkan formulir jika diperlukan
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            activity,
                            formError -> {
                                if (formError != null) {
                                    Log.w(TAG, "GDPR Form Error: " + formError.getMessage());
                                }
                            }
                    );
                },
                formError -> Log.w(TAG, "GDPR Request Error: " + formError.getMessage())
        );
        Log.d(TAG, "AdMob GDPR status update requested.");
    }

    /**
     * Memperbarui status persetujuan dengan opsi Debug dan Child-Directed (COPPA)
     */
    @SuppressLint("HardwareIds")
    public void updateGDPRConsentStatus(String adType, boolean isDebug, boolean childDirected) {
        if (!adType.equals(ADMOB) && !adType.equals(GOOGLE_AD_MANAGER)) {
            return;
        }

        ConsentRequestParameters.Builder paramsBuilder = new ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(childDirected);

        if (isDebug) {
            // Pengaturan debug berguna saat masa testing di area luar Eropa (EEA)
            ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(activity)
                    .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                    .build();
            paramsBuilder.setConsentDebugSettings(debugSettings);
        }

        consentInformation.requestConsentInfoUpdate(
                activity,
                paramsBuilder.build(),
                () -> {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            activity,
                            formError -> {
                                if (formError != null) {
                                    Log.w(TAG, "GDPR Form Error: " + formError.getMessage());
                                }
                                // CATATAN PENTING NEXT-GEN SDK:
                                // SDK Inisialisasi tidak lagi dilakukan di sini.
                                // Gunakan class AdMobProvider Anda yang memanggil MobileAds.initialize(activity, config, ...)
                                // setelah proses consent ini selesai atau di class pemanggilnya.
                            }
                    );
                },
                formError -> Log.w(TAG, "GDPR Request Error: " + formError.getMessage())
        );
    }

    /**
     * Mengecek apakah opsi privasi diwajibkan untuk diubah oleh pengguna
     */
    public boolean isPrivacyOptionsRequired() {
        if (consentInformation == null) {
            consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        }
        return consentInformation.getPrivacyOptionsRequirementStatus() == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED;
    }

    /**
     * Menampilkan menu pengaturan ulang privasi (biasanya dipanggil dari tombol di menu Settings aplikasi)
     */
    public void showPrivacyOptionsForm() {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, formError -> {
            if (formError != null) {
                Log.e(TAG, "Privacy Form Error: " + formError.getMessage());
            } else {
                Log.d(TAG, "Privacy Form Dismissed.");
            }
        });
    }

    /**
     * Utility Hash MD5 (Biasanya digunakan untuk mendaftarkan Test Device ID)
     */
    public static String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String h = Integer.toHexString(0xFF & b);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "MD5 error", e);
        }
        return "";
    }
}