package com.solodroid.ads.sdkdemo.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.solodroid.ads.sdkdemo.R;
import com.solodroid.ads.sdkdemo.application.MyApplication;
import com.solodroid.ads.sdkdemo.callback.CallbackConfig;
import com.solodroid.ads.sdkdemo.data.Constant;
import com.solodroid.ads.sdkdemo.database.SharedPref;
import com.solodroid.ads.sdkdemo.helper.AdsManager;
import com.solodroid.ads.sdkdemo.rest.RestAdapter;
import com.solodroidx.ads.appopen.AppOpenAd;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ConstantConditions")
public class ActivitySplash extends AppCompatActivity {

    private static final String TAG = "ActivitySplash";
    Call<CallbackConfig> callbackConfigCall = null;
    AdsManager adsManager;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPref = new SharedPref(this);
        adsManager = new AdsManager(this);
        adsManager.initializeAd();
        requestConfig();
    }

    private void requestConfig() {
        requestAPI("https://raw.githubusercontent.com/solodroidev/content/uploads/json/android.json");
    }

    private void requestAPI(@SuppressWarnings("SameParameterValue") String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            if (url.contains("https://drive.google.com")) {
                String driveUrl = url.replace("https://", "").replace("http://", "");
                List<String> data = Arrays.asList(driveUrl.split("/"));
                String googleDriveFileId = data.get(3);
                callbackConfigCall = RestAdapter.createApi().getDriveJsonFileId(googleDriveFileId);
            } else {
                callbackConfigCall = RestAdapter.createApi().getJsonUrl(url);
            }
        } else {
            callbackConfigCall = RestAdapter.createApi().getDriveJsonFileId(url);
        }
        callbackConfigCall.enqueue(new Callback<CallbackConfig>() {
            public void onResponse(@NonNull Call<CallbackConfig> call, @NonNull Response<CallbackConfig> response) {
                CallbackConfig resp = response.body();
                if (resp != null) {
                    sharedPref.savePostList(resp.android);
                    showAppOpenAdIfAvailable();
                    Log.d(TAG, "responses success");
                } else {
                    showAppOpenAdIfAvailable();
                    Log.d(TAG, "responses null");
                }
            }

            public void onFailure(@NonNull Call<CallbackConfig> call, @NonNull Throwable th) {
                Log.d(TAG, "responses failed: " + th.getMessage());
                showAppOpenAdIfAvailable();
            }
        });
    }

    private void showAppOpenAdIfAvailable() {
        if (Constant.FORCE_TO_SHOW_APP_OPEN_AD_ON_START) {
            if (Constant.OPEN_ADS_ON_START) {
                adsManager.loadAppOpenAds(Constant.OPEN_ADS_ON_START, true, ()-> {
                    startMainActivity();
                    AppOpenAd.isAppOpenAdLoaded = false;
                });
            } else {
                startMainActivity();
            }
        } else {
            if (Constant.AD_STATUS.equals("1") && Constant.OPEN_ADS_ON_START) {
                Application application = getApplication();
                ((MyApplication) application).showAdIfAvailable(this, this::startMainActivity);
            } else {
                startMainActivity();
            }
        }
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
