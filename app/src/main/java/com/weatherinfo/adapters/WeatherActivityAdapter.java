package com.weatherinfo.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherinfo.databinding.WeatherMappingItemBinding;
import com.weatherinfo.model.DataBindingForecastData;
import com.weatherinfo.model.ForecastData;
import java.util.List;

public class WeatherActivityAdapter extends RecyclerView.Adapter<WeatherActivityAdapter.ViewHolder> {

    private final List<ForecastData> list;

    public WeatherActivityAdapter(List<ForecastData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public WeatherActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WeatherMappingItemBinding binding = WeatherMappingItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastData forecastData = list.get(position);
        holder.binding.setForecast(new DataBindingForecastData(forecastData));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private WeatherMappingItemBinding binding;

        ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }

}
