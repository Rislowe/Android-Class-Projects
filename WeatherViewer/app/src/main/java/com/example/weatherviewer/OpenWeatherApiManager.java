package com.example.weatherviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenWeatherApiManager {
    private String city;

    public OpenWeatherApiManager(String city)
    {
        this.city = city;
    }

    public WeatherData getWeather() throws ApiException
    {
        JSONObject json = makeRequest();

        WeatherData data = parseJSON(json);

        return data;
    }

    private JSONObject makeRequest() throws ApiException
    {
        final String apiKey = "0c2e3195ca21ad8950b32419c7a08a28";
        final String route = "http://api.openweathermap.org/data/2.5/weather";

        String urlString = route + "?q=" + city + "&apiKey=" + apiKey + "&units=metric";

        HttpURLConnection urlConnection;

        JSONObject json = null;

        try
        {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inStream = urlConnection.getInputStream();

            BufferedReader bReader = new BufferedReader((
                    new InputStreamReader(inStream)
            ));

            String temp;

            StringBuilder response = new StringBuilder();

            while((temp = bReader.readLine()) != null)
            {
                response.append(temp);
            }

            json = (JSONObject) new JSONTokener(response.toString()).nextValue();
            Log.e(MainActivity.LOG_KEY, json.toString());

        } catch (IOException | JSONException e) {
            Log.e(MainActivity.LOG_KEY, e.getClass().getName() + " : " + e.getMessage());
            throw new ApiException("Unable to process request");
        }

        return json;
    }

    private WeatherData parseJSON(JSONObject json) throws ApiException
    {
        if(json==null)
        {
            Log.e(MainActivity.LOG_KEY, "parseJson parameter is null");
            return null;
        }

        WeatherData data = null;
        try
        {
            JSONObject main = json.getJSONObject("main");
            int currentTemp = main.getInt("temp");
            int highTemp = main.getInt("temp_max");
            int lowTemp = main.getInt("temp_min");
            int feelTemp = main.getInt("feels_like");

            JSONArray weather = json.getJSONArray("weather");

            String description = weather.getJSONObject(0).getString("description");

            JSONObject wind = json.getJSONObject("wind");

            int windSpeed = wind.getInt("speed");

            data = new WeatherData(
                    city,
                    currentTemp,
                    highTemp,
                    lowTemp,
                    description,
                    windSpeed,
                    feelTemp
            );


        } catch (JSONException e) {
            Log.e(MainActivity.LOG_KEY, e.getClass().getName() + " : " + e.getMessage());
            throw new ApiException("Unable to process JSON data");
        }
        return data;
    }
}
