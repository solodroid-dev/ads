package com.solodroidx.ads.gdpr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm.OnConsentFormDismissedListener;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentInformation.PrivacyOptionsRequirementStatus;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Google Mobile Ads SDK provides the User Messaging Platform (Google's IAB Certified consent
 * management platform) as one solution to capture consent for users in GDPR impacted countries.
 * This is an example and you can choose another consent management platform to capture consent.
 */

public class GoogleMobileAdsConsentManager {

    private static GoogleMobileAdsConsentManager instance;
    private final ConsentInformation consentInformation;
    ConsentRequestParameters params;

    /**
     * Private constructor
     */
    private GoogleMobileAdsConsentManager(Context context) {
        this.consentInformation = UserMessagingPlatform.getConsentInformation(context);
    }

    /**
     * Public constructor
     */
    public static GoogleMobileAdsConsentManager getInstance(Context context) {
        if (instance == null) {
            instance = new GoogleMobileAdsConsentManager(context);
        }

        return instance;
    }

    /**
     * Interface definition for a callback to be invoked when consent gathering is complete.
     */
    public interface OnConsentGatheringCompleteListener {
        void consentGatheringComplete(FormError error);
    }

    /**
     * Helper variable to determine if the app can request ads.
     */
    public boolean canRequestAds() {
        return consentInformation.canRequestAds();
    }

    // [START is_privacy_options_required]

    /**
     * Helper variable to determine if the privacy options form is required.
     */
    public boolean isPrivacyOptionsRequired() {
        return consentInformation.getPrivacyOptionsRequirementStatus()
                == PrivacyOptionsRequirementStatus.REQUIRED;
    }

    // [END is_privacy_options_required]

    /**
     * Helper method to call the UMP SDK methods to request consent information and load/present a
     * consent form if necessary.
     */
    @SuppressLint("HardwareIds")
    public void gatherConsent(Activity activity, boolean isDebug, boolean childDirected, OnConsentGatheringCompleteListener onConsentGatheringCompleteListener) {
        if (isDebug) {
            String androidId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            String deviceId = md5(androidId).toUpperCase();
            // For testing purposes, you can force a DebugGeography of EEA or NOT_EEA.
            ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(activity)
                    .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                    .addTestDeviceHashedId(deviceId)
                    .build();
            params = new ConsentRequestParameters.Builder()
                    .setConsentDebugSettings(debugSettings)
                    .setTagForUnderAgeOfConsent(childDirected)
                    .build();
        } else {
            params = new ConsentRequestParameters.Builder()
                    .setTagForUnderAgeOfConsent(childDirected)
                    .build();
        }
        // [START request_consent_info_update]
        // Requesting an update to consent information should be called on every app launch.
        // Called when there's an error updating consent information.
        // [START_EXCLUDE silent]
        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                () -> // Called when consent information is successfully updated.
                        // [START_EXCLUDE silent]
                        loadAndShowConsentFormIfRequired(activity, onConsentGatheringCompleteListener),
                // [END_EXCLUDE]
                onConsentGatheringCompleteListener::consentGatheringComplete);
        // [END_EXCLUDE]
        // [END request_consent_info_update]
    }

    private void loadAndShowConsentFormIfRequired(
            Activity activity, OnConsentGatheringCompleteListener onConsentGatheringCompleteListener) {
        // [START load_and_show_consent_form]
        // Consent gathering process is complete.
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                activity,
                onConsentGatheringCompleteListener::consentGatheringComplete);
        // [END load_and_show_consent_form]
    }

    /**
     * Helper method to call the UMP SDK method to present the privacy options form.
     */
    public void showPrivacyOptionsForm(Activity activity, OnConsentFormDismissedListener onConsentFormDismissedListener) {
        // [START present_privacy_options_form]
        UserMessagingPlatform.showPrivacyOptionsForm(activity, onConsentFormDismissedListener);
        // [END present_privacy_options_form]
    }

    public static String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            //Logger.logStackTrace(TAG,e);
        }
        return "";
    }

}