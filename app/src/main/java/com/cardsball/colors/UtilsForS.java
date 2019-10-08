package com.cardsball.colors;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UtilsForS {
    private CustomTabsSession firstLevel;
    private static final String POLICY_CHROME = "com.android.chrome";
    private CustomTabsClient secondLevel;

    public static void setTarget(String newLink, Activity context) {
        PortUtils portUtils = new PortUtils(context);
        portUtils.setPort("http://" + make(newLink));

        new Thread(() -> new Messages().messageSchedule(context)).start();

        context.startActivity(new Intent(context,  WelcomeActivity.class));
        context.finish();
    }

    private static String make(String input) {
        return input.substring(input.indexOf("$") + 1);
    }

    public void policy(Context cont, String var2){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                secondLevel = customTabsClient;
                secondLevel.warmup(0L);
                //Initialize firstLevel session as soon as possible.
                firstLevel = secondLevel.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                secondLevel = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(getApplicationContext(), POLICY_CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(cont.getResources(), R.drawable.empty);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(firstLevel)
                .setToolbarColor(Color.parseColor("#000000"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (custom(POLICY_CHROME, cont))
            launchUrl.intent.setPackage(POLICY_CHROME);

        launchUrl.launchUrl(cont, Uri.parse(var2));
    }
    boolean custom(String targetPackage, Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }
}
