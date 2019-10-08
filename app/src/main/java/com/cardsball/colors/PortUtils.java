package com.cardsball.colors;

import android.content.Context;
import android.content.SharedPreferences;

public class PortUtils {
    private static String port = "port";
    private SharedPreferences preferences;

    public PortUtils(Context context){
        String NAME = "port";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setPort(String data){
        preferences.edit().putString(PortUtils.port, data).apply();
    }

    public String getPort(){
        return preferences.getString(port, "");
    }
}
