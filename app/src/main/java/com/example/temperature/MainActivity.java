package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    public static final char DEGREE = '\u00B0';
    public static final String STARTING_URL
            = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String KEY_NAME = "&appid=";
    // private String city = "New York, NY";
    private String city = "London,UK";
    private String key = "6dfabd12c6b5fce8d876ab8fe84e02c9";
    EditText cityEditText;
    TextView tempTextView;
    String inputCity = "";
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler(Looper.getMainLooper());
        cityEditText = (EditText) findViewById(R.id.city);
        tempTextView = (TextView) findViewById(R.id.temp);

        TextChangeHandler tch = new TextChangeHandler();
        cityEditText.addTextChangedListener(tch);
    }

    private class TextChangeHandler implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int
                i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i,
                                  int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            inputCity = cityEditText.getText().toString();

            Handler handler = new Handler(Looper.getMainLooper());
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //Do background here
                    String baseUrl = "", cityString = "", keyName = "", key = "";
                    baseUrl = STARTING_URL;
                    cityString = inputCity;
                    keyName = KEY_NAME;
                    key = MainActivity.this.key;

                    //Create an object RemoteDataReader
                    RemoteDataReader rdr = new RemoteDataReader(baseUrl, cityString, keyName, key);
                    // Get the JSON string
                    json = rdr.getData();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TemperatureParser parser = new TemperatureParser(json);
                            tempTextView.setText(String.valueOf(parser.getTemperatureF()) + DEGREE + "F");

                            Log.w("MainActivity",
                                    String.valueOf(parser.getTemperatureK()) + DEGREE + "K");
                            }
                        }, 4000);
                }
            });
        }
    }

}

