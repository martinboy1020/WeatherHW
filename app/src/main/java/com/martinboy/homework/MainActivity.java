package com.martinboy.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements VolleyTool.VolleyToolCallBack {

    RecyclerView recycle_view_weather;
    WeatherListAdapter weatherListAdapter;
    SharedPreferences sharedPreferences;
    VolleyTool volleyTool;
    ViewStub viewStub;
    View layoutView;
    TextView text_warning;
    Button btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_warning = findViewById(R.id.text_warning);
        btn_refresh = findViewById(R.id.btn_refresh);

        showWarningText(getResources().getString(R.string.string_loading));
        viewStub = findViewById(R.id.layout_view_stub);

        sharedPreferences = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constant.FIRST_COME, true)) {
            sharedPreferences.edit().putBoolean(Constant.FIRST_COME, false).apply();
        } else {
            Toast.makeText(this, getResources().getString(R.string.string_welcome), Toast.LENGTH_LONG).show();
        }

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherList();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherList();
    }

    private void getWeatherList() {
        if (volleyTool == null)
            volleyTool = new VolleyTool();

        if (Utils.checkNetWorkConnect(this)) {
            volleyTool.runNetApi(this, Constant.WEATHER_URL, VolleyTool.VolleyToolMethod.METHOD_GET, this);
        } else {
            showWarningText(getResources().getString(R.string.string_get_data_error));
        }
    }

    public void goToDetailPage(WeatherBean.WeatherElementBean bean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA, bean);
        Intent intent = new Intent().setClass(this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showWarningText(String text) {
        if (text.equals(getResources().getString(R.string.string_loading))) {
            btn_refresh.setVisibility(View.GONE);
        } else {
            btn_refresh.setVisibility(View.VISIBLE);
        }
        text_warning.setVisibility(View.VISIBLE);
        text_warning.setText(text);
    }

    @Override
    public void callBackStringResponse(String response) {

        if (layoutView == null) {
            layoutView = viewStub.inflate();
            recycle_view_weather = layoutView.findViewById(R.id.recycle_view_weather);
            recycle_view_weather.setLayoutManager(new LinearLayoutManager(this));
            weatherListAdapter = new WeatherListAdapter(this);
            recycle_view_weather.setAdapter(weatherListAdapter);
        }

        try {
            WeatherBean weatherBean = WeatherBean.parse(response);
            ArrayList<WeatherBean.WeatherElementBean> list = weatherBean.getWeatherList();
            if (list.size() > 0)
                layoutView.setVisibility(View.VISIBLE);
            text_warning.setVisibility(View.GONE);
            btn_refresh.setVisibility(View.GONE);
            weatherListAdapter.setList(list);
        } catch (JSONException e) {
            e.printStackTrace();
            layoutView.setVisibility(View.GONE);
            showWarningText(getResources().getString(R.string.string_get_data_error));
        }


    }

    @Override
    public void callBackStringResponseError(String error) {
        layoutView.setVisibility(View.GONE);
        showWarningText(getResources().getString(R.string.string_get_data_error));

    }
}
