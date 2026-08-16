package com.solodroidx.ads.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.solodroidx.ads.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LicenseChecker {

    private static final String TAG = "LicenseChecker";

    public static void check(Context context) {
        String currentActivityName = context.getClass().getSimpleName();
        String currentAppId = context.getPackageName();

        // 1. Initial check log
        if (!currentActivityName.equals("MainActivity")) {
            Log.d(TAG, "Canceling check for [" + currentAppId + "]. Current activity (" + currentActivityName + ") is not MainActivity.");
            return;
        }

        Log.d(TAG, "Starting license check for app: [" + currentAppId + "]");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                URL url = new URL(Tools.decode("WVVoU01HTklUVFpNZVRsNVdWaGpkVm95YkRCaFNGWnBaRmhPYkdOdFRuWmlibEpzWW01UmRWa3lPWFJNTTA1MllrYzVhMk50T1hCYVIxWXlUREpXZFdSdFJqQmllVGwwV1Zkc2RVd3lTakZsVjFaNVRESktjMWxYVG5KaVIyeDZaRU0xY1dNeU9YVT0="));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Log.d(TAG, "[" + currentAppId + "] Successfully downloaded JSON data from the server.");

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONObject defaultConfig = jsonObject.getJSONObject("defaultConfig");
                JSONArray blacklistData = jsonObject.getJSONArray("blacklistData");

                boolean isAppBlocked = false;
                String specificPurchaseUrl = "";

                // Find the app status in the JSON
                for (int i = 0; i < blacklistData.length(); i++) {
                    JSONObject item = blacklistData.getJSONObject(i);

                    if (item.getString("applicationId").equals(currentAppId) && item.getBoolean("isBlocked")) {
                        isAppBlocked = true;
                        specificPurchaseUrl = item.getString("purchaseUrl");
                        break;
                    }
                }

                // Evaluate the result
                if (isAppBlocked) {
                    Log.e(TAG, "STATUS: BLOCKED! The app [" + currentAppId + "] is in the blacklist.");

                    String title = defaultConfig.getString("title");
                    String message = defaultConfig.getString("message");
                    String appealUrl = defaultConfig.getString("appealFormUrl");

                    final String finalPurchaseUrl = (specificPurchaseUrl != null && !specificPurchaseUrl.isEmpty()) ? specificPurchaseUrl : "";

                    handler.post(() -> {
                        showFullScreenBlockScreen(context, title, message, finalPurchaseUrl, appealUrl, currentAppId);
                    });
                } else {
                    Log.i(TAG, "STATUS: SAFE. The app [" + currentAppId + "] is not found in the blacklist or block status is false.");
                }

            } catch (Exception e) {
                // Log if there's a network error or JSON parsing issue
                Log.e(TAG, "FAILED to check license for [" + currentAppId + "]: " + e.getMessage());
            }
        });
    }

    private static void showFullScreenBlockScreen(Context context, String title, String message, String purchaseUrl, String appealUrl, String currentAppId) {

        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            Activity activity = (Activity) context;

            LayoutInflater inflater = LayoutInflater.from(activity);
            View blockView = inflater.inflate(R.layout.view_license_block, null);

            TextView tvTitle = blockView.findViewById(R.id.tv_block_title);
            TextView tvMessage = blockView.findViewById(R.id.tv_block_message);
            View btnBuy = blockView.findViewById(R.id.btn_buy_license);
            View btnAppeal = blockView.findViewById(R.id.btn_appeal);

            tvTitle.setText(title);
            tvMessage.setText(Html.fromHtml(message));

            if (purchaseUrl.isEmpty()) {
                btnBuy.setVisibility(View.GONE);
            } else {
                btnBuy.setOnClickListener(v -> {
                    Log.d(TAG, "[" + currentAppId + "] User clicked the buy license button.");
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(purchaseUrl)));
                    activity.finish();
                });
            }

            btnAppeal.setOnClickListener(v -> {
                Log.d(TAG, "[" + currentAppId + "] User clicked the appeal license button.");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(appealUrl));
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(activity, "No web browser installed.", Toast.LENGTH_SHORT).show();
                }
                activity.finish();
            });

            ViewGroup rootView = activity.findViewById(android.R.id.content);
            rootView.addView(blockView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            Log.d(TAG, "[" + currentAppId + "] Block warning screen successfully displayed (Injected to UI).");
        } else {
            Log.w(TAG, "[" + currentAppId + "] Failed to display warning screen: Context is not an Activity or Activity is finishing.");
        }
    }
}