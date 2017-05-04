package com.weatherinfo.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Date;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 23.04.17.
 */

public class ServiceConnectionFacory {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context, String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        return retrofit;
    }

    private static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateAsLongAdapter());
        return gsonBuilder.create();
    }

    private static class DateAsLongAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(Long.valueOf(json.getAsString()));
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

}
