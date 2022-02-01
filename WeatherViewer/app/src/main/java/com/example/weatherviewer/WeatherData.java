package com.example.weatherviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable {

    private String city;
    private int currentTemp;
    private int highTemp;
    private int lowTemp;
    private String description;
    private int windSpeed;
    private int feelTemp;

    public WeatherData (
            String city,
            int currentTemp,
            int highTemp,
            int lowTemp,
            String description,
            int windSpeed,
            int feelTemp
    )
    {
        this.city = city;
        this.currentTemp = currentTemp;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
        this.windSpeed = windSpeed;
        this.feelTemp = feelTemp;
    }

    public String getCity()
    {
        return city;
    }

    public int getCurrentTemp()
    {
        return currentTemp;
    }

    public int getHighTemp()
    {
        return highTemp;
    }

    public int getLowTemp()
    {
        return lowTemp;
    }

    public String getDescription()
    {
        return description;
    }

    public int getWindSpeed(){
        int convertedValue = (int) (windSpeed * 3.6); //converts m/s value to km/hour
        return convertedValue;
    }

    public int getFeelTemp(){ return feelTemp; }

    protected WeatherData(Parcel in) {
        city = in.readString();
        currentTemp = in.readInt();
        highTemp = in.readInt();
        lowTemp = in.readInt();
        description = in.readString();
        windSpeed = in.readInt();
        feelTemp = in.readInt();
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeInt(currentTemp);
        parcel.writeInt(highTemp);
        parcel.writeInt(lowTemp);
        parcel.writeString(description);
        parcel.writeInt(windSpeed);
        parcel.writeInt(feelTemp);
    }
}
