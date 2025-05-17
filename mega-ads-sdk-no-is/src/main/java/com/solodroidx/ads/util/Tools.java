package com.solodroidx.ads.util;

import static com.solodroidx.ads.util.Constant.TOKEN;
import static com.solodroidx.ads.util.Constant.VALUE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.solodroidx.ads.gdpr.LegacyGDPR;
import com.yandex.mobile.ads.banner.BannerAdSize;

import java.nio.charset.StandardCharsets;

public class Tools {

    public static AdSize getAdSize(Activity activity) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static BannerAdSize getYandexAdaptiveInlineBannerAdSize(Activity activity, View view) {
        final DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        final int screenHeight = Math.round(displayMetrics.heightPixels / displayMetrics.density);
        // Calculate the width of the ad, taking into account the padding in the ad container.
        int adWidthPixels = view.getWidth();
        if (adWidthPixels == 0) {
            // If the ad hasn't been laid out, default to the full screen width
            adWidthPixels = displayMetrics.widthPixels;
        }
        final int adWidth = Math.round(adWidthPixels / displayMetrics.density);
        // Determine the maximum allowable ad height. The current value is given as an example.
        final int maxAdHeight = screenHeight / 12;

        return BannerAdSize.inlineSize(activity, adWidth, maxAdHeight);
    }

    public static BannerAdSize getYandexAdaptiveStickyBannerAdSize(Activity activity, View view) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return BannerAdSize.stickySize(activity, adWidth);
    }

    public static AdSize getAdSizeMREC() {
        return AdSize.MEDIUM_RECTANGLE;
    }

    public static AdRequest getAdRequest(Activity activity, Boolean legacyGDPR) {
        //Bundle extras = new FacebookExtras().setNativeBanner(true).build();
        if (legacyGDPR) {
            return new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, LegacyGDPR.getBundleAd(activity))
                    //.addNetworkExtrasBundle(FacebookAdapter.class, extras)
                    .build();
        } else {
            return new AdRequest.Builder()
                    //.addNetworkExtrasBundle(FacebookAdapter.class, extras)
                    .build();
        }
    }

    public static AdRequest getAdRequest(boolean isCollapsibleBanner) {
        if (isCollapsibleBanner) {
            Bundle extras = new Bundle();
            extras.putString("collapsible", "bottom");
            return new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
        } else {
            return new AdRequest.Builder().build();
        }
    }

    @SuppressLint("VisibleForTests")
    public static AdManagerAdRequest getGoogleAdManagerRequest() {
        return new AdManagerAdRequest.Builder()
                .build();
    }

    public static String decode(String code) {
        return decodeBase64(decodeBase64(decodeBase64(code)));
    }

    public static String decodeBase64(String code) {
        byte[] valueDecoded = Base64.decode(code.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    public static String jsonDecode(String code) {
        String data = code.replace(TOKEN, VALUE);
        byte[] valueDecoded = Base64.decode(data.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

}
