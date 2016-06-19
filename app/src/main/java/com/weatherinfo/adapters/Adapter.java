package com.weatherinfo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.weatherinfo.R;
import com.weatherinfo.entityes.ForecastData;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ForecastData> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView data;
        public TextView general;
        public TextView temperature;
        public TextView pressure;
        public TextView humidity;
        public TextView windSpeed;

        public ViewHolder(View v) {
            super(v);
            data = (TextView)v.findViewById(R.id.data);
            general = (TextView)v.findViewById(R.id.general_description);
            temperature = (TextView)v.findViewById(R.id.temperature_val);
            pressure = (TextView)v.findViewById(R.id.pressure_val);
            humidity = (TextView)v.findViewById(R.id.humidity_val);
            windSpeed = (TextView)v.findViewById(R.id.wind_speed_val);
        }
    }

    public Adapter(List<ForecastData> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_mapping_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ForecastData forecastData = list.get(position);
        holder.data.setText(forecastData.getData());
        holder.general.setText(forecastData.getGeneralDescription());
        holder.temperature.setText(String.valueOf(forecastData.getTemperature()));
        holder.pressure.setText(String.valueOf(forecastData.getPressure()));
        holder.humidity.setText(String.valueOf(forecastData.getHumidity()));
        holder.windSpeed.setText(String.valueOf(forecastData.getWindSpeed()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
