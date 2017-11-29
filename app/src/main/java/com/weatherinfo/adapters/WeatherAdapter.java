package com.weatherinfo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.weatherinfo.R;
import com.weatherinfo.model.ForecastData;
import com.weatherinfo.utils.Universal;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<ForecastData> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView data;
        public TextView general;
        public TextView temperature;
        public TextView pressure;
        public TextView humidity;
        public TextView windSpeed;
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.im_description);
            data = v.findViewById(R.id.data);
            general = v.findViewById(R.id.general_description);
            temperature = v.findViewById(R.id.temperature_val);
            pressure = v.findViewById(R.id.pressure_val);
            humidity = v.findViewById(R.id.humidity_val);
            windSpeed = v.findViewById(R.id.wind_speed_val);
        }
    }

    public WeatherAdapter(List<ForecastData> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_mapping_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ForecastData forecastData = list.get(position);
        holder.imageView.setImageResource(Universal.getWeatherResource(forecastData.getWeather()[0].getDescription()));
        holder.data.setText(Universal.formatData(forecastData.getDt()));
        holder.general.setText(forecastData.getWeather()[0].getDescription());
        holder.temperature.setText(Universal.convertToCelicies(forecastData.getTemp().getMin()));
        holder.pressure.setText(String.valueOf(forecastData.getPressure()));
        holder.humidity.setText(String.valueOf(forecastData.getHumidity()));
        holder.windSpeed.setText(String.valueOf(forecastData.getSpeed()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
