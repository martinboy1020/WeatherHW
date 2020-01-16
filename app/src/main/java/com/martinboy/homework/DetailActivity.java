package com.martinboy.homework;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView text_start_time, text_end_time, text_temperature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        text_start_time = findViewById(R.id.text_start_time);
        text_end_time = findViewById(R.id.text_end_time);
        text_temperature = findViewById(R.id.text_temperature);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.getParcelable(Constant.DATA) != null) {
            WeatherBean.WeatherElementBean bean = bundle.getParcelable(Constant.DATA);

            if (bean != null) {
                text_start_time.setText(bean.getStartTime());
                text_end_time.setText(bean.getEndTime());
                String sb = bean.getParameterName() + bean.getParameterUnit();
                text_temperature.setText(sb);
            }

        }
    }
}
