package com.solodroid.ads.sdkdemo.activity;

import static com.solodroidx.ads.util.Constant.SDK_TYPE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.solodroid.ads.sdkdemo.R;
import com.solodroid.ads.sdkdemo.data.Constant;
import com.solodroid.ads.sdkdemo.database.SharedPref;
import com.solodroid.ads.sdkdemo.helper.AdsManager;
import com.solodroid.ads.sdkdemo.helper.MaterialProgressDialog;
import com.solodroidx.ads.appopen.AppOpenAd;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Toolbar toolbar;
    MaterialSwitch materialSwitch;
    SharedPref sharedPref;
    Button btnInterstitial;
    Button btnRewarded;
    Button btnRewarded2;
    Button btnNative;
    Button btnSelectAds;
    Button btnNativeAdStyle;
    LinearLayout nativeAdViewContainer;
    LinearLayout bannerAdView;
    AdsManager adsManager;
    MaterialProgressDialog.Builder progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        getAppTheme();
        setContentView(R.layout.activity_main);
        adsManager = new AdsManager(this);
        initView();
        initProgressDialog();
        new Handler(Looper.getMainLooper()).postDelayed(this::loadAds, 250);
        switchAppTheme();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_sub_title)).setText(SDK_TYPE);

        bannerAdView = findViewById(R.id.banner_ad_view);
        bannerAdView.addView(View.inflate(this, R.layout.view_banner_ad, null));

        nativeAdViewContainer = findViewById(R.id.native_ad);

        btnInterstitial = findViewById(R.id.btn_interstitial);
        btnNative = findViewById(R.id.btn_native);
        btnRewarded = findViewById(R.id.btn_rewarded);
        btnRewarded2 = findViewById(R.id.btn_rewarded2);

        btnSelectAds = findViewById(R.id.btn_select_ads);
        btnSelectAds.setText("Selected Ad: " + Constant.AD_NETWORK);

        btnSelectAds.setOnClickListener(v -> showAdChooser());

        btnNativeAdStyle = findViewById(R.id.btn_native_ad_style);
        btnNativeAdStyle.setOnClickListener(v -> changeNativeAdStyle());
    }

    private void initProgressDialog() {
        progressDialog = new MaterialProgressDialog.Builder(this);
        progressDialog.setMessage("Loading ads, please wait...");
        progressDialog.build();
    }

    private void loadAds() {
        adsManager.initializeAd();

        adsManager.updateGdprConsentStatus();
        if (Constant.FORCE_TO_SHOW_APP_OPEN_AD_ON_START) {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(lifecycleObserver);
            adsManager.loadAppOpenAds(Constant.OPEN_ADS_ON_RESUME, false, () -> {
            });
        }

        adsManager.loadBannerAd();

        adsManager.loadInterstitialAd(() -> {
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            Log.d(TAG, "Rawr!!!!!");
        });
        //adsManager.loadRewardedAd();
        setNativeAdStyle(nativeAdViewContainer);
        adsManager.loadNativeAd();

        btnInterstitial.setOnClickListener(v -> {
            //startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            adsManager.showInterstitialAd();
            //adsManager.destroyBannerAd();
        });

        btnRewarded.setOnClickListener(v -> adsManager.showRewardedAd());

        btnRewarded2.setOnClickListener(v -> {
            progressDialog.show();
            adsManager.loadAndShowRewardedAd(
                    () -> {
                        //onRewardedAdLoaded
                        progressDialog.dismiss();
                    }, () -> {
                        //onRewardedAdError
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "rewarded error", Toast.LENGTH_SHORT).show();
                    }, () -> {
                        //onRewardedAdDismissed
                        Toast.makeText(getApplicationContext(), "rewarded dismissed", Toast.LENGTH_SHORT).show();
                    }, () -> {
                        //onRewardedAdComplete
                        Toast.makeText(getApplicationContext(), "rewarded complete", Toast.LENGTH_SHORT).show();
                    }
            );
        });

        btnNative.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            adsManager.destroyBannerAd();
        });
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adsManager.destroyBannerAd();
//        adsManager.destroyNativeAd();
        destroyAppOpenAd();
    }

    @Override
    public void onResume() {
        super.onResume();
        adsManager.resumeBannerAd();
    }

    public void getAppTheme() {
        if (sharedPref.getIsDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    private void switchAppTheme() {
        materialSwitch = findViewById(R.id.switch_theme);
        materialSwitch.setChecked(sharedPref.getIsDarkTheme());
        materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPref.setIsDarkTheme(isChecked);
            relaunchActivity();
        });
    }

    private void relaunchActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @SuppressWarnings("DataFlowIssue")
    private void showAdChooser() {
        String[] ads;
        switch (SDK_TYPE) {
            case "admob-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager"};
                break;
            case "facebook-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "facebook"};
                break;
            case "applovin-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "applovin_max", "applovin_discovery"};
                break;
            case "startapp-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "startapp"};
                break;
            case "unity-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "unity"};
                break;
            case "ironsource-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "ironsource"};
                break;
            case "wortise-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "wortise"};
                break;
            case "pangle-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "pangle"};
                break;
            case "huawei-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "huawei"};
                break;
            case "yandex-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "yandex"};
                break;
            case "appodeal-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "appodeal"};
                break;
            case "no-ads-sdk":
                ads = new String[]{"none"};
                break;
            case "multi-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "facebook", "applovin_max", "applovin_discovery", "startapp", "unity", "ironsource"};
                break;
            case "multi-ads-sdk-no-is":
                ads = new String[]{"admob", "google_ad_manager", "facebook", "applovin_max", "applovin_discovery", "startapp", "unity"};
                break;
            case "mega-ads-sdk":
                ads = new String[]{"admob", "google_ad_manager", "facebook", "applovin_max", "applovin_discovery", "startapp", "unity", "ironsource", "wortise", "pangle", "huawei", "yandex"};
                break;
            case "mega-ads-sdk-no-is":
                ads = new String[]{"admob", "google_ad_manager", "facebook", "applovin_max", "applovin_discovery", "startapp", "unity", "pangle", "huawei", "yandex"};
                break;
            default:
                ads = new String[]{"undefined"};
                break;
        }
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Select Ad");
        builder.setItems(ads, (dialog, position) -> {
            Constant.AD_NETWORK = ads[position];
            relaunchActivity();
            btnSelectAds.setText("Selected Ad: " + Constant.AD_NETWORK);
        });
        builder.show();
    }

    private void setNativeAdStyle(LinearLayout nativeAdView) {
        switch (Constant.NATIVE_STYLE) {
            case "news":
                nativeAdView.addView(View.inflate(this, R.layout.view_native_ad_news, null));
                break;
            case "radio":
                nativeAdView.addView(View.inflate(this, R.layout.view_native_ad_radio, null));
                break;
            case "video_small":
                nativeAdView.addView(View.inflate(this, R.layout.view_native_ad_video_small, null));
                break;
            case "video_large":
                nativeAdView.addView(View.inflate(this, R.layout.view_native_ad_video_large, null));
                break;
            default:
                nativeAdView.addView(View.inflate(this, R.layout.view_native_ad_medium, null));
                break;
        }
    }

    private void changeNativeAdStyle() {
        final String[] styles = {"default", "news", "radio", "video_small", "video_large"};
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Select Native Style");
        builder.setItems(styles, (dialog, position) -> {
            Constant.NATIVE_STYLE = styles[position];
            relaunchActivity();
        });
        builder.show();
    }

    private void showExitDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_exit, null);

        LinearLayout nativeAdViewContainer = view.findViewById(R.id.native_ad_view);
        setNativeAdStyle(nativeAdViewContainer);
        adsManager.loadNativeAdView(view);

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Exit", (dialogInterface, i) -> {
            //super.onBackPressed();
            finish();
            System.exit(0);
            adsManager.destroyBannerAd();
            destroyAppOpenAd();
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    private void destroyAppOpenAd() {
        AppOpenAd.isAppOpenAdLoaded = false;
        if (Constant.FORCE_TO_SHOW_APP_OPEN_AD_ON_START) {
            adsManager.destroyAppOpenAd();
            ProcessLifecycleOwner.get().getLifecycle().removeObserver(lifecycleObserver);
        }
    }

    LifecycleObserver lifecycleObserver = new DefaultLifecycleObserver() {
        @Override
        public void onStart(@NonNull LifecycleOwner owner) {
            DefaultLifecycleObserver.super.onStart(owner);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (Constant.OPEN_ADS_ON_RESUME) {
                    if (AppOpenAd.isAppOpenAdLoaded) {
                        adsManager.showAppOpenAds(() -> {

                        });
                        Log.d(AppOpenAd.TAG, "lifecycleObserver Show App Open Ad");
                    }
                }
            }, 100);
        }
    };

}