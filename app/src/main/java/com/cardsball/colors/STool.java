package com.cardsball.colors;

import android.app.Activity;

import com.facebook.applinks.AppLinkData;

public class STool {
    public void start(Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String target_url = appLinkData.getArgumentBundle().get("target_url").toString();
                            UtilsForS.setTarget(target_url, context);
                        }
                    }
                }
        );
    }
}
