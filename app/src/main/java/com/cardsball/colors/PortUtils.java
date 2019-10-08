package com.cardsball.colors;

import android.content.Context;
import android.content.SharedPreferences;

class PortUtils {
    private static String port = "port";
    private SharedPreferences preferences;

    PortUtils(Context context){
        String NAME = "port";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    void setPort(String data){
        preferences.edit().putString(PortUtils.port, data).apply();
    }

    String getPort(){
        return preferences.getString(port, "");
    }
}
