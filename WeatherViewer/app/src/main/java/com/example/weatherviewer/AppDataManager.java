package com.example.weatherviewer;

import android.content.Context;
import android.content.SharedPreferences;

public class AppDataManager {

    private static final String APP_KEY = "WeatherViewer";
    private static final String CITY_KEY = "city";

    private SharedPreferences sharedPreferences;

    public AppDataManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(
                APP_KEY,
                Context.MODE_PRIVATE
        );
    }

    public void saveCity(String city)
    {
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.clear();

        edit.putString(CITY_KEY, city);
        edit.apply();
    }

    public String getCity()
    {
        String city = sharedPreferences.getString(CITY_KEY, "");
        return city;
    }
}
