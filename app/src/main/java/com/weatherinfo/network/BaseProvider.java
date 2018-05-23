package com.weatherinfo.network;


import android.content.res.Resources;
import com.weatherinfo.R;

public class BaseProvider {

    private Resources resources;

    public BaseProvider(Resources resources) {
        this.resources = resources;
    }

    public String getBaseUrl(){
        return resources.getString(R.string.base_url);
    }

    public String getApiKey(){
        return resources.getString(R.string.api_key);
    }

}
