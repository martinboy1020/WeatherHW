package com.martinboy.homework;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherBean {

    private static String SUCCESS = "success";
    private static String RESULT = "result";
    private static String RECORDS = "records";
    private static String LOCATION = "location";
    private static String TIME = "time";
    private static String WEATHER_ELEMENT = "weatherElement";
    private static String START_TIME = "startTime";
    private static String END_TIME = "endTime";
    private static String PARAMETER = "parameter";
    private static String PARAMETER_NAME = "parameterName";
    private static String PARAMETER_UNIT = "parameterUnit";

    private ArrayList<WeatherElementBean> weatherList;

    private boolean success;

    public WeatherBean() {

    }

    public ArrayList<WeatherElementBean> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(ArrayList<WeatherElementBean> weatherList) {
        this.weatherList = weatherList;
    }

    public static WeatherBean parse(String jsonString)
            throws JSONException {

        WeatherBean bean = new WeatherBean();

        JSONObject jRoot;

        try {
            jRoot = new JSONObject(jsonString);
        } catch (Exception e) {
            jRoot = null;
        }

        if (jRoot != null) {

            bean.success = jRoot.getBoolean(SUCCESS);

            if (bean.success) {

                JSONObject records = jRoot.getJSONObject(RECORDS);

                if (!records.isNull(LOCATION)) {

                    JSONArray locationArray = records.getJSONArray(LOCATION);

                    if (locationArray.length() > 0) {

                        for (int i = 0; i < locationArray.length(); i++) {

                            JSONObject locationObj = locationArray.getJSONObject(i);

                            if (!locationObj.isNull(WEATHER_ELEMENT)) {

                                JSONArray weatherElement = locationObj.getJSONArray(WEATHER_ELEMENT);
                                ArrayList<WeatherElementBean> weatherList = new ArrayList<>();

                                for (int j = 0; j < weatherElement.length(); j++) {

                                    JSONObject weatherElementObj = weatherElement.getJSONObject(j);

                                    if (!weatherElementObj.isNull(TIME)) {

                                        JSONArray timeArray = weatherElementObj.getJSONArray(TIME);

                                        for (int k = 0; k < timeArray.length(); k++) {

                                            JSONObject timeObj = timeArray.getJSONObject(k);

                                            if (timeObj != null) {
                                                WeatherElementBean weatherElementBean = new WeatherElementBean();
                                                weatherElementBean.setStartTime(timeObj.getString(START_TIME));
                                                weatherElementBean.setEndTime(timeObj.getString(END_TIME));

                                                if (!timeObj.isNull(PARAMETER)) {
                                                    JSONObject parameter = timeObj.getJSONObject(PARAMETER);
                                                    weatherElementBean.setParameterName(parameter.getString(PARAMETER_NAME));
                                                    weatherElementBean.setParameterUnit(parameter.getString(PARAMETER_UNIT));
                                                }

                                                weatherElementBean.setIsData(true);
                                                weatherList.add(weatherElementBean);

                                                WeatherElementBean weatherElementBeanPic = new WeatherElementBean();
                                                weatherElementBeanPic.setIsData(false);
                                                weatherList.add(weatherElementBeanPic);
                                            }

                                        }

                                    }

                                }

                                bean.setWeatherList(weatherList);

                            }


                        }

                    }

                }

            }


        }

        return bean;
    }

    public static class WeatherElementBean implements Parcelable {

        private String startTime;
        private String endTime;
        private String parameterName;
        private String parameterUnit;
        private boolean isData;

        protected WeatherElementBean(Parcel in) {
            startTime = in.readString();
            endTime = in.readString();
            parameterName = in.readString();
            parameterUnit = in.readString();
            isData = in.readByte() != 0;
        }

        public static final Creator<WeatherElementBean> CREATOR = new Creator<WeatherElementBean>() {
            @Override
            public WeatherElementBean createFromParcel(Parcel in) {
                return new WeatherElementBean(in);
            }

            @Override
            public WeatherElementBean[] newArray(int size) {
                return new WeatherElementBean[size];
            }
        };

        public WeatherElementBean() {

        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterUnit() {
            return parameterUnit;
        }

        public void setParameterUnit(String parameterUnit) {
            this.parameterUnit = parameterUnit;
        }

        public boolean isData() {
            return isData;
        }

        public void setIsData(boolean isData) {
            this.isData = isData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

            parcel.writeString(startTime);
            parcel.writeString(endTime);
            parcel.writeString(parameterName);
            parcel.writeString(parameterUnit);
            parcel.writeByte((byte) (isData ? 1 : 0));
        }
    }

}
