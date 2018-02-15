package com.example.german.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowWeather extends AppCompatActivity {
    public final static String SHOW_WEATHER_KEY = new String();
    Integer iconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        Intent intent = getIntent();
        //Получаем данные из интента
        if (intent != null) {
            String message = intent.getStringExtra(SHOW_WEATHER_KEY);
            TextView textView1 = findViewById(R.id.textViewTemperatureContent);
            TextView textView2 = findViewById(R.id.textViewPressureContent);

            if (message.contains(";")) {
                String[] arrayMessage = message.split(";");
                textView1.setText(arrayMessage[0] + getString(R.string.gradus));
                textView2.setText(arrayMessage[1] + getString(R.string.bar));
//                ->>>> так и не получилось разобраться, чтобы  можно было найти погодную иконку по переданным через интент данным
                Integer iconId = Integer.valueOf(arrayMessage[2]);
                ImageView myImageView = findViewById(R.id.imageViewWeatherIcon);
                myImageView.setImageResource(iconId);
//                Toast.makeText(getBaseContext(), "Content = " + iconId, Toast.LENGTH_LONG).show();
            }
            //Убираем вью если строка содержит 888
            if (message.contains("888")) {
                View pressureLayout = findViewById(R.id.pressureLayout);
                pressureLayout.setVisibility(LinearLayout.GONE);
            }
            //Убираем вью если строка содержит 777
            if (message.contains("777")) {
                View weathericon = findViewById(R.id.imageViewWeatherIcon);
                weathericon.setVisibility(ImageView.GONE);
            }
        }
    }
}

