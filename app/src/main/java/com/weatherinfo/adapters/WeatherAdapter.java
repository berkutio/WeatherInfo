package com.weatherinfo.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherinfo.databinding.WeatherMappingItemBinding;
import com.weatherinfo.model.ForecastData;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<ForecastData> list;

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WeatherMappingItemBinding binding = WeatherMappingItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ForecastData forecastData = list.get(position);
        holder.binding.setForecast(forecastData);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updateAdapter(List<ForecastData> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private WeatherMappingItemBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }
    }

}
