package com.example.onix.trainjsonwheaterapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String apiKey = "&appid=7fa21c492d858f5b840f7d1b5036425c";
    private EditText location, country, temperature, humidity, pressure;
    private HandleJSON obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = (EditText) findViewById(R.id.locationInput);
        country = (EditText) findViewById(R.id.countryInput);
        temperature = (EditText) findViewById(R.id.temperatureInput);
        humidity = (EditText) findViewById(R.id.humidityInput);
        pressure = (EditText) findViewById(R.id.pressureInput);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    public void open(View view) {
        String url = location.getText().toString();
        String finalUrl = apiUrl + url + apiKey;
        country.setText(finalUrl);
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON();

        while (obj.parsingComplete) ;
        country.setText(obj.getCountry());
        temperature.setText(obj.getTemperature());
        humidity.setText(obj.getHumidity());
        pressure.setText(obj.getPressure());

    }
}
