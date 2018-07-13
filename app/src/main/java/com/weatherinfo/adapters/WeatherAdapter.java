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

import butterknife.BindView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<ForecastData> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView data;
        public TextView temperature;
        public TextView pressure;
        public TextView humidity;
        public TextView windSpeed;
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView)v.findViewById(R.id.im_description);
            data = (TextView)v.findViewById(R.id.data);
            temperature = (TextView)v.findViewById(R.id.temperature_val);
            pressure = (TextView)v.findViewById(R.id.pressure_val);
            humidity = (TextView)v.findViewById(R.id.humidity_val);
            windSpeed = (TextView)v.findViewById(R.id.wind_speed_val);
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
