package com.solodroidx.ads.gdpr;

import static com.solodroidx.ads.util.Constant.ADMOB;
import static com.solodroidx.ads.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroidx.ads.util.Constant.APPLOVIN_MAX;
import static com.solodroidx.ads.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroidx.ads.util.Constant.PANGLE;
import static com.solodroidx.ads.util.Constant.STARTAPP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.applovin.sdk.AppLovinPrivacySettings;
import com.bytedance.sdk.openadsdk.api.PAGConstant;
import com.bytedance.sdk.openadsdk.api.init.PAGConfig;
import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GDPR {

    public static final String TAG = "GDPR";
    ConsentInformation consentInformation;
    ConsentDebugSettings debugSettings;
    ConsentRequestParameters params;
    AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    ConsentForm consentForm;
    Activity activity;
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    public GDPR(Activity activity) {
        this.activity = activity;
    }

    public void updateGDPRConsentStatus() {
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        consentInformation.requestConsentInfoUpdate(activity, params, () -> {
                    if (consentInformation.isConsentFormAvailable()) {
                        loadForm(activity);
                    }
                },
                formError -> {
                });
        Log.d("GDPR", "AdMob GDPR is selected");
    }

    @SuppressLint("HardwareIds")
    public void updateGDPRConsentStatus(String adType, boolean isDebug, boolean childDirected) {
        switch (adType) {
            case ADMOB:
            case GOOGLE_AD_MANAGER:
                googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(activity);
                googleMobileAdsConsentManager.gatherConsent(
                        activity,
                        isDebug,
                        childDirected,
                        consentError -> {
                            if (consentError != null) {
                                // Consent not obtained in current session.
                                Log.w(TAG, String.format("%s: %s", consentError.getErrorCode(), consentError.getMessage()));
                            }

                            if (googleMobileAdsConsentManager.canRequestAds()) {
                                initializeMobileAdsSdk();
                            }

                            if (googleMobileAdsConsentManager.isPrivacyOptionsRequired()) {
                                // Regenerate the options menu to include a privacy setting.
                                //invalidateOptionsMenu();
                            }
                        });

                // This sample attempts to load ads using consent obtained in the previous session.
                if (googleMobileAdsConsentManager.canRequestAds()) {
                    initializeMobileAdsSdk();
                }
                break;
            case STARTAPP:
                StartAppSDK.setUserConsent(activity, "pas", System.currentTimeMillis(), true);
                break;
            case APPLOVIN_MAX:
                AppLovinPrivacySettings.setHasUserConsent(true, activity);
                //AppLovinPrivacySettings.setIsAgeRestrictedUser(childDirected, activity);
                break;
            case APPLOVIN_DISCOVERY:
                //AppLovinPrivacySettings.setIsAgeRestrictedUser(childDirected, activity);
                AppLovinPrivacySettings.setHasUserConsent(true, activity);
                break;
            case PANGLE:
                PAGConfig.setGDPRConsent(PAGConstant.PAGGDPRConsentType.PAG_GDPR_CONSENT_TYPE_CONSENT);
                PAGConfig.getGDPRConsent();
                break;
        }
    }

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }
        MobileAds.initialize(activity);
    }

    public void loadForm(Activity activity) {
        UserMessagingPlatform.loadConsentForm(activity, consentForm -> {
                    this.consentForm = consentForm;
                    if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(activity, formError -> {
                            loadForm(activity);
                        });
                    }
                },
                formError -> {
                }
        );
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
