package com.example.weatherviewer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class WeatherDetails extends Fragment {

    public WeatherDetails() {
        // Required empty public constructor
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        WeatherData data = getArguments().getParcelable(MainActivity.WEATHER_KEY);

        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);

        TextView cityTextView = view.findViewById(R.id.cityTextView);

        TextView currentTempTextView = view.findViewById(R.id.currentTempTextView);

        TextView highTempTextView = view.findViewById(R.id.highTempTextView);

        TextView lowTempTextView = view.findViewById(R.id.lowTempTextView);

        TextView descriptionTextView = view.findViewById(R.id.weatherDescriptionTextView);

        TextView windSpeedTextView = view.findViewById(R.id.windSpeedTextView);

        TextView feelsLikeTextView = view.findViewById(R.id.feelsLikeTextView);

        if(data != null)
        {
            cityTextView.setText(data.getCity());

            currentTempTextView.setText(
                    String.format(
                            getResources().getString(R.string.temp_celsius),
                            data.getCurrentTemp()
                    )
            );

            highTempTextView.setText(
                    String.format(
                            getResources().getString(R.string.temp_celsius),
                            data.getHighTemp()
                    )
            );

            lowTempTextView.setText(
                    String.format(
                            getResources().getString(R.string.temp_celsius),
                            data.getLowTemp()
                    )
            );

            descriptionTextView.setText(data.getDescription());

            windSpeedTextView.setText(
                    String.format(
                            getResources().getString(R.string.speed_kmh),
                            data.getWindSpeed()
                    )
            );

            feelsLikeTextView.setText(
                    String.format(
                            getResources().getString(R.string.temp_celsius),
                            data.getFeelTemp()
                    )
            );

        }

        return view;
    }
}