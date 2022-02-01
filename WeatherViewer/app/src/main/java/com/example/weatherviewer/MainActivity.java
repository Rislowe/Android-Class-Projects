package com.example.weatherviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnWeatherRequestCompleted {

    public static final String WEATHER_KEY = "WEATHER_DATA";
    public static final String LOG_KEY = "WeatherViewer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        AppDataManager appDataManager = new AppDataManager(MainActivity.this);

        ImageButton searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchText = findViewById(R.id.searchText);
                String city = searchText.getText().toString();
                appDataManager.saveCity(city);

                WeatherRequest task = new WeatherRequest(MainActivity.this);
                task.execute(city);
            }
        });

        String city = appDataManager.getCity();

        if(city!=null && !city.isEmpty())
        {
            WeatherRequest task = new WeatherRequest(MainActivity.this);
            task.execute(city);
        }
    }

    @Override
    public void onTaskCompleted(WeatherData data) {

        Bundle args = new Bundle();
        args.putParcelable(WEATHER_KEY, data);

        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.weatherFragment, weatherDetails).commit();
    }

    @Override
    public void onTaskFailed() {
        String text = getResources().getString(R.string.search_error);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(MainActivity.this, text, duration);
        toast.show();
    }

    public static class WeatherRequest extends AsyncTask<String, Void, WeatherData>
    {
        private OnWeatherRequestCompleted listener;

        public WeatherRequest(OnWeatherRequestCompleted listener)
        {
            this.listener = listener;
        }

        @Override
        protected WeatherData doInBackground(String... strings) {
            String city = strings[0];

            //call out to weather api to get real data
            WeatherData data = null;

            OpenWeatherApiManager apiManager = new OpenWeatherApiManager(city);
            try
            {
                data = apiManager.getWeather();
            }
            catch(ApiException e)
            {
                Log.e(LOG_KEY, e.getClass().getName() + " : " + e.getMessage());
            }


            return data;
        }

        @Override
        protected void onPostExecute(WeatherData data)
        {
            if(data!=null)
            {
                listener.onTaskCompleted(data);
            }
            else
            {
                listener.onTaskFailed();
            }
        }
    }
}