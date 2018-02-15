package com.example.german.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    String[] cities = {"Moscow", "Novgorod", "Saratov"};
    String[] todayTemperature = {"10", "20", "30"};
    String[] todayPressure = {"750", "760", "765"};
    String[] weatherIcon = {"partly_cloudy.png", "snow.png", "snow.png"};
    Button checkWeather;
    Spinner citySpinner;
    CheckBox checkBoxPressure;
    CheckBox checkBoxWeatherIcon;
    Integer citySpinnerSelectedItemPositionInt;
    Integer drawableId;
    Integer checBoxPressureConnection;
    Integer checBoxWeatherConnection;
    String citySpinnerSelectedItemPositionString;
    String choosedCityTemp;
    String choosedCityPressure;
    String choosedCityWeatherIcon;
    String stringResId;



    //Получаем ID картинки погоды
    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }
    // Set support actionbar with toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkBoxPressure = findViewById(R.id.checkBox_pressure);
        checkBoxWeatherIcon = findViewById(R.id.checkBox_weather_icon);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // адаптер

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner = findViewById(R.id.cityspinner);
        citySpinner.setAdapter(adapter);
        citySpinner.setSelection(0);

        // Отправляем позицию спиннера в новое активити SHow weather
        checkWeather = findViewById(R.id.checkweatherbutton);
        checkWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citySpinnerSelectedItemPositionInt = citySpinner.getSelectedItemPosition();
                citySpinnerSelectedItemPositionString = String.valueOf(citySpinnerSelectedItemPositionInt);
                sendMessage();
            }
        });
    }

    // Переопределение метода создания меню.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Метод вызывается по нажатию на любой пункт меню. В качестве агрумента приходит item меню.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        checkBoxPressure = findViewById(R.id.checkBox_pressure);
//        checkBoxWeatherIcon = findViewById(R.id.checkBox_weather_icon);
        // обработка нажатий
        switch (item.getItemId()) {
            case R.id.menu_add:
                return true;
            //проверяем нажатие чекбокса
            case R.id.checkBox_pressure:
                if (item.isChecked())
                    item.setChecked(false);
                else item.setChecked(true);
                return true;
            case R.id.checkBox_weather_icon:
                if (item.isChecked())
                    item.setChecked(false);
                else item.setChecked(true);
                return true;

            case R.id.menu_clear:
                clearList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Метод очищает лист полностью. <--- ТУТ НЕ РАБОТАЕТ!!!! NullpointerExeption
    private void clearList() {
        //снимаем галки чекбоксов
        try {
            if (checkBoxPressure.isChecked())
                checkBoxPressure.setChecked(false);
            else checkBoxPressure.setChecked(true);
            if (checkBoxWeatherIcon.isChecked())
                checkBoxWeatherIcon.setChecked(false);
            else checkBoxWeatherIcon.setChecked(true);
        } catch (Exception e) {
            Log.e("CheckBoxTag", "Failure to set uncheckable.", e);
        }
    }

    // Метод для отправки данных по ключу
    private void sendMessage() {
        Intent intent = new Intent(this, ShowWeather.class);
        //Берем данные  о темепературе по городу + если выделенны чекбоксы то и данные из них
        choosedCityTemp = todayTemperature[citySpinnerSelectedItemPositionInt];
        if (checkBoxPressure.isChecked()) {
            choosedCityPressure = todayPressure[citySpinnerSelectedItemPositionInt];
        } else {
            choosedCityPressure = "888";
        }
        if (checkBoxWeatherIcon.isChecked()) {
            choosedCityWeatherIcon = weatherIcon[citySpinnerSelectedItemPositionInt];
//            try {
//                Class res = R.drawable.class;
//                Field field = res.getField("partly_cloudy");
//                int drawableId = field.getInt(null);
//            } catch (Exception e) {
//                Log.e("MyTag", "Failure to get drawable id.", e);
//            }

            drawableId = getResourseId(MainActivity.this, "snow", "drawable", getPackageName());
            stringResId = String.valueOf((drawableId));

        } else {
//            choosedCityWeatherIcon = "777";
            stringResId = "777";
        }

        intent.putExtra(ShowWeather.SHOW_WEATHER_KEY, choosedCityTemp + ";" + choosedCityPressure + ";" + stringResId);
//        Toast.makeText(getBaseContext(), "Content = " + stringResId, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }


}
