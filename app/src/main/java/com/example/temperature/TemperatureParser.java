package com.example.temperature;

import org.json.JSONException;
import org.json.JSONObject;

public class TemperatureParser {
    public static final double ZERO_K = -273.15;
    private final String MAIN_KEY = "main";
    private final String TEMPERATURE_KEY = "temp";

    private JSONObject jsonObject;

    // jsonObject is a JSONObject created from the String in Figure 1

    public TemperatureParser(String json) {
        try {
          jsonObject = new JSONObject(json);
        } catch (JSONException jsonE){

        }
    }

    public double getTemperatureK() {
        try {
            JSONObject jsonMain = jsonObject.getJSONObject("main");
            return jsonMain.getDouble("temp");
            // process temperature here
        } catch (JSONException jsonE){
            return 25 - ZERO_K;
        }
    }

    public double getTemperatureC() {
        return ( int ) ( getTemperatureK() + ZERO_K + 0.5 );
    }

    public double getTemperatureF() {
        return ( int ) ( (getTemperatureK() + ZERO_K)  * 9 / 5 + 32 + 0.5 );
    }
}

