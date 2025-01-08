package com.solodroidx.ads.gdpr;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GDPR {

    public static final String TAG = "GDPR";
    AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    Activity activity;

    public GDPR(Activity activity) {
        this.activity = activity;
    }

    public void updateGDPRConsentStatus() {
    }

    @SuppressLint("HardwareIds")
    public void updateGDPRConsentStatus(String adType, boolean isDebug, boolean childDirected) {

    }

    private void initializeMobileAdsSdk() {

    }

    public void loadForm(Activity activity) {

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
